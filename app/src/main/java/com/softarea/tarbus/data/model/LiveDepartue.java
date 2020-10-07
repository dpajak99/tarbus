package com.softarea.tarbus.data.model;

import androidx.annotation.Nullable;

import com.softarea.tarbus.data.interfaces.Departue;
import com.tickaroo.tikxml.annotation.Attribute;
import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "d")
public class LiveDepartue implements Departue {
  @Attribute(name = "i")
  int departueId;
  @Attribute(name = "di")
  int variantId;
  @Attribute(name = "n")
  int busId;
  @Attribute(name = "t")
  int t;
  @Attribute(name = "r")
  int busLine;
  @Attribute(name = "d")
  String destination;
  @Attribute(name = "dd")
  String dd;
  @Attribute(name = "p")
  String p;
  @Attribute(name = "kn")
  String kn;
  @Attribute(name = "vr")
  int remainingTime;
  @Attribute(name = "m")
  int m;
  @Attribute(name = "v")
  String liveTime;
  @Attribute(name = "vn")
  String vn;
  @Nullable
  @Element(name = "D")
  LiveDepartue nextDepartue;

  public LiveDepartue() {
  }

  public LiveDepartue(int departueId, int variantId, int busId, int t, int busLine, String destination, String dd, String p, String kn, int remainingTime, int m, String liveTime, String vn, @Nullable LiveDepartue nextDepartue) {
    this.departueId = departueId;
    this.variantId = variantId;
    this.busId = busId;
    this.t = t;
    this.busLine = busLine;
    this.destination = destination;
    this.dd = dd;
    this.p = p;
    this.kn = kn;
    this.remainingTime = remainingTime;
    this.m = m;
    this.liveTime = liveTime;
    this.vn = vn;
    this.nextDepartue = nextDepartue;
  }

  @Override
  public int getDepartueId() {
    return departueId;
  }

  @Override
  public int getVariantId() {
    return variantId;
  }

  @Override
  public int getDepartueTime() {
    return remainingTime;
  }

  @Override
  public int getBusId() {
    return busId;
  }

  public int getT() {
    return t;
  }

  @Override
  public int getBusLine() {
    return busLine;
  }

  @Override
  public String getDestination() {
    return destination;
  }

  public String getDd() {
    return dd;
  }

  public String getP() {
    return p;
  }

  public String getKn() {
    return kn;
  }

  public int getRemainingTime() {
    return remainingTime;
  }

  public int getM() {
    return m;
  }

  @Override
  public String getLiveTime() {
    return liveTime;
  }

  public String getVn() {
    return vn;
  }

  @Nullable
  public LiveDepartue getNextDepartue() {
    return nextDepartue;
  }
}
