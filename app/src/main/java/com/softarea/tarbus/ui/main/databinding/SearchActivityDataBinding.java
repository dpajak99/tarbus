package com.softarea.tarbus.ui.main.databinding;


import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.tarbus.R;
import com.softarea.tarbus.ui.main.adapter.BusStopListAdapter;

public class SearchActivityDataBinding {
  private View view;
  public RecyclerView recyclerBusStops;
  public EditText searchView;
  public Toolbar toolbar;
  public BusStopListAdapter recyclerBusStopsAdapter;

  public SearchActivityDataBinding(View view) {
    this.view = view;
    this.toolbar = view.findViewById(R.id.toolbar_activity_search);
    this.recyclerBusStops = view.findViewById(R.id.recycler_bus_stops);
    this.searchView = view.findViewById(R.id.search_view);
  }

  public View getView() {
    return view;
  }
}
