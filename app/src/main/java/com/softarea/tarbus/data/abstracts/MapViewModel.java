package com.softarea.tarbus.data.abstracts;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import com.softarea.tarbus.data.interfaces.MapDataBinding;
import com.softarea.tarbus.data.interfaces.MyMarker;
import com.softarea.tarbus.ui.main.view.MapMainFragment;

import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.disposables.CompositeDisposable;


public abstract class MapViewModel extends Observable implements com.softarea.tarbus.data.interfaces.MapViewModel {
  private Context context;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private List<MyMarker> myMarkers = new ArrayList<>();
  private MapDataBinding binding;
  private boolean clearMapAfterChange = true;
  private AsyncTaskLoader asyncTaskLoader;

  protected CompositeDisposable getCompositeDisposable() {
    return compositeDisposable;
  }

  protected void setCompositeDisposable(CompositeDisposable compositeDisposable) {
    this.compositeDisposable = compositeDisposable;
  }

  protected void setClearMapAfterChange( boolean status ) {
    this.clearMapAfterChange = status;
  }

  protected void setBinding(MapDataBinding mapDataBinding) {
    this.binding = mapDataBinding;
  }

  protected void setContext(Context context) {
    this.context = context;
  }

  protected AsyncTaskLoader getAcyncTaskLoader() {
    return asyncTaskLoader;
  }

  private void setMyMarkersList(List<MyMarker> myMarkers) {
    this.myMarkers = myMarkers;
  }

  public abstract void update();

  protected void addMarkersInBackground(Object objects, MyMarker markerTemplate) {
    this.asyncTaskLoader = new AsyncTaskLoader(context) {
      @Override
      public Object loadInBackground() {
        List<?> tmp = (List<?>) objects;
        boolean isToUpdate = true;
        if (myMarkers.size() != ((List<?>) objects).size() || !markerTemplate.isToUpdate() || myMarkers.size() == 0) {
          if( clearMapAfterChange ) {
            binding.getMap().getOverlayManager().clear();
          } else {
            for( MyMarker myMarker : myMarkers) {
              binding.getMap().getOverlayManager().remove(myMarker.getMarker());
            }
          }
          myMarkers.clear();
          isToUpdate = false;
        }
        for (int i = 0; i < tmp.size(); i++) {
          Object object = tmp.get(i);
          MyMarker myMarker = markerTemplate.getInstance();
          myMarker.setMarkerObject(object);
          if (isToUpdate) {
            myMarkers.get(i).update(context, myMarker);
          } else {
            if ( !asyncTaskLoader.isAbandoned()) {
              Marker marker = MapMainFragment.drawMarker(binding.getMap(), context, myMarker);
              myMarker.setMarker(marker);
              MapFragment.setUpMarker(binding.getMap(), context, marker, myMarker);
              myMarkers.add(myMarker);
            }
          }
        }
        setChanged();
        notifyObservers();
        return null;
      }
    };
    asyncTaskLoader.forceLoad();
  }

  public void stopAsyncTask() {
    this.asyncTaskLoader.abandon();
  }

  public void reset() {
    stopAsyncTask();
    compositeDisposable.dispose();
    compositeDisposable = null;
    context = null;
  }
}
