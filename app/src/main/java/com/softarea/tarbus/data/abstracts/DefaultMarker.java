package com.softarea.tarbus.data.abstracts;

import android.content.Context;
import android.graphics.Bitmap;

import com.softarea.tarbus.data.constant.MapConst;
import com.softarea.tarbus.data.interfaces.MyMarker;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;

import java.util.Observable;

public abstract class DefaultMarker extends Observable implements MyMarker {
  private GeoPoint position = MapConst.CENTER;
  private float degrees = 0;
  private boolean isRotated = false;
  private Object markerObject = new Object();
  private float anchorHorizontal = 0;
  private float anchorVertical = 0;
  private int iconId = 0;
  private Bitmap icon;
  private Marker marker = null;

  public void onOpen(Context context) {

  }

  public void onClose() {

  }

  public void onItemWindowClick() {

  }

  public boolean isAnchor() {
    return false;
  }

  public void setAnchor( float anchorVertical, float anchorHorizontal ) {
    this.anchorVertical = anchorVertical;
    this.anchorHorizontal = anchorHorizontal;
  }

  public void setMarkerObject( Object markerObject) {
    this.markerObject = markerObject;
  }

  public Object getMarkerObject() {
    return this.markerObject;
  }

  public void setMarker( Marker marker ) {
    this.marker = marker;
  }

  public Marker getMarker() {
    return marker;
  }

  public Bitmap getIcon(Context context) {
    return null;
  }

  public void setIcon( Bitmap icon ) {
    this.icon = icon;
  }

  public float getVerticalAnchor() {
    return anchorVertical;
  }

  public float getHorizontalAnchor() {
    return anchorHorizontal;
  }

  public GeoPoint getPosition() {
    return position;
  }

  public void setIconId( int iconId ) {
    this.iconId = iconId;
  }

  public int getIconId() {
    return iconId;
  }

  public void setPosition( GeoPoint position ) {
    this.position = position;
  }

  public float getRotation() {
    return degrees;
  }

  public void setRotation( float degrees ) {
    this.isRotated = true;
    this.degrees = degrees;
  }

  public boolean isRotated() {
    return isRotated;
  }

  public void update( Context context, Object object ) {

  }

  public boolean isToUpdate() {
    return false;
  }

  public int getInfoWindowView() {
    return 0;
  }
}
