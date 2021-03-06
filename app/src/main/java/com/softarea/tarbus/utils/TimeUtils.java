package com.softarea.tarbus.utils;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.softarea.tarbus.R;

import java.util.Calendar;

public class TimeUtils {
  public static String min2HHMM(int n) {
    int t = n / 60;
    int i = n - t * 60;

    return (t < 10 ? " " + t : t) + ":" + (i < 10 ? "0" + i : i);
  }

  public static String sec2HHMM(int n) {
    n = n % 86400;
    int i = Integer.parseInt(String.valueOf(n / 3600));
    int t = Integer.parseInt(String.valueOf(n % 3600 / 60));
    return i + ":" + (t < 10 ? "0" + t : t);
  }

  public static String calcDelayInfo(FragmentActivity activity, int value) {
    if (value < 0) {
      return activity.getString(R.string.current_acceleration);
    } else {
      return activity.getString(R.string.current_delay);
    }
  }

  public static String calcDelayValue(int value) {
    value = MathUtils.makePositive(value);
    if (value / 60 < 1) {
      return " < 1 min";
    } else if (value / 60 >= 60) {
      int hours = (int) value / 3600;
      int minutes = (int) (value - hours * 3600) / 60;
      return " " + hours + " h " + minutes + " min";
    } else {
      return " " + (int) (value / 60) + " min";
    }
  }

  public static String getCurrentTime() {
    Calendar rightNow = Calendar.getInstance();
    return StringUtils.join(makeZero(rightNow.get(Calendar.HOUR_OF_DAY)), ":", makeZero(rightNow.get(Calendar.MINUTE)));
  }

  public static int getCurrentTimeInMin() {
    Calendar rightNow = Calendar.getInstance();
    return rightNow.get(Calendar.HOUR_OF_DAY) * 60 + rightNow.get(Calendar.MINUTE);
  }

  public static String getCurrentDate() {
    // yyyy-mm-dd
    Calendar rightNow = Calendar.getInstance();
    return StringUtils.join(makeZero(rightNow.get(Calendar.YEAR)), "-", makeZero(rightNow.get(Calendar.MONTH)), "-", makeZero(rightNow.get(Calendar.DAY_OF_MONTH)));
  }

  public static String makeZero(int content) {
    String text = String.valueOf(content);
    if (text.length() == 1) {
      return StringUtils.join("0", text);
    }
    return text;
  }

  public static String getCurrentDayType() {
    Calendar calendar = Calendar.getInstance();
    int day = calendar.get(Calendar.DAY_OF_WEEK);
    if (day <= 5) {
      return "RO";
    } else if (day == 6) {
      return "WS";
    } else {
      return "SW";
    }
  }

  public static String translateDayShortcutToDayName(String shortcut) {
    switch (shortcut) {
      case "RO":
        return "DNI ROBOCZE";
      case "WS":
        return "SOBOTY";
      case "SW":
        return "DNI ŚWIĄTECZNE";
      default:
        return "INNE";
    }
  }

  public static int liveTimeToMin(String timeInString) {
    //TODO: Prettify it
    StringBuilder result = new StringBuilder();
    Log.i("TEST", "DUPA1" +timeInString.charAt(0) + timeInString.charAt(1) + timeInString.charAt(2) + "");
    timeInString = StringUtils.replaceHTML(timeInString);
    if (timeInString.contains("min")) {
      Log.i("TEST", "DUPA2" +timeInString.charAt(0) + timeInString.charAt(1) + timeInString.charAt(2) + "");
      for (int i = 0; i < timeInString.length(); i++) {
        char a = timeInString.charAt(i);
        if (a == '<') {
          Log.i("TEST", "<1min " + TimeUtils.getCurrentTimeInMin());
          return TimeUtils.getCurrentTimeInMin();
        } else if (a == ' ') {
          Log.i("TEST", timeInString.charAt(0) + timeInString.charAt(1) + timeInString.charAt(2) + "");
          return TimeUtils.getCurrentTimeInMin() + Integer.parseInt(result.toString());
        } else {
          result.append(a);
        }
      }
    } else {
      int one = Character.getNumericValue(timeInString.charAt(0));
      int two = Character.getNumericValue(timeInString.charAt(1));
      int three = Character.getNumericValue(timeInString.charAt(3));
      int four = Character.getNumericValue(timeInString.charAt(4));

      return (one * 10 + two) * 60 + (three * 10) + four;
    }
    return 0;
  }
}
