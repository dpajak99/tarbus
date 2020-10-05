package com.softarea.tarbus.data.repository;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.Marker;
import com.google.gson.JsonArray;
import com.softarea.tarbus.data.model.BusStopInfoMapHolder;
import com.softarea.tarbus.data.model.BusStopMapObject;
import com.softarea.tarbus.data.model.BusTrack;
import com.softarea.tarbus.data.model.LiveDepartues;
import com.softarea.tarbus.data.model.LiveJsonVehicles;
import com.softarea.tarbus.data.model.RouteHolder;
import com.softarea.tarbus.data.model.RouteWariant;
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

public class MpkXmlRepository {
  private MpkService mpkService;
  private static MpkXmlRepository MpkXmlRepository;

  private MpkXmlRepository() {
    TikXml tikxml = new TikXml.Builder().exceptionOnUnreadXml(true).build();
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(MpkService.HTTPS_API_MPK_URL)
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

  public LiveData<BusStopInfoMapHolder> getBusStopInfo(int id, final Marker marker, final BusStopMapObject busStop) {
    final MutableLiveData<BusStopInfoMapHolder> data = new MutableLiveData<>();
    mpkService.getSchedule(String.valueOf(id)).enqueue(new Callback<LiveDepartues>() {
      @Override
      public void onResponse(Call<LiveDepartues> call, Response<LiveDepartues> response) {
        LiveDepartues departues = response.body();
        data.setValue(new BusStopInfoMapHolder(departues, marker, busStop));
      }

      @Override
      public void onFailure(Call<LiveDepartues> call, Throwable t) {
        //AlertUtils.alert(activity, activity.getString(R.string.error_download_shedule));
      }
    });

    return data;
  }

  public LiveData<LiveDepartues> getNextDepartuesFromBusStop(int id) {
    final MutableLiveData<LiveDepartues> data = new MutableLiveData<>();
    mpkService.getSchedule(String.valueOf(id)).enqueue(new Callback<LiveDepartues>() {
      @Override
      public void onResponse(Call<LiveDepartues> call, Response<LiveDepartues> response) {
        LiveDepartues departues = response.body();
        data.setValue(departues);
      }

      @Override
      public void onFailure(Call<LiveDepartues> call, Throwable t) {
        Log.i("TEST", "DeserializeFromXML - onFailure : " + t.toString());
      }
    });

    return data;
  }
}
