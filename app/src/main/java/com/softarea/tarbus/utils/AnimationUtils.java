package com.softarea.tarbus.utils;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

public class AnimationUtils {
  public static Animation getBlinkAnimation() {
    Animation animation = new AlphaAnimation(1, 0);         // Change alpha from fully visible to invisible
    animation.setDuration(300);                             // duration - half a second
    animation.setInterpolator(new LinearInterpolator());    // do not alter animation rate
    animation.setRepeatCount(60);                            // Repeat animation infinitely
    animation.setRepeatMode(Animation.REVERSE);             // Reverse animation at the end so the button will fade back in

    return animation;
  }
}
