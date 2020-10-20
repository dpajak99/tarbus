package com.softarea.tarbus.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import com.softarea.tarbus.data.interfaces.BusStop;

import java.util.List;

@Entity(tableName = "Przystanek_autobusowy", primaryKeys = {"id_przystanku"})
public class DatabaseBusStop implements BusStop {
  @ColumnInfo(name = "id_przystanku")
  @NonNull
  int id;
  @ColumnInfo(name = "id_miejscowosci")
  @NonNull
  int isCity;
  @ColumnInfo(name = "latitude")
  @NonNull
  double latitude;
  @ColumnInfo(name = "longitude")
  @NonNull
  double longitude;
  @ColumnInfo(name = "nazwa_przystanku")
  @NonNull
  String name;
  @ColumnInfo(name = "numer_przystanku")
  @NonNull
  String number;
  @ColumnInfo(name = "strefa")
  @NonNull
  String zone;
  @ColumnInfo(name = "autobusy_odj")
  @NonNull
  String busLineDepartues;

  @Ignore
  List<String> departueLines;

  public DatabaseBusStop(int id, int isCity, double latitude, double longitude, String name, String number, String zone, String busLineDepartues) {
    this.id = id;
    this.isCity = isCity;
    this.latitude = latitude;
    this.longitude = longitude;
    this.name = name;
    this.number = number;
    this.zone = zone;
    this.busLineDepartues = busLineDepartues;
  }

  public int getId() {
    return id;
  }

  public int getIsCity() {
    return isCity;
  }

  @Override
  public void setIsCity(int status) {
      this.isCity = isCity;
  }

  public double getLat() {
    return latitude;
  }

  public double getLng() {
    return longitude;
  }

  public String getName() {
    return name;
  }

  public String getNumber() {
    return number;
  }

  public String getZone() {
    return zone;
  }

  public String getBusLineDepartues() {
    return busLineDepartues;
  }

  public List<String> getDepartueLines() {
    return departueLines;
  }

  public void setDepartueLines(List<String> departueLines) {
    this.departueLines = departueLines;
  }

  @Override
  public String toString() {
    return "DatabaseBusStop{" +
      "id=" + id +
      ", isCity=" + isCity +
      ", latitude=" + latitude +
      ", longitude=" + longitude +
      ", name='" + name + '\'' +
      ", number='" + number + '\'' +
      ", zone='" + zone + '\'' +
      ", busLineDepartues='" + busLineDepartues + '\'' +
      ", departueLines=" + departueLines +
      '}';
  }
}
