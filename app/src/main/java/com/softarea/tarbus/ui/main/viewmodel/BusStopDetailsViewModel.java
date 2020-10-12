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
import com.softarea.tarbus.ui.main.helpers.DeparturesHelper;
import com.softarea.tarbus.ui.main.view.BusStopDetailsSlideFragment;
import com.softarea.tarbus.utils.ListUtils;
import com.softarea.tarbus.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Comparator;
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
    refresh();
  }

  public void refresh() {
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
    this.departues.clear();
    this.departues.addAll(this.departuesFromDatabase);
    this.departues = removePreviousDepartues(this.departues);
    this.departues.addAll(prepareRemoteDepartue(departues));
    this.departues.sort(Comparator.comparing(Departue::getDepartueTime));
    setChanged();
    notifyObservers();
  }

  private List<Departue> removePreviousDepartues(List<Departue> departues) {
    List<Integer> itemsToRemove = new ArrayList<>();
    List<Integer> usedLines = new ArrayList<>();
    int currentTime = TimeUtils.getCurrentTimeInMin();
    for (int i = 0; i < departues.size(); i++) {
      Departue departue = departues.get(i);
      if (departue.getDepartueTime() > 0 && departue.getDepartueTime() < 60) {
        departue.setDepartueTime(departue.getDepartueTime() + 1440);
      } else if (departue.getDepartueTime() < currentTime) {
        itemsToRemove.add(i);
        continue;
      }
      if (!ListUtils.isIntInList(usedLines, departue.getBusLine())) {
        usedLines.add(departue.getBusLine());
        itemsToRemove.add(i);
      }
    }

    return DeparturesHelper.removeDepartuesFromList(itemsToRemove, departues);
  }

  private List<LiveDepartue> prepareRemoteDepartue(List<LiveDepartue> departues) {
    int currentTime = TimeUtils.getCurrentTimeInMin();
    for(LiveDepartue departue : departues ) {
      if(departue.getDepartueTime() < currentTime) {
        departue.setDepartueTime(departue.getDepartueTime()+1440);
      }
      if(departue.getBusId() > 0) {
        departue.getDepartureTag().setLive(true);
      }
      departue.getDepartureTag().setFirst(true);
    }
    return departues;
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
