package com.softarea.tarbus.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.softarea.tarbus.data.model.DatabaseRoute;

import java.util.List;

@Dao
public interface DbBusDAO {
  @Query("SELECT DISTINCT numer_linii FROM Autobus, Odjazd WHERE Odjazd.id_przystanku = :busStopId AND Autobus.id_autobusu = Odjazd.id_autobusu")
  List<String> getBusLines(int busStopId);

  @Query("SELECT numer_linii FROM Autobus WHERE id_autobusu = :lineId")
  int getBusLineFromId(int lineId);

  @Query("SELECT id_autobusu FROM Autobus WHERE numer_linii = :line")
  int getBusLineId(String line);

  @Query("SELECT * FROM Autobus")
  List<DatabaseRoute> getAll();

  @Query("SELECT * FROM Autobus WHERE numer_linii = :id")
  DatabaseRoute getRouteByBusLine(int id);
}
