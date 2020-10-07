package com.softarea.tarbus.utils;

import com.softarea.tarbus.data.interfaces.Departue;

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

  /*public static class SortByTime implements Comparator<NearBusStop> {
    // Used for sorting in ascending order of
    // roll number
    public int compare(NearBusStop a, NearBusStop b)
    {
      return a.getMeters() - b.getMeters();
    }
  }*/

  public static boolean isIntInList(List<Integer> list, int object) {
    for (int objectTmp : list) {
      if (objectTmp == object) {
        return true;
      }
    }
    return false;
  }
}
