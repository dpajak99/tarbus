package com.softarea.tarbus.ui.main.databinding;


import android.view.View;
import android.widget.TextView;

import com.softarea.tarbus.R;

public class HomeFragmentDataBinding {
  private View view;
  public TextView buttonSearch;

  public HomeFragmentDataBinding(View view) {
    this.view = view;
    this.buttonSearch = view.findViewById(R.id.edittext_search);
  }

  public View getView() {
    return view;
  }
}
