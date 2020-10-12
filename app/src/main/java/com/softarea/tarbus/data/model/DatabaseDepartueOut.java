package com.softarea.tarbus.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import com.softarea.tarbus.data.interfaces.Departue;

public class DatabaseDepartueOut implements Departue {
  @ColumnInfo(name = "id_odjazdu")
  private int departueId;
  @ColumnInfo(name = "numer_linii")
  private int busLine;
  @ColumnInfo(name = "id_wariant_trasy")
  private int variantId;
  @ColumnInfo(name = "godzina_odjazdu_in_sec")
  private int departueTime;
  @ColumnInfo(name = "opis_trasy")
  private String destination;
  @Ignore
  DepartureTag departureTag;

  public DatabaseDepartueOut(int departueId, int busLine, int variantId, int departueTime, String destination) {
    this.departueId = departueId;
    this.busLine = busLine;
    this.variantId = variantId;
    this.departueTime = departueTime;
    this.destination = destination;
    this.departureTag = new DepartureTag();
  }

  @Override
  public int getDepartueId() {
    return departueId;
  }

  @Override
  public int getBusLine() {
    return busLine;
  }

  @Override
  public int getBusId() {
    return 0;
  }

  @Override
  public int getVariantId() {
    return variantId;
  }

  @Override
  public int getDepartueTime() {
    return departueTime;
  }

  @Override
  public void setDepartueTime(int time) {
    this.departueTime = departueTime;
  }

  @Override
  public DepartureTag getDepartureTag() {
    return departureTag;
  }

  @Override
  public String getLiveTime() {
    return null;
  }

  @Override
  public String getDestination() {
    return destination;
  }
}
