package com.softarea.tarbus.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softarea.tarbus.data.model.LiveJsonVehicles;
import com.softarea.tarbus.data.model.Vehicle;
import com.softarea.tarbus.utils.RepositoryUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MpkJsonRepository {
  private MpkService mpkService;
  private static MpkJsonRepository mpkJsonRepository;

  private MpkJsonRepository() {
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(MpkService.HTTPS_API_MPK_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    mpkService = retrofit.create(MpkService.class);
  }

  public synchronized static MpkJsonRepository getInstance() {
    if (mpkJsonRepository == null) {
      mpkJsonRepository = new MpkJsonRepository();
    }
    return mpkJsonRepository;
  }

  public LiveData<List<Vehicle>> getBusTrackByLine(String busId, String busLine) {
    final MutableLiveData<List<Vehicle>> data = new MutableLiveData<>();
    if (busId.equals("0")) {
      busId = "";
    }

    mpkService.getVehicles(busLine, "", busId).enqueue(new Callback<LiveJsonVehicles>() {
      @Override
      public void onResponse(Call<LiveJsonVehicles> call, Response<LiveJsonVehicles> response) {
        List<Vehicle> vehicles = new ArrayList<>();
        LiveJsonVehicles jsonVehicles = response.body();
        if (!response.headers().get("content-length").equals("15")) {
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
}
