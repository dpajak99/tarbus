package com.softarea.tarbus.ui.main.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.tarbus.R;
import com.softarea.tarbus.ui.main.adapter.BusStopListAdapter;
import com.softarea.tarbus.ui.main.databinding.SearchActivityDataBinding;
import com.softarea.tarbus.ui.main.viewmodel.BusStopListViewModel;

import java.util.Observable;
import java.util.Observer;


public class SearchActivity extends AppCompatActivity implements Observer {
  private BusStopListViewModel busStopListViewModel;
  private SearchActivityDataBinding binding;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    binding = new SearchActivityDataBinding(getWindow().getDecorView().getRootView());
    busStopListViewModel = new BusStopListViewModel(getApplicationContext());
    setupToolbar();
    setUpSearchView();
    setupObserver(busStopListViewModel);
    setupListBusStop(binding.recyclerBusStops);
  }

  public void setupToolbar() {
    setSupportActionBar(binding.toolbar);
    setTitle("Wyszukaj połączenie");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
  }
  public void setUpSearchView() {
    binding.searchView.requestFocus();
    binding.searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
    binding.searchView.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        binding.recyclerBusStopsAdapter.getFilter().filter(charSequence);
      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });
  }

  private void setupListBusStop(RecyclerView recyclerBusStop) {
    binding.recyclerBusStopsAdapter = new BusStopListAdapter(this);
    recyclerBusStop.setAdapter(binding.recyclerBusStopsAdapter);
    recyclerBusStop.setLayoutManager(new LinearLayoutManager(this));
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

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return true;
  }
}
