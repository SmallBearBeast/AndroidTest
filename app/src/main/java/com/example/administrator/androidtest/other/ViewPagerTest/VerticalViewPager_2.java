package com.example.administrator.androidtest.other.ViewPagerTest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

public class VerticalViewPager_2 extends ViewPager {
    private static final String TAG = "VerticalViewPager";

    public VerticalViewPager_2(@NonNull Context context) {
        this(context, null);
    }

    public VerticalViewPager_2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOverScrollMode(OVER_SCROLL_NEVER);
//        try {
//            Class cls = this.getClass().getSuperclass();
//            Field distanceField = cls.getDeclaredField("mFlingDistance");
//            distanceField.setAccessible(true);
//            distanceField.setInt(this, distanceField.getInt(this) / 40);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            Class cls = this.getClass().getSuperclass();
            Field minVelocityField = cls.getDeclaredField("mMinimumVelocity");
            minVelocityField.setAccessible(true);
            minVelocityField.setInt(this, minVelocityField.getInt(this) / 50);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            Class cls = this.getClass().getSuperclass();
//            Field maxVelocityField = cls.getDeclaredField("mMaximumVelocity");
//            maxVelocityField.setAccessible(true);
//            maxVelocityField.setInt(this, maxVelocityField.getInt(this) * 10);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Class cls = this.getClass().getSuperclass();
//            Field slopField = cls.getDeclaredField("mTouchSlop");
//            slopField.setAccessible(true);
//            slopField.setInt(this, slopField.getInt(this) / 10);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        MotionEvent transposeEv = MotionEvent.obtain(ev);
        float newX = (ev.getY() / getHeight()) * getWidth();
        float newY = (ev.getX() / getWidth()) * getHeight();
        transposeEv.setLocation(newX, newY);
//        transposeEv.
        Log.d(TAG, "dispatchTouchEvent: evGetX = " + ev.getX() + ", evGetY = " + ev.getY() + ", transposeEvGetX = " + transposeEv.getX() + ", transposeEvGetY = " + transposeEv.getY());
        return super.dispatchTouchEvent(transposeEv);
    }
}
