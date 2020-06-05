package com.example.administrator.androidtest.Test.ViewPagerTest;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class VerticalPageTransformer implements VerticalViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        position = position - ((page.getLeft() % page.getWidth()) * 1f / page.getWidth());
        if (position < -1) {
            page.setAlpha(0);
        } else if (position <= 1) {
            page.setAlpha(1);
            page.setTranslationX(page.getWidth() * -position);
            page.setTranslationY(page.getHeight() * position);
        } else {
            page.setAlpha(0);
        }
    }
}
