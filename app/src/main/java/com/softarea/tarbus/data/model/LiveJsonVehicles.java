package com.softarea.tarbus.data.model;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Xml;

import java.util.List;

@Xml(name = "VL")
public class LiveJsonVehicles {
  @Element
  List<LiveJsonVehicle> jsonVehicles;

  public LiveJsonVehicles() {
  }

  public List<LiveJsonVehicle> getJsonVehicles() {
    return jsonVehicles;
  }

  @Override
  public String toString() {
    return "VehiclesList{" +
      "jsonVehicles=" + jsonVehicles.toString() +
      '}';
  }
}
