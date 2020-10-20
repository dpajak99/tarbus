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
import com.softarea.tarbus.ui.main.adapter.DepartureAdapter;
import com.softarea.tarbus.ui.main.databinding.BusDetailsDataBinding;
import com.softarea.tarbus.ui.main.viewmodel.BusStopDetailsViewModel;
import com.softarea.tarbus.utils.HardwareUtils;

import java.util.Observable;
import java.util.Observer;

public class BusStopDetailsFragment extends Fragment implements Observer {
  private DepartureAdapter departureAdapter;
  private BusDetailsDataBinding binding;
  private BusStopDetailsViewModel busStopDetailsViewModel;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    binding = new BusDetailsDataBinding(inflater.inflate(R.layout.fragment_bus_stop_details, container, false));
    busStopDetailsViewModel = new BusStopDetailsViewModel(getContext());
    setupObserver(busStopDetailsViewModel);
    setUpRecyclerBusList();
    setUpPullToRefreshAction();
    startAutoRefreshing();
    return binding.getView();
  }

  public void setupObserver(Observable observable) {
    observable.addObserver(this);
  }

  private void setUpPullToRefreshAction() {
    binding.swipeRefreshLayout.setOnRefreshListener(() -> {
      busStopDetailsViewModel.refresh();
      final Handler handler = new Handler();
      handler.postDelayed(() -> {
        if(binding.swipeRefreshLayout.isRefreshing()) {
          binding.swipeRefreshLayout.setRefreshing(false);
        }
      }, 1000);
    });
  }

  private void setUpRecyclerBusList() {
    departureAdapter = new DepartureAdapter();
    binding.recyclerBuses.setHasFixedSize(true);
    binding.recyclerBuses.setLayoutManager(new LinearLayoutManager(getActivity()));
    binding.recyclerBuses.setAdapter(departureAdapter);
  }

  final Handler handler = new Handler();
  protected void startAutoRefreshing() {
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        busStopDetailsViewModel.refresh();
        HardwareUtils.vibrate(requireContext(), 50);
        handler.postDelayed(this, 30000);
      }
    };
    handler.postDelayed(runnable, 0);
  }

  protected void stopAutoRefreshing() {
    handler.removeCallbacksAndMessages(null);
  }

  @Override
  public void update(Observable observable, Object o) {
    if (observable instanceof BusStopDetailsViewModel) {
      DepartureAdapter departureAdapter = (DepartureAdapter) binding.recyclerBuses.getAdapter();
      BusStopDetailsViewModel busStopDetailsViewModel = (BusStopDetailsViewModel) observable;
      if (departureAdapter != null) {
        departureAdapter.update(busStopDetailsViewModel.getDepartues());
      }
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    stopAutoRefreshing();
    departureAdapter.reset();
    busStopDetailsViewModel.reset();
  }

  @Override
  public void onStop() {
    super.onStop();
    stopAutoRefreshing();
  }
}
