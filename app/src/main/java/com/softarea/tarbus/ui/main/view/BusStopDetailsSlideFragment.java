package com.softarea.tarbus.ui.main.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;
import com.softarea.tarbus.R;
import com.softarea.tarbus.ui.main.adapter.SlideBusStopAdapter;
import com.softarea.tarbus.ui.main.databinding.BusStopSlideDetailsDataBinding;

public class BusStopDetailsSlideFragment extends Fragment {
  public static int BUS_STOP_ID_GLOBAL;
  private BusStopSlideDetailsDataBinding binding;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_bus_stop_details_slide, container, false);
    binding = new BusStopSlideDetailsDataBinding(root, this.getArguments());
    BUS_STOP_ID_GLOBAL = binding.bundleBusStopId;
    setUpFragment();
    setUpFragmentAdapter();
    return root;
  }

  private void setUpFragment() {
    binding.tvBusStopName.setText(binding.bundleBusStopName);
  }

  private void setUpFragmentAdapter() {
    binding.pagerAdapter = new SlideBusStopAdapter(this);
    binding.viewPager.setAdapter(binding.pagerAdapter);

    new TabLayoutMediator(binding.tabLayout, binding.viewPager,
      (tab, position) -> {
        if (position == 0) {
          tab.setText(getString(R.string.next_departues));
        } else {
          tab.setText(getString(R.string.schedule));
        }
      }
    ).attach();
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
  }
}
