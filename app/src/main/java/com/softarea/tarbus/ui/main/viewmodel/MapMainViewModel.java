package com.softarea.tarbus.ui.main.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.softarea.tarbus.Application;
import com.softarea.tarbus.data.abstracts.MapViewModel;
import com.softarea.tarbus.data.database.DatabaseService;
import com.softarea.tarbus.data.model.MarkerBus;
import com.softarea.tarbus.data.model.MarkerBusStop;
import com.softarea.tarbus.data.model.Vehicle;
import com.softarea.tarbus.data.repository.MpkXmlRepository;
import com.softarea.tarbus.ui.main.databinding.MapMainDataBinding;
import com.softarea.tarbus.ui.main.helpers.DataHelper;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


public class MapMainViewModel extends MapViewModel {
  private Context context;
  private MapMainDataBinding binding;

  public MapMainViewModel(@NonNull Context context, MapMainDataBinding binding) {
    setContext(context);
    setBinding(binding);
    this.binding = binding;
    this.context = context;
    update();
  }

  private void fetchBusStopMarkers() {
    Application application = Application.create(context);
    DatabaseService databaseService = new DatabaseService(context);
    Disposable disposable = databaseService.getDatabase().dbBusStopDAO().getAll()
      .subscribeOn(application.subscribeScheduler())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(markers -> {
        addMarkersInBackground(markers, new MarkerBusStop());
      }, throwable -> {
        Log.e("TEST", "TODO1: On Error Message" + throwable.toString());
        throwable.printStackTrace();
      });

    getCompositeDisposable().add(disposable);
  }

  private void fetchBuses() {
    Application application = Application.create(context);
    Disposable disposable = MpkXmlRepository.getInstance().getMpkService().getAllVehicles()
      .subscribeOn(application.subscribeScheduler())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(liveJsonVehicles -> {
        List<Vehicle> parsedVehicles = DataHelper.parseJsonToVehicle(liveJsonVehicles);
        addMarkersInBackground(parsedVehicles, new MarkerBus());
      }, throwable -> {
        Log.e("TEST", "TODO2: On Error Message" + throwable.toString());
        throwable.printStackTrace();
      });

    getCompositeDisposable().add(disposable);
  }

  public void update() {
    if (getAcyncTaskLoader() != null) {
      stopAsyncTask();
    }
    if (binding.dataStatus == MapMainDataBinding.GET_BUSES) {
      fetchBuses();
    } else {
      fetchBusStopMarkers();
    }
  }

  public void reset() {
    stopAsyncTask();
    getCompositeDisposable().dispose();
    setCompositeDisposable(null);
    context = null;
  }
}
