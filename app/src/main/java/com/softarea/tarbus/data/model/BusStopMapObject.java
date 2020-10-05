package com.softarea.tarbus.data.model;

import com.google.android.gms.maps.model.LatLng;
import com.softarea.tarbus.data.interfaces.BusStop;

public class BusStopMapObject implements BusStop {
  private int id;
  private int isCity;
  private double latitude;
  private double longitude;
  private String name;

  public BusStopMapObject(int id, String name, double latitude, double longitude, int idCity) {
    this.id = id;
    this.isCity = idCity;
    this.latitude = latitude;
    this.longitude = longitude;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public int getIsCity() {
    return isCity;
  }

  public LatLng getPosition() {
    return new LatLng(latitude, longitude);
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public String getName() {
    return name;
  }
}
