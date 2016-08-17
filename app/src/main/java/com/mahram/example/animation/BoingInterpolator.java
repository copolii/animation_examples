package com.mahram.example.animation;

import android.animation.TimeInterpolator;

/**
 Created by mahra on 2016-08-16.
 */
public class BoingInterpolator
  implements TimeInterpolator {

    private static final double CYCLE = Math.PI / 2.0;

    @Override public float getInterpolation (final float v) {
        return 4 * (v - (v * v));
    }
}

