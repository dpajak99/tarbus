package com.softarea.tarbus.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import com.softarea.tarbus.R;
import com.softarea.tarbus.data.model.Vehicle;

import static android.graphics.Bitmap.Config.ARGB_8888;

public class ImageUtils {
  public static Bitmap createBusPinImage(Context context, Vehicle vehicle) {
    String text = vehicle.getNumerLini();
    text = StringUtils.deleteWhiteSpaces(text);
    Resources resources = context.getResources();
    float scale = resources.getDisplayMetrics().density;
    Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.vh_pin);
    bitmap = bitmap.copy(ARGB_8888, true);

    Canvas canvas = new Canvas(bitmap);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(context.getColor(R.color.colorPrimary));
    paint.setFakeBoldText(true);
    paint.setTextSize(9 * scale);
    Rect bounds = new Rect();
    paint.getTextBounds(text, 0, text.length(), bounds);

    int x = bitmap.getWidth() / 2 - 45;
    int y = bitmap.getHeight() - 70;

    Canvas secondImage = new Canvas(bitmap);
    Bitmap arrow = rotateMyBitmap( BitmapFactory.decodeResource(context.getResources(), R.drawable.vh_arrow), vehicle.getWektor());
    secondImage.drawBitmap(arrow, x, y, paint);

    x = bitmap.getWidth() / 2 - 8 * text.length();
    y = bounds.height() + 24;

    canvas.drawText(text, x, y, paint);
    return bitmap;
  }

  /*public static Bitmap createYourPositionPin(Context context, String text) {
    Resources resources = context.getResources();
    float scale = resources.getDisplayMetrics().density;
    Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_cursor);
    bitmap = bitmap.copy(ARGB_8888, true);

    Canvas canvas = new Canvas(bitmap);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(context.getColor(R.color.colorPrimary));
    paint.setFakeBoldText(true);
    paint.setTextSize(9 * scale);
    Rect bounds = new Rect();
    paint.getTextBounds(text, 0, text.length(), bounds);

    int x = bitmap.getWidth() / 2 - 7 * text.length();
    int y = bounds.height();
    canvas.drawText(text, x, y, paint);
    return bitmap;
  }

  public static Bitmap createBusChangeImage(Context context, String text) {
    Resources resources = context.getResources();
    float scale = resources.getDisplayMetrics().density;
    Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.tp_transit);
    bitmap = bitmap.copy(ARGB_8888, true);

    Canvas canvas = new Canvas(bitmap);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(context.getColor(R.color.colorPrimary));
    paint.setFakeBoldText(true);
    paint.setTextSize(9 * scale);
    Rect bounds = new Rect();
    paint.getTextBounds(text, 0, text.length(), bounds);

    int x = bitmap.getWidth() / 2 - 7 * text.length();
    int y = bounds.height() + 24;
    canvas.drawText(text, x, y, paint);
    return bitmap;
  }*/

  public static Bitmap createLongBusPinImage(Context context, Vehicle vehicle) {
    String text = vehicle.getNastNumLini();
    String departue = String.valueOf(vehicle.getIleSekDoOdjazdu());
    text = StringUtils.deleteWhiteSpaces(text);
    Resources resources = context.getResources();
    float scale = resources.getDisplayMetrics().density;
    Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.vh_pin_loop);
    bitmap = bitmap.copy(ARGB_8888, true);

    Canvas canvas = new Canvas(bitmap);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(context.getColor(R.color.colorPrimary));
    paint.setFakeBoldText(true);
    paint.setTextSize(9 * scale);
    Rect bounds = new Rect();
    paint.getTextBounds(text, 0, text.length(), bounds);

    int x = bitmap.getWidth() / 2 - 30;
    int y = bitmap.getHeight() - 60;

    Canvas secondImage = new Canvas(bitmap);
    secondImage.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.vh_clock), x, y, paint);

    x = bitmap.getWidth() / 2 - 8 * text.length();
    y = bounds.height() + 24;
    canvas.drawText(text, x, y, paint);

    String result;
    if( (int) Integer.parseInt(departue) / 60 < 1 ) {
      result = "< 1";
    } else {
      result = String.valueOf((int) Integer.parseInt(departue) / 60 );
    }
    departue = StringUtils.join("odjazd za " + result + " min");

    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(context.getColor(R.color.colorPrimary));
    paint.setTextSize(9 * scale);
    bounds = new Rect();
    paint.getTextBounds(departue, 0, departue.length(), bounds);

    x = bitmap.getWidth() / 2 - 6 * departue.length();
    y = bounds.height() + 60;
    canvas.drawText(departue, x, y, paint);


    return bitmap;
  }

  public static Bitmap rotateMyBitmap(Bitmap source, float angle)
  {
    Matrix matrix = new Matrix();
    matrix.postRotate(angle);
    return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
  }
}
