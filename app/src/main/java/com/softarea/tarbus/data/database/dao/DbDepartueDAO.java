package com.softarea.tarbus.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.softarea.tarbus.data.model.DatabaseDepartueOut;
import com.softarea.tarbus.data.model.DatabaseSchedule;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface DbDepartueDAO {
  @Query("SELECT godzina_odjazdu_in_sec, dodatkowe_oznaczenia_string FROM Odjazd WHERE id_przystanku = :busStopId AND id_autobusu = :busLineId AND typ_dnia = :dayType")
  List<DatabaseSchedule> getSchedule(int busStopId, int busLineId, String dayType);

  @Query("SELECT id_odjazdu, numer_linii, Odjazd.id_wariant_trasy, godzina_odjazdu_in_sec, opis_trasy FROM Odjazd, Autobus, Wariant_trasy WHERE Wariant_trasy.id_przystanku = :busStopId AND Wariant_trasy.id_wariant_trasy = Odjazd.id_wariant_trasy AND Odjazd.id_autobusu = Autobus.id_autobusu AND Odjazd.id_przystanku = :busStopId AND typ_dnia = :dayType AND (godzina_odjazdu_in_sec > :currentTime OR godzina_odjazdu_in_sec < 60) ORDER BY godzina_odjazdu_in_sec")
  Observable<List<DatabaseDepartueOut>> getNextDepartues(int busStopId, int currentTime, String dayType);
}
