package com.softarea.tarbus;

import android.content.Context;
import androidx.multidex.MultiDexApplication;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;


public class Application extends MultiDexApplication {

  private Scheduler scheduler;

  private static Application get(Context context) {
    return (Application) context.getApplicationContext();
  }

  public static Application create(Context context) {
    return Application.get(context);
  }

  public Scheduler subscribeScheduler() {
    if (scheduler == null) {
      scheduler = Schedulers.io();
    }
    return scheduler;
  }
}
