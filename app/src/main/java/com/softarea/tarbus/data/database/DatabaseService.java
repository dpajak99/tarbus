package com.softarea.tarbus.data.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseService {
  private AppDatabase database;

  public DatabaseService(Context context) {
    this.database = Room.databaseBuilder(context,
      AppDatabase.class, "tarbus.db").createFromAsset("databases/tarbus.db").allowMainThreadQueries().build();
  }

  public AppDatabase getDatabase() {
    return database;
  }
}
