package com.example.administrator.androidtest.Test.ViewPagerTest;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class AlphaAndScalePageTransformer implements VerticalViewPager.PageTransformer {
    private static final String TAG = "AlphaAndScalePageTransformer";
    private final float SCALE_MAX = 0.8f;
    private final float ALPHA_MAX = 0.5f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        float oldPosition = position;
        // Remove the effects of padding of ViewPager
        position = position - ((page.getLeft() % page.getWidth()) * 1f / page.getWidth());
        Log.d(TAG, "transformPage: newPosition = " + position + ", oldPosition = " + oldPosition);
        float scale = (position < 0)
                ? ((1 - SCALE_MAX) * position + 1)
                : ((SCALE_MAX - 1) * position + 1);
        float alpha = (position < 0)
                ? ((1 - ALPHA_MAX) * position + 1)
                : ((ALPHA_MAX - 1) * position + 1);
        if (position < 0) {
            page.setPivotX(page.getWidth());
            page.setPivotY(page.getHeight() / 2);
        } else {
            page.setPivotX(0);
            page.setPivotY(page.getHeight() / 2);
        }
        page.setScaleY(scale);
        page.setAlpha(Math.abs(alpha));
    }
}
