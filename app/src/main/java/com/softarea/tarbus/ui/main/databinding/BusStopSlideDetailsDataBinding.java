package com.softarea.tarbus.ui.main.databinding;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.softarea.tarbus.R;

public class BusStopSlideDetailsDataBinding {
  private View view;
  public ViewPager2 viewPager;
  public int bundleBusStopId;
  public String bundleBusStopName;
  public TextView tvBusStopName;
  public FragmentStateAdapter pagerAdapter;
  public TabLayout tabLayout;

  public BusStopSlideDetailsDataBinding(View view, Bundle bundle) {
    this.view = view;
    this.bundleBusStopId = bundle.getInt("BUS_STOP_ID");
    this.bundleBusStopName = bundle.getString("BUS_STOP_NAME");
    this.tvBusStopName = view.findViewById(R.id.tv_bus_stop_name);
    this.viewPager = view.findViewById(R.id.pager_bus_stop);
    this.tabLayout = view.findViewById(R.id.tab_slide_bus_stop);
  }

  public View getView() {
    return view;
  }
}
