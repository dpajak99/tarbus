package com.softarea.tarbus.ui.main.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.softarea.tarbus.Application;
import com.softarea.tarbus.data.abstracts.MapFragment;
import com.softarea.tarbus.data.abstracts.MapViewModel;
import com.softarea.tarbus.data.database.DatabaseService;
import com.softarea.tarbus.data.interfaces.BusStop;
import com.softarea.tarbus.data.interfaces.MyMarker;
import com.softarea.tarbus.data.model.HolderRoute;
import com.softarea.tarbus.data.model.LiveBusStop;
import com.softarea.tarbus.data.model.MarkerBus;
import com.softarea.tarbus.data.model.MarkerBusStop;
import com.softarea.tarbus.data.model.RoutePoint;
import com.softarea.tarbus.data.model.RouteWariant;
import com.softarea.tarbus.data.repository.MpkJsonRepository;
import com.softarea.tarbus.data.repository.MpkXmlRepository;
import com.softarea.tarbus.ui.main.databinding.MapDeparturesDetailsDataBinding;
import com.softarea.tarbus.ui.main.helpers.DataHelper;
import com.softarea.tarbus.ui.main.view.MapDepartureDetailsFragment;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


public class MapDepartureDetailsViewModel extends MapViewModel {
  private Context context;
  private MapDeparturesDetailsDataBinding binding;
  private List<LiveBusStop> liveBusStops;
  private List<HolderRoute> routeHolders;
  private List<RouteWariant> routeWariants;

  public MapDepartureDetailsViewModel(@NonNull Context context, MapDeparturesDetailsDataBinding binding) {
    setContext(context);
    setBinding(binding);
    setClearMapAfterChange(false);
    this.binding = binding;
    this.context = context;
    update();
    fetchRouteIdAndTrack();
  }

  private void fetchRouteIdAndTrack() {
    Application application = Application.create(context);
    DatabaseService databaseService = new DatabaseService(context);
    Disposable disposable = databaseService.getDatabase().dbBusDAO().getBusLineId(binding.busLine)
      .subscribeOn(application.subscribeScheduler())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(this::fetchTrack, throwable -> {
        Log.e("TEST", "TODO1: On Error Message" + throwable.toString());
        throwable.printStackTrace();
      });

    getCompositeDisposable().add(disposable);
  }

  private void fetchTrack(int lineId) {
    Application application = Application.create(context);
    Disposable disposable = MpkJsonRepository.getInstance().getMpkService().getTracks(lineId, binding.busLine, 1)
      .subscribeOn(application.subscribeScheduler())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(jsonArray -> {
        this.liveBusStops = DataHelper.parseRouteBusStops(jsonArray.get(0).getAsJsonArray());
        this.routeHolders = DataHelper.parsePolylines(jsonArray.get(2).getAsJsonArray());
        this.routeWariants = DataHelper.parseRouteWariants(jsonArray.get(3).getAsJsonArray());
        changeDataSet();
      }, throwable -> {
        Log.e("TEST", "TODO2: On Error Message" + throwable.toString());
        throwable.printStackTrace();
      });

    getCompositeDisposable().add(disposable);
  }

  private void fetchRealVehiclePosition() {
    String busId = String.valueOf(binding.busId);
    if (binding.busId == 0) {
      busId = "";
    }
    Application application = Application.create(context);
    Disposable disposable = MpkXmlRepository.getInstance().getMpkService().getVehicles(binding.busLine, busId)
      .subscribeOn(application.subscribeScheduler())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(liveJsonVehicles -> {
        addMarkersInBackground(DataHelper.parseJsonToVehicle(liveJsonVehicles), new MarkerBus());
      }, throwable -> {
        Log.e("TEST", "TODO2: On Error Message" + throwable.toString());
        throwable.printStackTrace();
      });

    getCompositeDisposable().add(disposable);
  }

  public void update() {
    fetchRealVehiclePosition();
  }

  private void changeDataSet() {
    drawBusStopsAndPolylines();
    update();
  }

  private void drawBusStopsAndPolylines() {
    List<BusStop> busStops = new ArrayList<>();
    for (RouteWariant routeWariant : routeWariants) {
      if (routeWariant.getWariantId() == binding.variantId) {
        int getPointOnTrackSize = routeWariant.getPointsOnTrack().size();
        for (int i = 0; i < getPointOnTrackSize; i++) {
          HolderRoute routeHolder = routeHolders.get(routeWariant.getPointsOnTrack().get(i));
          BusStop busStop = null;
          BusStop nextBusStop = null;
          if (i != getPointOnTrackSize - 1) {
            busStop = liveBusStops.get(routeWariant.getBusOnTrack().get(i));
            if (i != getPointOnTrackSize - 2) {
              nextBusStop = liveBusStops.get(routeWariant.getBusOnTrack().get(i + 1));
            }
          } else {
            BusStop prevBusStop = liveBusStops.get(routeWariant.getBusOnTrack().get(i -1));
            busStop = new LiveBusStop(routeHolder.getPoints().get(routeHolder.getPoints().size() - 1).getCoords());
            busStop.setIsCity(prevBusStop.getIsCity());
          }

          List<GeoPoint> geoPoints = new ArrayList<>();
          busStops.add(busStop);
          for (RoutePoint routePoint : routeHolder.getPoints()) {
            geoPoints.add(routePoint.getCoords());
          }
          if (nextBusStop != null) {
            geoPoints.add(new GeoPoint(nextBusStop.getLat(), nextBusStop.getLng()));
          }
          MapDepartureDetailsFragment.drawPolylines(binding.map, context, geoPoints, MapDepartureDetailsFragment.getTrackColor(busStop.getIsCity()));
        }

        for (BusStop busStop : busStops) {
          MyMarker myMarker = new MarkerBusStop();
          myMarker.setMarkerObject(busStop);
          Marker marker = MapDepartureDetailsFragment.drawMarker(binding.map, context, myMarker);
          myMarker.setMarker(marker);
          MapFragment.setUpMarker(binding.map, context, marker, myMarker);
        }
        break;
      }
    }
  }

  public void reset() {
    getCompositeDisposable().dispose();
    setCompositeDisposable(null);
    context = null;
  }
}
