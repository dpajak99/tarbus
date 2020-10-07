package com.softarea.tarbus.ui.main.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.softarea.tarbus.R;
import com.softarea.tarbus.ui.main.adapter.BusAdapter;
import com.softarea.tarbus.ui.main.databinding.BusDetailsFragmentDataBinding;
import com.softarea.tarbus.ui.main.viewmodel.BusStopDetailsViewModel;

import java.util.Observable;
import java.util.Observer;

public class BusStopDetailsFragment extends Fragment implements Observer {
  private BusAdapter busAdapter;
  private BusDetailsFragmentDataBinding binding;
  private BusStopDetailsViewModel busStopDetailsViewModel;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    binding = new BusDetailsFragmentDataBinding(inflater.inflate(R.layout.fragment_bus_stop_details, container, false));
    busStopDetailsViewModel = new BusStopDetailsViewModel(getContext());
    setupObserver(busStopDetailsViewModel);
    setUpRecyclerBusList();
    setUpPullToRefreshAction();
    return binding.getView();
  }

  public void setupObserver(Observable observable) {
    observable.addObserver(this);
  }

  private void setUpPullToRefreshAction() {
    binding.swipeRefreshLayout.setOnRefreshListener(() -> {
      //updateFragment(busStopId);
      final Handler handler = new Handler();
      handler.postDelayed(() -> {
        if(binding.swipeRefreshLayout.isRefreshing()) {
          binding.swipeRefreshLayout.setRefreshing(false);
        }
      }, 1000);
    });
  }

  private void setUpRecyclerBusList() {
    busAdapter = new BusAdapter();
    binding.recyclerBuses.setHasFixedSize(true);
    binding.recyclerBuses.setLayoutManager(new LinearLayoutManager(getActivity()));
    binding.recyclerBuses.setAdapter(busAdapter);
  }

  @Override
  public void update(Observable observable, Object o) {
    if (observable instanceof BusStopDetailsViewModel) {
      BusAdapter busAdapter = (BusAdapter) binding.recyclerBuses.getAdapter();
      BusStopDetailsViewModel busStopDetailsViewModel = (BusStopDetailsViewModel) observable;
      if (busAdapter != null) {
        busAdapter.update(busStopDetailsViewModel.getDepartues());
      }
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    busStopDetailsViewModel.reset();
  }
}
