package com.softarea.tarbus.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface DbRouteVariantDAO {
  @Query("SELECT opis_trasy FROM Wariant_trasy WHERE id_przystanku = :busStopId AND id_wariant_trasy = :wariantTrasy ")
  String getDestinationByRouteId(int busStopId, int wariantTrasy);
}
