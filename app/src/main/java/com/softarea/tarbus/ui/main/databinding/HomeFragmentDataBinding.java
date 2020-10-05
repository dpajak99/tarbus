package com.softarea.tarbus.ui.main.databinding;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.softarea.tarbus.R;

public class HomeFragmentDataBinding {
  private View view;
  public RecyclerView recyclerBusStops;

  public HomeFragmentDataBinding(View view) {
    this.view = view;
    this.recyclerBusStops = view.findViewById(R.id.recycler_bus_stops);
  }

  public View getView() {
    return view;
  }
}
