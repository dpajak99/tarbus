package com.softarea.tarbus.ui.main.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.softarea.tarbus.Application;
import com.softarea.tarbus.data.database.DatabaseService;
import com.softarea.tarbus.data.interfaces.Departue;
import com.softarea.tarbus.data.model.DatabaseDepartue;
import com.softarea.tarbus.data.model.LiveDepartue;
import com.softarea.tarbus.data.model.LiveDepartues;
import com.softarea.tarbus.data.repository.MpkXmlRepository;
import com.softarea.tarbus.ui.main.view.BusStopDetailsSlideFragment;
import com.softarea.tarbus.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;


public class BusStopDetailsViewModel extends Observable {
  private Context context;
  private List<DatabaseDepartue> departuesFromDatabase;
  private List<Departue> departues;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  public BusStopDetailsViewModel(@NonNull Context context) {

    this.context = context;
    this.departuesFromDatabase = new ArrayList<>();
    fetchBusList();
    fetchBusListFromRepository();
  }

  private void fetchBusList() {
    Application application = Application.create(context);
     DatabaseService databaseService = new DatabaseService(context);
    int currentTime = TimeUtils.getCurrentTimeInMin();
    int busStopId = BusStopDetailsSlideFragment.BUS_STOP_ID_GLOBAL;
    compositeDisposable.add(databaseService.getDatabase().dbDepartueDAO().getNextDepartues(busStopId, currentTime, "RO")
      .subscribeOn(application.subscribeScheduler())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(databaseDepartues -> {
        departuesFromDatabase = databaseDepartues;
      }, throwable -> {
        Log.e("TEST", "dupacba" + throwable.toString());
        throwable.printStackTrace();
      }));
  }

  private void fetchBusListFromRepository() {
    Application application = Application.create(context);

    compositeDisposable.add(MpkXmlRepository.getInstance().getMpkService().getSchedule(BusStopDetailsSlideFragment.BUS_STOP_ID_GLOBAL)
      .subscribeOn(application.subscribeScheduler())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Consumer<LiveDepartues>() {
        @Override
        public void accept(LiveDepartues liveDepartues) throws Exception {
          Log.i("TEST", liveDepartues.toString());
        }
      }));
  }

  private void changeBusStopDataSet(List<LiveDepartue> departues) {
    this.departues.addAll(departues);
    //this.departues.addAll(departuesFromDatabase);
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
