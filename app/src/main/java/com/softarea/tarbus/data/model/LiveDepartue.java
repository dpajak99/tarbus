package com.softarea.tarbus.data.model;

import androidx.annotation.Nullable;
import androidx.room.Ignore;

import com.softarea.tarbus.data.interfaces.Departue;
import com.softarea.tarbus.utils.TimeUtils;
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
  int aa;
  @Attribute(name = "m")
  int m;
  @Attribute(name = "v")
  String liveTime;
  @Attribute(name = "vn")
  String vn;
  @Nullable
  @Element(name = "D")
  LiveDepartue nextDepartue;
  @Ignore
  int departureTime = 0;
  @Ignore
  DepartureTag departureTag;

  public LiveDepartue() {
    this.departureTag = new DepartureTag();
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
    if(departureTime == 0){
      return TimeUtils.liveTimeToMin(liveTime);
    } else {
      return departureTime;
    }
  }

  @Override
  public void setDepartueTime(int time) {
    this.departureTime = time;
  }

  @Override
  public DepartureTag getDepartureTag() {
    return departureTag;
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

  public int getDepartureTime() {
    return departureTime;
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
