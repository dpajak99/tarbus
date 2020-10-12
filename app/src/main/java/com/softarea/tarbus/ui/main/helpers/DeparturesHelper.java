package com.softarea.tarbus.ui.main.helpers;

import com.softarea.tarbus.data.interfaces.Departue;

import java.util.List;

public class DeparturesHelper {
  public static List<Departue> removeDepartuesFromList(List<Integer> itemsToRemove, List<Departue> list) {
    int o = 0;
    for (int i : itemsToRemove) {
      list.remove(i - o);
      o++;
    }
    return list;
  }
}
