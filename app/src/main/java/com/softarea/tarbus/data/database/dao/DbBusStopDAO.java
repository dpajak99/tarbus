package com.softarea.tarbus.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.softarea.tarbus.data.model.DatabaseBusStop;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface DbBusStopDAO {
  @Query("SELECT * FROM Przystanek_autobusowy")
  Observable<List<DatabaseBusStop>> getAll();

  @Query("SELECT * FROM Przystanek_autobusowy WHERE id_przystanku = :id")
  DatabaseBusStop getBusStopById(int id);

  @Query("SELECT * FROM Przystanek_autobusowy WHERE latitude between :latitude AND longitude between (:latitude + 0.0005) and (:longitude + 0.0005)")
  DatabaseBusStop getBusStopByCoords(double latitude, double longitude);
}
