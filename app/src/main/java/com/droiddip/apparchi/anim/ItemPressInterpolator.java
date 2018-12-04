package com.athlepic.app.anim;

import android.view.animation.Interpolator;

public class ItemPressInterpolator implements Interpolator {
    private final float mCycles = 0.5f;

    @Override
    public float getInterpolation(final float input) {
        return (float) Math.sin(2.0f * mCycles * Math.PI * input);
    }
}
