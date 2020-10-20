package com.softarea.tarbus.ui.main.databinding;


import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.softarea.tarbus.R;
import com.softarea.tarbus.data.interfaces.MapDataBinding;

import org.osmdroid.api.IMapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class MapDeparturesDetailsDataBinding implements MapDataBinding {
  private View view;
  public MapView map;
  public IMapController mapController;
  public GpsMyLocationProvider gpsMyLocationProvider;
  public MyLocationNewOverlay locationOverlay;

  public int busLine;
  public int busId;
  public int variantId;

  public MapDeparturesDetailsDataBinding(View view, FragmentActivity activity, Bundle bundle) {
    this.view = view;
    this.map = (MapView) view.findViewById(R.id.map_view);
    this.mapController = this.map.getController();
    this.gpsMyLocationProvider = new GpsMyLocationProvider(activity);
    this.locationOverlay = new MyLocationNewOverlay(gpsMyLocationProvider, this.map);
    this.busLine = bundle.getInt("BUS_LINE");
    this.busId = bundle.getInt("BUS_ID");
    this.variantId = bundle.getInt("VARIANT_ID");
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
