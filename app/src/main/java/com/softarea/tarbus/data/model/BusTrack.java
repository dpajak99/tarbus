package com.softarea.tarbus.data.model;

import java.util.List;

public class BusTrack {
  List<BusStopMapObject> busStops;
  List<HolderRoute> holderRoutes;
  List<RouteWariant> routeWariants;

  public BusTrack(List<BusStopMapObject> busStops, List<HolderRoute> holderRoutes, List<RouteWariant> routeWariants) {
    this.busStops = busStops;
    this.holderRoutes = holderRoutes;
    this.routeWariants = routeWariants;
  }

  public List<BusStopMapObject> getBusStops() {
    return busStops;
  }

  public List<HolderRoute> getHolderRoutes() {
    return holderRoutes;
  }

  public List<RouteWariant> getRouteWariants() {
    return routeWariants;
  }
}
