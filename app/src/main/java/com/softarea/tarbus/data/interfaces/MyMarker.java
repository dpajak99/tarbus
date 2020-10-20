package com.softarea.tarbus.data.interfaces;

import android.content.Context;
import android.graphics.Bitmap;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;

public interface MyMarker {
  void onOpen(Context context);
  void onClose();
  void onItemWindowClick();
  Object getObject();
  void setMarkerObject(Object object);
  void update( Context context, Object object );
  int getIconId();
  boolean isAnchor();
  Marker getMarker();
  void setMarker(Marker marker);
  Bitmap getIcon(Context context);
  float getVerticalAnchor();
  float getHorizontalAnchor();
  GeoPoint getPosition();
  float getRotation();
  int getInfoWindowView();
  MyMarker getInstance();
  boolean isToUpdate();
}
