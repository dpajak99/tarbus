package com.softarea.tarbus.data.interfaces;

public interface BusStop extends MapItem {
  int getId();
  int getIsCity();
  void setIsCity(int status);
  double getLat();
  double getLng();
  String getName();
}
