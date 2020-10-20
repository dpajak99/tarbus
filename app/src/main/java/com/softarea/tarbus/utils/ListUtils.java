package com.softarea.tarbus.utils;

import com.softarea.tarbus.data.interfaces.Departue;
import com.softarea.tarbus.data.interfaces.MapItem;

import java.util.Comparator;
import java.util.List;

public class ListUtils {
  public static class Sortbyroll implements Comparator<Departue> {
    // Used for sorting in ascending order of
    // roll number
    public int compare(Departue a, Departue b) {
      return a.getDepartueTime() - b.getDepartueTime();
    }
  }

  public static class SortbyCoords implements Comparator<MapItem> {
    public int compare(MapItem a, MapItem b) {
      if (a.getLng() < b.getLng()) return -1;
      if (a.getLng() > b.getLng()) return 1;
      return 0;
    }
  }

  public static boolean isIntInList(List<Integer> list, int object) {
    for (int objectTmp : list) {
      if (objectTmp == object) {
        return true;
      }
    }
    return false;
  }
}
