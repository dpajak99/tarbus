package com.softarea.tarbus.data.interfaces;

import com.softarea.tarbus.data.model.DepartureTag;

public interface Departue {
  int getDepartueId();
  int getBusLine();
  int getBusId();
  int getVariantId();
  int getDepartueTime();
  void setDepartueTime(int time);
  DepartureTag getDepartureTag();
  String getLiveTime();
  String getDestination();
}
