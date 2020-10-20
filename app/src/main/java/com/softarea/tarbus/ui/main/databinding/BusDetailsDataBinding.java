package com.softarea.tarbus.ui.main.databinding;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.softarea.tarbus.R;
import com.softarea.tarbus.ui.main.view.BusStopDetailsSlideFragment;

public class BusDetailsDataBinding {
  private View view;
  public int bundleBusStopId;
  public RecyclerView recyclerBuses;
  public SwipeRefreshLayout swipeRefreshLayout;

  public BusDetailsDataBinding(View view) {
    this.view = view;
    this.bundleBusStopId = BusStopDetailsSlideFragment.BUS_STOP_ID_GLOBAL;
    this.recyclerBuses = view.findViewById(R.id.list_buses);
    this.swipeRefreshLayout = view.findViewById(R.id.swiperefresh_items);
  }

  public View getView() {
    return view;
  }
}
