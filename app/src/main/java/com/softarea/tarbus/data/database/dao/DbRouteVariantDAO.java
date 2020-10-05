package com.softarea.tarbus.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.softarea.tarbus.data.model.DatabaseBusStop;
import com.softarea.tarbus.data.model.DatabaseDepartue;
import com.softarea.tarbus.data.model.DatabaseSchedule;

import java.util.List;

@Dao
public interface DbRouteVariantDAO {
  @Query("SELECT opis_trasy FROM Wariant_trasy WHERE id_przystanku = :busStopId AND id_wariant_trasy = :wariantTrasy ")
  String getDestinationByRouteId(int busStopId, int wariantTrasy);
}
