package com.softarea.tarbus.data.model;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.tarbus.Application;
import com.softarea.tarbus.R;
import com.softarea.tarbus.data.abstracts.DefaultMarker;
import com.softarea.tarbus.data.abstracts.MapFragment;
import com.softarea.tarbus.data.interfaces.BusStop;
import com.softarea.tarbus.data.interfaces.MyMarker;
import com.softarea.tarbus.data.repository.MpkXmlRepository;
import com.softarea.tarbus.ui.main.adapter.MapBusStopAdapter;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MarkerBusStop extends DefaultMarker {
  public MarkerBusStop() {
    setAnchor(Marker.ANCHOR_TOP, Marker.ANCHOR_CENTER);
  }

  @Override
  public GeoPoint getPosition() {
    BusStop busStop = getObject();
    return new GeoPoint(busStop.getLat(), busStop.getLng());
  }


  @Override
  public void onOpen(Context context) {
    Log.i("TEST", "mbs: " + getObject().toString());

    Marker marker = getMarker();
    View v = marker.getInfoWindow().getView();
    MapBusStopAdapter mapBusStopAdapter;
    RecyclerView departuresList = v.findViewById(R.id.list_buses_map);

    ProgressBar progressDialog = v.findViewById(R.id.progress_bar);
    progressDialog.setVisibility(View.VISIBLE);

    Button buttonClose = v.findViewById(R.id.button_close);
    buttonClose.setOnClickListener(view -> {
      MapFragment.closeMarker(marker);
    });

    TextView test = v.findViewById(R.id.bus_stop_title);
    test.setText(getObject().getName());

    mapBusStopAdapter = new MapBusStopAdapter();
    departuresList.setLayoutManager(new GridLayoutManager(context, 4));
    departuresList.setHasFixedSize(true);
    departuresList.setAdapter(mapBusStopAdapter);

    Application application = Application.create(context);

    Disposable disposable = MpkXmlRepository.getInstance().getMpkService().getSchedule(getObject().getId())
      .subscribeOn(application.subscribeScheduler())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(departues -> {
        mapBusStopAdapter.update(departues.getDepartueList());
        progressDialog.setVisibility(View.GONE);
      }, throwable -> {
        Log.e("TEST", "TODO2: On Error Message" + throwable.toString());
        throwable.printStackTrace();
      });
    setChanged();
    notifyObservers();
  }

  public BusStop getObject() {
    return (BusStop) getMarkerObject();
  }

  @Override
  public int getIconId(){
    if( getObject().getIsCity() == 0 ) {
      return R.drawable.ic_bus_stop_pin_0;
    } else {
      return R.drawable.ic_bus_stop_pin_1;
    }

    // getIconId();
  }

  @Override
  public float getVerticalAnchor() {
    return Marker.ANCHOR_CENTER;
  }

  @Override
  public float getHorizontalAnchor() {
    return Marker.ANCHOR_CENTER;
  }

  @Override
  public boolean isAnchor() {
    return true;
  }

  @Override
  public int getInfoWindowView() {
    return R.layout.item_map_bus_stop_schedule;
  }

  @Override
  public MyMarker getInstance() {
    return new MarkerBusStop();
  }
}
