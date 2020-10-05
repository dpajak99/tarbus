package com.softarea.tarbus.ui.main.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.tarbus.R;
import com.softarea.tarbus.ui.main.adapter.BusStopListAdapter;
import com.softarea.tarbus.ui.main.databinding.HomeFragmentDataBinding;
import com.softarea.tarbus.ui.main.viewmodel.BusStopListViewModel;

import java.util.Observable;
import java.util.Observer;


public class HomeFragment extends Fragment implements Observer {
  private BusStopListViewModel busStopListViewModel;
  private HomeFragmentDataBinding binding;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    binding = new HomeFragmentDataBinding(inflater.inflate(R.layout.fragment_home, container, false));
    busStopListViewModel = new BusStopListViewModel(getContext());
    setupObserver(busStopListViewModel);
    setupListBusStop(binding.recyclerBusStops);
    return binding.getView();
  }

  private void setupListBusStop(RecyclerView recyclerBusStop) {
    BusStopListAdapter adapter = new BusStopListAdapter(getActivity());
    recyclerBusStop.setAdapter(adapter);
    recyclerBusStop.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerBusStop.setHasFixedSize(true);
  }

  public void setupObserver(Observable observable) {
    observable.addObserver(this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    busStopListViewModel.reset();
  }


  @Override
  public void update(Observable observable, Object o) {
    if (observable instanceof BusStopListViewModel) {
      BusStopListAdapter busStopListAdapter = (BusStopListAdapter) binding.recyclerBusStops.getAdapter();
      BusStopListViewModel busStopListViewModel = (BusStopListViewModel) observable;
      if (busStopListAdapter != null) {
        busStopListAdapter.update(busStopListViewModel.getBusStopsList());
      }
    }
  }
}
