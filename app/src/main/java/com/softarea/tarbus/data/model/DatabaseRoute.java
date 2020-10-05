package com.softarea.tarbus.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "Autobus", primaryKeys = {"id_autobusu"})
public class DatabaseRoute {
  @ColumnInfo(name = "id_autobusu")
  int id;
  @ColumnInfo(name = "numer_linii")
  @NonNull
  String busLine;

  public DatabaseRoute(int id, @NonNull String busLine) {
    this.id = id;
    this.busLine = busLine;
  }

  public int getId() {
    return id;
  }

  public String getBusLine() {
    return busLine;
  }

  @Override
  public String toString() {
    return "Route{" +
      "id=" + id +
      ", busLine='" + busLine + '\'' +
      '}';
  }
}
