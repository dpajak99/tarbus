package com.softarea.tarbus.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.softarea.tarbus.data.database.dao.DbBusDAO;
import com.softarea.tarbus.data.database.dao.DbBusStopDAO;
import com.softarea.tarbus.data.database.dao.DbDepartueDAO;
import com.softarea.tarbus.data.database.dao.DbRouteVariantDAO;
import com.softarea.tarbus.data.model.DatabaseBusStop;
import com.softarea.tarbus.data.model.DatabaseDepartueIn;
import com.softarea.tarbus.data.model.DatabaseRoute;
import com.softarea.tarbus.data.model.DatabaseRouteVariant;

@Database(entities = {DatabaseDepartueIn.class, DatabaseRouteVariant.class, DatabaseRoute.class, DatabaseBusStop.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
  public abstract DbBusDAO dbBusDAO();
  public abstract DbBusStopDAO dbBusStopDAO();
  public abstract DbDepartueDAO dbDepartueDAO();
  public abstract DbRouteVariantDAO dbRouteVariantDAO();
}
