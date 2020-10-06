package com.softarea.tarbus.ui.main.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.softarea.tarbus.Application;
import com.softarea.tarbus.data.database.DatabaseService;
import com.softarea.tarbus.data.model.DatabaseBusStop;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class BusStopListViewModel extends Observable {
  private Context context;
  private List<DatabaseBusStop> busStops;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  public BusStopListViewModel(@NonNull Context context) {

    this.context = context;
    this.busStops = new ArrayList<>();
    onClickFabLoad();
  }

  public void onClickFabLoad() {
    fetchBusStopList();
  }

  private void fetchBusStopList() {
    Log.i("TEST", "START");
    Application application = Application.create(context);
     DatabaseService databaseService = new DatabaseService(context);

    Disposable disposable = databaseService.getDatabase().dbBusStopDAO().getAll()
      .subscribeOn(application.subscribeScheduler())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(databaseBusStops -> {
        changeBusStopDataSet(databaseBusStops);
        //peopleProgress.set(View.GONE);
        //peopleLabel.set(View.GONE);
        //peopleRecycler.set(View.VISIBLE);
      }, throwable -> {
        Log.e("TEST", throwable.toString());
        //messageLabel.set(context.getString(R.string.error_loading_people));
        //peopleProgress.set(View.GONE);
        //peopleLabel.set(View.VISIBLE);
        //peopleRecycler.set(View.GONE);
        throwable.printStackTrace();
      });

    compositeDisposable.add(disposable);
  }

  private void changeBusStopDataSet(List<DatabaseBusStop> busStops) {
    this.busStops = busStops;
    setChanged();
    notifyObservers();
  }

  public List<DatabaseBusStop> getBusStopsList() {
    return busStops;
  }

  public void reset() {
    compositeDisposable.dispose();
    compositeDisposable = null;
    context = null;
  }
}
