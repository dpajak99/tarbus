package com.softarea.tarbus.ui.main.databinding;


import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.softarea.tarbus.R;
import com.softarea.tarbus.data.interfaces.MapDataBinding;

import org.osmdroid.api.IMapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class MapMainDataBinding implements MapDataBinding {
  public static int GET_BUSES = 0;
  public static int GET_BUS_STOPS = 1;

  private View view;
  public MapView map;
  public IMapController mapController;
  public GpsMyLocationProvider gpsMyLocationProvider;
  public MyLocationNewOverlay locationOverlay;
  public Button buttonchangeData;
  public int dataStatus;


  public MapMainDataBinding(View view, FragmentActivity activity) {
    this.view = view;
    this.map = (MapView) view.findViewById(R.id.map_view);
    this.mapController = this.map.getController();
    this.gpsMyLocationProvider = new GpsMyLocationProvider(activity);
    this.locationOverlay = new MyLocationNewOverlay(gpsMyLocationProvider, this.map);
    this.buttonchangeData = view.findViewById(R.id.button_change_data);
    this.dataStatus = GET_BUS_STOPS;
  }

  public View getView() {
    return view;
  }

  @Override
  public MapView getMap() {
    return map;
  }

  @Override
  public IMapController getMapController() {
    return mapController;
  }

  @Override
  public MyLocationNewOverlay getLocationOverlay() {
    return locationOverlay;
  }
}
