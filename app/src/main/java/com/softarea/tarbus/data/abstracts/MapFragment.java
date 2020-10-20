package com.softarea.tarbus.data.abstracts;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.softarea.tarbus.BuildConfig;
import com.softarea.tarbus.R;
import com.softarea.tarbus.data.constant.MapConst;
import com.softarea.tarbus.data.interfaces.MapDataBinding;
import com.softarea.tarbus.data.interfaces.MapViewModel;
import com.softarea.tarbus.data.interfaces.MyMarker;
import com.softarea.tarbus.utils.HardwareUtils;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.infowindow.InfoWindow;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public abstract class MapFragment extends Fragment implements Observer {
  private MapView map;
  private MapViewModel mapMainViewModel;
  private IMapController mapController;
  private MyLocationNewOverlay locationOverlay;
  public static Marker actualOpenedMarker;

  public MapFragment() {
  }

  protected void setUpFragment(MapDataBinding binding, MapViewModel mapMainViewModel) {
    Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
    Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
    this.map = binding.getMap();
    this.mapMainViewModel = mapMainViewModel;
    this.mapController = binding.getMapController();
    this.locationOverlay = binding.getLocationOverlay();
    setUpMap();
    setUpLocalization();
  }

  final Handler handler = new Handler();

  protected void startAutoRefreshing() {
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        mapMainViewModel.update();
        HardwareUtils.vibrate(requireContext(), 50);
        handler.postDelayed(this, 10000);
      }
    };
    handler.postDelayed(runnable, 0);
  }

  protected void stopAutoRefreshing() {
    handler.removeCallbacksAndMessages(null);
  }

  protected void setupObserver(Observable observable) {
    observable.addObserver(this);
  }

  protected void setUpMap() {
    map.setTileSource(TileSourceFactory.MAPNIK);
    map.setUseDataConnection(true);
    map.setMultiTouchControls(true);
    map.setMaxZoomLevel(18.0);
    map.setMinZoomLevel(12.0);
    mapController.setZoom(15.0);
    mapController.setCenter(MapConst.CENTER);
    map.getTileProvider().getTileCache().getProtectedTileComputers().clear();
  }

  protected void setUpLocalization() {
    locationOverlay.enableMyLocation();
    locationOverlay.setPersonIcon(BitmapFactory.decodeResource(requireContext().getResources(), MapConst.locationIcon));
    map.getOverlays().add(locationOverlay);
  }

  public static Marker drawMarker(MapView map, Context context, MyMarker marker) {
    Marker startMarker = new Marker(map);
    startMarker.setPosition(marker.getPosition());
    if (marker.isAnchor()) {
      startMarker.setAnchor(marker.getVerticalAnchor(), marker.getHorizontalAnchor());
    }
    if (marker.getIconId() != 0) {
      startMarker.setIcon(ContextCompat.getDrawable(context, marker.getIconId()));
    } else {
      startMarker.setIcon(new BitmapDrawable(context.getResources(), marker.getIcon(context)));
    }
    map.getOverlays().add(startMarker);

    return startMarker;
  }

  public static void setUpMarker(MapView map, Context context, Marker marker, MyMarker myMarker) {
    marker.setInfoWindow(new InfoWindow(myMarker.getInfoWindowView(), map) {
      @Override
      public void onOpen(Object item) {
        myMarker.onOpen(context);
      }

      @Override
      public void onClose() {
        myMarker.onClose();
      }
    });

    marker.setOnMarkerClickListener((clickedMarker, mapView) -> {
      mapView.getController().animateTo(marker.getPosition());
      if (actualOpenedMarker != null) {
        closeMarker(actualOpenedMarker);
      }
      actualOpenedMarker = clickedMarker;
      clickedMarker.showInfoWindow();
      return true;
    });
  }

  public static void closeMarker(Marker actualOpenedMarker) {
    if (actualOpenedMarker != null) {
      actualOpenedMarker.closeInfoWindow();
    }
  }

  public static int getTrackColor(int isCity) {
    if (isCity == 0) {
      return R.color.color_city;
    } else {
      return R.color.color_zone;
    }
  }

  public static void drawPolylines(MapView map, Context context, List<GeoPoint> geoPoints, int color) {
    Polyline line = new Polyline();
    line.setPoints(geoPoints);
    line.getOutlinePaint().setStrokeCap(Paint.Cap.ROUND);
    line.getOutlinePaint().setColor(context.getColor(color));
    map.getOverlayManager().add(line);
  }

  @Override
  public void update(Observable observable, Object o) {
    map.invalidate();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    stopAutoRefreshing();
    mapMainViewModel.reset();
  }

}
