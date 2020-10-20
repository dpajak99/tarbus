package com.softarea.tarbus.ui.main.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.softarea.tarbus.R;
import com.softarea.tarbus.data.abstracts.MapFragment;
import com.softarea.tarbus.ui.main.databinding.MapMainDataBinding;
import com.softarea.tarbus.ui.main.viewmodel.MapMainViewModel;

public class MapMainFragment extends MapFragment {
  private MapMainDataBinding binding;
  private MapMainViewModel mapMainViewModel;
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = new MapMainDataBinding(inflater.inflate(R.layout.fragment_map_main, container, false), getActivity());
    mapMainViewModel = new MapMainViewModel(requireContext(), binding);

    setUpFragment(binding, mapMainViewModel);
    setUpChangeDataButton();
    setupObserver(mapMainViewModel);

    return binding.getView();
  }

  private void setUpChangeDataButton() {
    binding.buttonchangeData.setOnClickListener(view -> {
      if (binding.dataStatus == MapMainDataBinding.GET_BUSES) {
        binding.dataStatus = MapMainDataBinding.GET_BUS_STOPS;
        stopAutoRefreshing();
        mapMainViewModel.update();
      } else {
        binding.dataStatus = MapMainDataBinding.GET_BUSES;
        startAutoRefreshing();
      }
      binding.map.getOverlayManager().clear();
      binding.map.invalidate();
      closeMarker(actualOpenedMarker);
      actualOpenedMarker = null;
    });
  }
}
