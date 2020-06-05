package com.example.administrator.androidtest.Test.ViewPagerTest;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class ParallaxTransformer implements VerticalViewPager.PageTransformer {
    private float mConfig = 0.85f;

    public ParallaxTransformer(float config) {
        mConfig = config;
    }

    public ParallaxTransformer() {

    }

    @Override
    public void transformPage(View page, float position) {
        // Remove the effects of padding of ViewPager
        position = position - ((page.getLeft() % page.getWidth()) * 1f / page.getWidth());
        int width = page.getWidth();
        if (position < -1) {
            page.setScrollX((int) (width * mConfig * -1));
        } else if (position <= 1) {
            if (position < 0) {
                page.setScrollX((int) (width * mConfig * position));
            } else {
                page.setScrollX((int) (width * mConfig * position));
            }
        } else {
            page.setScrollX((int) (width * mConfig));
        }
    }
}