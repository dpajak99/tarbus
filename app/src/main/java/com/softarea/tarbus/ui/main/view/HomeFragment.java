package com.softarea.tarbus.ui.main.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.softarea.tarbus.R;
import com.softarea.tarbus.ui.main.databinding.HomeDataBinding;

import java.util.Observable;
import java.util.Observer;


public class HomeFragment extends Fragment implements Observer {
  private HomeDataBinding binding;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    binding = new HomeDataBinding(inflater.inflate(R.layout.fragment_home, container, false));

    setUpSearchButton();
    return binding.getView();
  }

  public void setUpSearchButton() {
    binding.buttonSearch.setOnClickListener(view -> {
      Intent intent = new Intent(getActivity(), SearchActivity.class);
      startActivityForResult(intent, 0);
    });
  }

  @Override
  public void update(Observable observable, Object o) {

  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 0) {
      if(resultCode == Activity.RESULT_OK){
        Bundle result = new Bundle();
        result.putInt("BUS_STOP_ID", data.getIntExtra("BUS_STOP_ID", 1));
        result.putString("BUS_STOP_NAME", data.getStringExtra("BUS_STOP_NAME"));

        Navigation.findNavController(binding.getView()).navigate(R.id.navigation_bus_stop_details_slide, result);
      }
      if (resultCode == Activity.RESULT_CANCELED) {
        //Write your code if there's no result
      }
    }
  }
}
