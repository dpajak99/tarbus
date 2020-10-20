package com.softarea.tarbus.ui.main.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.softarea.tarbus.R;
import com.softarea.tarbus.data.abstracts.MapFragment;
import com.softarea.tarbus.ui.main.databinding.MapDeparturesDetailsDataBinding;
import com.softarea.tarbus.ui.main.viewmodel.MapDepartureDetailsViewModel;

public class MapDepartureDetailsFragment extends MapFragment {
  private MapDeparturesDetailsDataBinding binding;
  private MapDepartureDetailsViewModel mapDepartureDetailsViewModel;
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = new MapDeparturesDetailsDataBinding(inflater.inflate(R.layout.fragment_map_main, container, false), getActivity(), this.getArguments());
    mapDepartureDetailsViewModel = new MapDepartureDetailsViewModel(requireContext(), binding);

    setUpFragment(binding, mapDepartureDetailsViewModel);
    setupObserver(mapDepartureDetailsViewModel);
    startAutoRefreshing();

    return binding.getView();
  }
}
