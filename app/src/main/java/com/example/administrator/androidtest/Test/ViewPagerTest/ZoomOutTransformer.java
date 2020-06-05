package com.example.administrator.androidtest.Test.ViewPagerTest;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class ZoomOutTransformer implements VerticalViewPager.PageTransformer {
    private static final String TAG = "ZoomOutTransformer";

    @Override
    public void transformPage(@NonNull View view, float position) {
        float oldPosition = position;
        // Remove the effects of padding of ViewPager
        position = position - ((view.getLeft() % view.getWidth()) * 1f / view.getWidth());
        Log.d(TAG, "transformPage: newPosition = " + position + ", oldPosition = " + oldPosition);
        view.setTranslationX(-view.getWidth() * position);
        final float scale = 1f + Math.abs(position);
        view.setScaleX(scale);
        view.setScaleY(scale);
        view.setPivotX(view.getWidth() * 0.5f);
        view.setPivotY(view.getHeight() * 0.5f);
        if (position < -1) {
            view.setAlpha(0);
        } else if (position <= 0) {
            view.setAlpha(1 + position);
        } else if (position <= 1) {
            view.setAlpha(1 - position);
        } else {
            view.setAlpha(0);
        }
    }
}
