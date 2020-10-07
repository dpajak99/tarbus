package com.softarea.tarbus.data.model;

import com.tickaroo.tikxml.annotation.TextContent;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "p")
public class LiveJsonVehicle {
  @TextContent
  String content;

  public LiveJsonVehicle() {
  }

  public String getContent() {
    return content;
  }

  @Override
  public String toString() {
    return "P{" +
      "content='" + content + '\'' +
      '}';
  }
}
