package com.softarea.tarbus.data.model;

import com.google.android.gms.maps.model.Marker;

public class BusStopInfoMapHolder {
  private LiveDepartues departues;
  private Marker marker;
  private BusStopMapObject busStop;

  public BusStopInfoMapHolder(LiveDepartues departues, Marker marker, BusStopMapObject busStop) {
    this.departues = departues;
    this.marker = marker;
    this.busStop = busStop;
  }

  public LiveDepartues getDepartues() {
    return departues;
  }

  public Marker getMarker() {
    return marker;
  }

  public BusStopMapObject getBusStop() {
    return busStop;
  }
}
