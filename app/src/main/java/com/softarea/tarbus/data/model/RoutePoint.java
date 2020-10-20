package com.softarea.tarbus.data.model;

import org.osmdroid.util.GeoPoint;

public class RoutePoint {
  double latitude;
  double longtitude;

  public RoutePoint(double latitude, double longtitude) {
    this.latitude = latitude;
    this.longtitude = longtitude;
  }

  public GeoPoint getCoords() {
    return new GeoPoint(this.longtitude, this.latitude);
  }
}
