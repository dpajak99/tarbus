package com.softarea.tarbus.utils;

import com.softarea.tarbus.data.interfaces.Departue;

import java.util.Comparator;

public class ListUtils {
  public static class Sortbyroll implements Comparator<Departue> {
    // Used for sorting in ascending order of
    // roll number
    public int compare(Departue a, Departue b)
    {
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
}
