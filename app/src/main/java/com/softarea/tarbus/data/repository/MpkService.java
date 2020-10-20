package com.softarea.tarbus.data.repository;

import com.google.gson.JsonArray;
import com.softarea.tarbus.data.model.LiveDepartues;
import com.softarea.tarbus.data.model.LiveJsonVehicles;
import com.softarea.tarbus.data.model.SearchConnectionCallback;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MpkService {
  String HTTPS_API_MPK_URL = "http://rozklad.komunikacja.tarnow.pl/";

  @GET("Home/GetNextDepartues")
  Observable<LiveDepartues> getSchedule(@Query("busStopId") int id);

  @GET("Home/CNR_GetVehicles")
  Observable<LiveJsonVehicles> getVehicles(@Query("r") int busLine, @Query("nb") String busId );

  @GET("Home/CNR_GetVehicles")
  Observable<LiveJsonVehicles> getAllVehicles();

  @GET("Home/GetTracks")
  Observable<JsonArray> getTracks( @Query("routeId") int routeId, @Query("ttId") int ttId, @Query("transits") int transits );

  @GET("Home/SearchConnection")
  Call<SearchConnectionCallback> searchConnection(@Query("lng1") String lng1, @Query("lat1") String lat1, @Query("lng2") String lng2, @Query("lat2") String lat2,
                                                  @Query("hour") String hour, @Query("date") String date, @Query("lang") String lang, @Query("c") String c,
                                                  @Query("transfers") String transfers, @Query("mode") String mode, @Query("carriers") String carriers,
                                                  @Query("skipRoutes") String skipRoutes, @Query("vehicleFlags") String vehicleFlags, @Query("walkMode") String walkMode);
}
