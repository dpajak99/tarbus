package com.softarea.tarbus.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.softarea.tarbus.data.model.DatabaseDepartue;
import com.softarea.tarbus.data.model.DatabaseSchedule;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface DbDepartueDAO {
  @Query("SELECT godzina_odjazdu_in_sec, dodatkowe_oznaczenia_string FROM Odjazd WHERE id_przystanku = :busStopId AND id_autobusu = :busLineId AND typ_dnia = :dayType")
  List<DatabaseSchedule> getSchedule(int busStopId, int busLineId, String dayType);

  @Query("SELECT * FROM Odjazd WHERE id_przystanku = :busStopId AND typ_dnia = :dayType AND godzina_odjazdu_in_sec > :currentTime ORDER BY godzina_odjazdu_in_sec LIMIT 12")
  Observable<List<DatabaseDepartue>> getNextDepartues(int busStopId, int currentTime, String dayType);
}
