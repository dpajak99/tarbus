package com.softarea.tarbus.utils;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class HardwareUtils {

  public static void vibrate(Context context, int time) {
    Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    // Vibrate for 500 milliseconds
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      v.vibrate(VibrationEffect.createOneShot(time, VibrationEffect.DEFAULT_AMPLITUDE));
    } else {
      //deprecated in API 26
      v.vibrate(500);
    }
  }
}
