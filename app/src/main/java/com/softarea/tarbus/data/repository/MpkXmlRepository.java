package com.softarea.tarbus.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softarea.tarbus.data.model.LiveJsonVehicles;
import com.softarea.tarbus.data.model.Vehicle;
import com.softarea.tarbus.utils.RepositoryUtils;
import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class MpkXmlRepository {
  private MpkService mpkService;
  private static MpkXmlRepository MpkXmlRepository;

  public MpkXmlRepository() {
    TikXml tikxml = new TikXml.Builder().exceptionOnUnreadXml(true).build();
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(MpkService.HTTPS_API_MPK_URL)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(TikXmlConverterFactory.create(tikxml))
      .build();

    mpkService = retrofit.create(MpkService.class);
  }

  public synchronized static MpkXmlRepository getInstance() {
    if (MpkXmlRepository == null) {
      MpkXmlRepository = new MpkXmlRepository();
    }
    return MpkXmlRepository;
  }

  public MpkService getMpkService() {
    return mpkService;
  }

  public LiveData<List<Vehicle>> getBusStopByLine(String busId, String busLine) {
    final MutableLiveData<List<Vehicle>> data = new MutableLiveData<>();
    if (busId.equals("0")) {
      busId = "";
    }
    mpkService.getVehicles(busLine, "", busId).enqueue(new Callback<LiveJsonVehicles>() {
      @Override
      public void onResponse(Call<LiveJsonVehicles> call, Response<LiveJsonVehicles> response) {
        List<Vehicle> vehicles = new ArrayList<>();
        if (!response.headers().get("content-length").equals("15")) {
          LiveJsonVehicles jsonVehicles = response.body();
          for (int i = 0; i < jsonVehicles.getJsonVehicles().size(); i++) {
            vehicles.add(RepositoryUtils.parseJsonToVehicle(jsonVehicles.getJsonVehicles().get(i).getContent()));
          }
        }

        data.setValue(vehicles);
      }

      @Override
      public void onFailure(Call<LiveJsonVehicles> call, Throwable t) {
        Log.i("TEST", "DeserializeFromXML - onFailure : " + t.toString());
      }
    });

    return data;
  }

  /*public Observable<BusStopInfoMapHolder> getBusStopInfo(int id, final Marker marker, final BusStopMapObject busStop) {
    final MutableLiveData<BusStopInfoMapHolder> data = new MutableLiveData<>();
    mpkService.getSchedule(String.valueOf(id)).enqueue(new Callback<Observable<LiveDepartues>>() {
      @Override
      public void onResponse(Call<Observable<LiveDepartues>> call, Response<Observable<LiveDepartues>> response) {
        Observable<LiveDepartues> departues = response.body();
        //data.setValue(new BusStopInfoMapHolder(departues, marker, busStop));
      }

      @Override
      public void onFailure(Call<Observable<LiveDepartues>> call, Throwable t) {
        //AlertUtils.alert(activity, activity.getString(R.string.error_download_shedule));
      }
    });

    return null;
  }*/

  /*public Observable<LiveDepartues> getNextDepartuesFromBusStop(int id) {
    final Observable<LiveDepartues>[] data = new Observable[]{};
    mpkService.getSchedule(id).enqueue(new Callback<Observable<LiveDepartues>>() {
      @Override
      public void onResponse(Call<Observable<LiveDepartues>> call, Response<Observable<LiveDepartues>> response) {
        Observable<LiveDepartues> departues = response.body();
        data[0] = departues;
      }

      @Override
      public void onFailure(Call<Observable<LiveDepartues>> call, Throwable t) {
        Log.i("TEST", "DeserializeFromXML - onFailure : " + t.toString());
      }
    });

    return data[0];
  }*/
}
