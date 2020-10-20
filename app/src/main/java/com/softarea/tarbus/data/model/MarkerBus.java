package com.softarea.tarbus.data.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.softarea.tarbus.R;
import com.softarea.tarbus.data.abstracts.DefaultMarker;
import com.softarea.tarbus.data.interfaces.MyMarker;
import com.softarea.tarbus.utils.ImageUtils;

import org.osmdroid.util.GeoPoint;

public class MarkerBus extends DefaultMarker {
  public MarkerBus() {
  }

  @Override
  public Vehicle getObject() {
    return (Vehicle) getMarkerObject();
  }

  @Override
  public GeoPoint getPosition() {
    Vehicle vehicle = getObject();
    return new GeoPoint(vehicle.getSzerokosc(), vehicle.getDlugosc());
  }

  @Override
  public Bitmap getIcon(Context context) {
    Vehicle vehicle = getObject();
    if(!vehicle.getNumerLini().isEmpty() ) {
      return ImageUtils.createBusPinImage(context, vehicle);
    } else {
      return ImageUtils.createLongBusPinImage(context, vehicle);
    }
  }

  @Override
  public void update(Context context, Object object) {
    MarkerBus markerBus = (MarkerBus) object;
    Vehicle newVehicle = markerBus.getObject();
    setMarkerObject(newVehicle);
    getMarker().setPosition(new GeoPoint(newVehicle.getSzerokosc(), newVehicle.getDlugosc()));
    getMarker().setIcon(new BitmapDrawable(context.getResources(), getIcon(context)));
  }

  @Override
  public int getInfoWindowView() {
    return R.layout.item_empty;
  }

  @Override
  public MyMarker getInstance() {
    return new MarkerBus();
  }

  @Override
  public boolean isToUpdate() {
    return true;
  }
}
