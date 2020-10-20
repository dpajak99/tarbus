package com.softarea.tarbus.utils;

import com.google.gson.JsonArray;
import com.softarea.tarbus.data.model.BusStopMapObject;
import com.softarea.tarbus.data.model.HolderRoute;
import com.softarea.tarbus.data.model.RoutePoint;
import com.softarea.tarbus.data.model.RouteWariant;
import com.softarea.tarbus.data.model.Vehicle;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class RepositoryUtils {
  public static List<BusStopMapObject> parseRouteBusStops(JsonArray array) {
    List<BusStopMapObject> busStops = new ArrayList<>();
    for (int i = 0; i < array.size(); i++) {
      JsonArray busArray = array.get(i).getAsJsonArray();
      BusStopMapObject busStop = new BusStopMapObject(
        busArray.get(0).getAsInt(),    //id
        busArray.get(1).getAsString(), //isCity
        busArray.get(4).getAsDouble(), //latitude
        busArray.get(3).getAsDouble(), //longitude
        busArray.get(5).getAsInt()     //name
      );
      busStops.add(busStop);
    }
    return busStops;
  }

  public static List<RouteWariant> parseRouteWariants(JsonArray array) {
    List<RouteWariant> routeWariants = new ArrayList<>();
    for (int i = 0; i < array.size(); i++) {
      JsonArray jsonElements = array.get(i).getAsJsonArray();
      List<Integer> busOnTrack = new ArrayList<>();
      List<Integer> pointsOnTrack = new ArrayList<>();
      JsonArray jsonTrack = jsonElements.get(6).getAsJsonArray();
      JsonArray jsonBusOnTrack = jsonTrack.get(0).getAsJsonArray();
      JsonArray jsonPointsOnTrack = jsonTrack.get(1).getAsJsonArray();

      for (int j = 0; j < jsonBusOnTrack.size(); j++) {
        busOnTrack.add(jsonBusOnTrack.get(j).getAsInt());
      }
      for (int j = 0; j < jsonPointsOnTrack.size(); j++) {
        pointsOnTrack.add(jsonPointsOnTrack.get(j).getAsInt());
      }

      routeWariants.add(new RouteWariant(
        jsonElements.get(0).getAsInt(),
        jsonElements.get(0).getAsInt(),
        jsonElements.get(0).getAsInt(),
        jsonElements.get(0).getAsString(),
        jsonElements.get(0).getAsString(),
        jsonElements.get(0).getAsString(),
        busOnTrack,
        pointsOnTrack,
        jsonElements.get(0).getAsInt(),
        jsonElements.get(0).getAsString(),
        jsonElements.get(0).getAsInt()
      ));
    }
    return routeWariants;
  }

  public static List<HolderRoute> parsePolylines(JsonArray array) {
    List<HolderRoute> holderRoutes = new ArrayList<>();
    double tmp = 0;

    for (int i = 0; i < array.size(); i++) {
      List<RoutePoint> routePoints = new ArrayList<>();
      JsonArray routeElement = array.get(i).getAsJsonArray();
      JsonArray routeJsonPoints = routeElement.get(3).getAsJsonArray();

      for (int j = 0; j < routeJsonPoints.size(); j++) {
        if (j % 2 == 0) {
          tmp = routeJsonPoints.get(j).getAsDouble();
        } else {
          routePoints.add(new RoutePoint(tmp, routeJsonPoints.get(j).getAsDouble()));
        }
      }
      holderRoutes.add(new HolderRoute(routeElement.get(0).getAsInt(), routeElement.get(1).getAsInt(), routeElement.get(2).getAsInt(), routePoints));
    }

    return holderRoutes;
  }
  public static Vehicle parseJsonToVehicle(String jsonVehicles) {
    JSONArray jsonArray = null;
    Vehicle vehicle = new Vehicle();
    try {
      jsonArray = new JSONArray(jsonVehicles);

      vehicle = new Vehicle(
        jsonArray.getInt(0),
        jsonArray.getInt(1),
        jsonArray.getString(2),
        jsonArray.getString(3),
        jsonArray.getString(4),
        jsonArray.getInt(5),
        jsonArray.getInt(6),
        jsonArray.getInt(7),
        jsonArray.getInt(8),
        jsonArray.getDouble(9),
        jsonArray.getDouble(10),
        jsonArray.getDouble(11),
        jsonArray.getDouble(12),
        jsonArray.getInt(13),
        jsonArray.getString(14),
        jsonArray.getInt(15),
        jsonArray.getString(16),
        jsonArray.getInt(17),
        jsonArray.getString(18),
        jsonArray.getString(19),
        jsonArray.getString(20),
        jsonArray.getString(21),
        jsonArray.getInt(22),
        jsonArray.getString(23),
        jsonArray.getString(24),
        jsonArray.getString(25),
        jsonArray.getString(26),
        jsonArray.getInt(27)
      );
    } catch (JSONException e1) {
      e1.printStackTrace();
    }

    return vehicle;
  }
}
