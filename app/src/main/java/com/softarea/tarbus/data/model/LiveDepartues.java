package com.softarea.tarbus.data.model;

import com.tickaroo.tikxml.annotation.Attribute;
import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Xml;

import java.util.List;

@Xml(name = "Departues")
public class LiveDepartues {
  @Element(name = "N")
  LiveDepartueHolder liveDepartueHolder;
  @Attribute(name = "time")
  String time;
  @Attribute(name = "i")
  int busStopId;
  @Element(name = "D")
  List<LiveDepartue> departueList;

  public String getTime() {
    return time;
  }

  public int getBusStopId() {
    return busStopId;
  }

  public List<LiveDepartue> getDepartueList() {
    return departueList;
  }

  public LiveDepartues() {
  }

  @Override
  public String toString() {
    return "LiveDepartues{" +
      "n=" + liveDepartueHolder +
      ", time='" + time + '\'' +
      ", busStopId=" + busStopId +
      ", departueList=" + departueList +
      '}';
  }
}
