package com.softarea.tarbus.data.interfaces;

import org.osmdroid.api.IMapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public interface MapDataBinding {
  MapView getMap();
  IMapController getMapController();
  MyLocationNewOverlay getLocationOverlay();
}
