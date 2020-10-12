package com.softarea.tarbus.data.model;

public class DepartureTag {
  private boolean isLive;
  private boolean isFirst;

  public DepartureTag() {
    isLive = false;
    isFirst = false;
  }

  public void setLive(boolean live) {
    isLive = live;
  }

  public void setFirst(boolean first) {
    isFirst = first;
  }

  public boolean isLive() {
    return isLive;
  }

  public boolean isFirst() {
    return isFirst;
  }
}
