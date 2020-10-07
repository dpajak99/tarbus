package com.softarea.tarbus.ui.main.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.softarea.tarbus.Application;
import com.softarea.tarbus.data.database.DatabaseService;
import com.softarea.tarbus.data.interfaces.Departue;
import com.softarea.tarbus.data.model.DatabaseDepartueOut;
import com.softarea.tarbus.data.model.LiveDepartue;
import com.softarea.tarbus.data.repository.MpkXmlRepository;
import com.softarea.tarbus.ui.main.view.BusStopDetailsSlideFragment;
import com.softarea.tarbus.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class BusStopDetailsViewModel extends Observable {
  private Context context;
  private List<DatabaseDepartueOut> departuesFromDatabase;
  private List<Departue> departues;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  public BusStopDetailsViewModel(@NonNull Context context) {

    this.context = context;
    this.departuesFromDatabase = new ArrayList<>();
    this.departues = new ArrayList<>();
    fetchBusList();
  }

  private void fetchBusList() {
    Application application = Application.create(context);
     DatabaseService databaseService = new DatabaseService(context);
    int busStopId = BusStopDetailsSlideFragment.BUS_STOP_ID_GLOBAL;
    Disposable disposable = databaseService.getDatabase().dbDepartueDAO().getNextDepartues(busStopId, TimeUtils.getCurrentTimeInMin(), TimeUtils.getCurrentDayType())
      .subscribeOn(application.subscribeScheduler())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(databaseDepartues -> {
        departuesFromDatabase = databaseDepartues;
        fetchBusListFromRepository();
      }, throwable -> {
        Log.e("TEST", "TODO1: On Error Message" + throwable.toString());
        throwable.printStackTrace();
      });

    compositeDisposable.add(disposable);
  }

  private void fetchBusListFromRepository() {
    Application application = Application.create(context);

    Disposable disposable = MpkXmlRepository.getInstance().getMpkService().getSchedule(BusStopDetailsSlideFragment.BUS_STOP_ID_GLOBAL)
      .subscribeOn(application.subscribeScheduler())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(departues -> {
        changeBusStopDataSet(departues.getDepartueList());
      }, throwable -> {
        Log.e("TEST", "TODO2: On Error Message" + throwable.toString());
        throwable.printStackTrace();
      });

    compositeDisposable.add(disposable);
  }

  private void changeBusStopDataSet(List<LiveDepartue> departues) {
    this.departues.addAll(departues);
    this.departues.addAll(departuesFromDatabase);
    setChanged();
    notifyObservers();
  }

  public List<Departue> getDepartues() {
    return departues;
  }

  public void reset() {
    compositeDisposable.dispose();
    compositeDisposable = null;
    context = null;
  }
}
