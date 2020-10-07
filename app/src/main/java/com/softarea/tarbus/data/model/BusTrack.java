package com.softarea.tarbus.data.model;

import java.util.List;

public class BusTrack {
  List<BusStopMapObject> busStops;
  List<RouteHolder> routeHolders;
  List<RouteWariant> routeWariants;

  public BusTrack(List<BusStopMapObject> busStops, List<RouteHolder> routeHolders, List<RouteWariant> routeWariants) {
    this.busStops = busStops;
    this.routeHolders = routeHolders;
    this.routeWariants = routeWariants;
  }

  public List<BusStopMapObject> getBusStops() {
    return busStops;
  }

  public List<RouteHolder> getRouteHolders() {
    return routeHolders;
  }

  public List<RouteWariant> getRouteWariants() {
    return routeWariants;
  }
}
