package com.softarea.tarbus.data.model;

import com.softarea.tarbus.data.interfaces.MyMarker;

import java.util.List;

public class HolderMarker {
  private List<MyMarker> markers;

  public HolderMarker(List<MyMarker> markers) {
    this.markers = markers;
  }

  public List<MyMarker> getMarkers() {
    return markers;
  }
}
