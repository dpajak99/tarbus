package com.softarea.tarbus.data.model;

import com.google.android.gms.maps.model.LatLng;
import com.softarea.tarbus.data.interfaces.BusStop;

import org.osmdroid.util.GeoPoint;

public class LiveBusStop implements BusStop {
  private int id;
  private int isCity;
  private double latitude;
  private double longitude;
  private String name;

  public LiveBusStop(int id, String name, double latitude, double longitude, int idCity) {
    this.id = id;
    this.isCity = idCity;
    this.latitude = latitude;
    this.longitude = longitude;
    this.name = name;
  }

  public LiveBusStop(GeoPoint geoPoint) {
    this.latitude = geoPoint.getLatitude();
    this.longitude = geoPoint.getLongitude();
  }

  public int getId() {
    return id;
  }

  public int getIsCity() {
    return isCity;
  }

  @Override
  public void setIsCity(int status) {
    this.isCity = status;
  }

  public LatLng getPosition() {
    return new LatLng(latitude, longitude);
  }

  public double getLat() {
    return latitude;
  }

  public double getLng() {
    return longitude;
  }

  public String getName() {
    return name;
  }
}