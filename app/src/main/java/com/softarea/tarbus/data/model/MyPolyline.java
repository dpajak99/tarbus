package com.softarea.tarbus.data.model;

import org.osmdroid.util.GeoPoint;

public class MyPolyline {
  private GeoPoint startCoords;
  private GeoPoint endCoords;
  private int polylineColor;


  public MyPolyline(GeoPoint startCoords, GeoPoint endCoords, int polylineColor) {
    this.startCoords = startCoords;
    this.endCoords = endCoords;
    this.polylineColor = polylineColor;
  }

  public GeoPoint getStartCoords() {
    return startCoords;
  }

  public GeoPoint getEndCoords() {
    return endCoords;
  }

  public int getPolylineColor() {
    return polylineColor;
  }
}
