package com.example.administrator.androidtest.Test.MainTest.ViewDemo.MotionTest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.example.administrator.androidtest.Test.OtherUtil;

public class MotionEventViewGroup extends FrameLayout {
    private static final String TAG = "MotionViewGroup";
    public MotionEventViewGroup(Context context) {
        super(context);
    }

    public MotionEventViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent: eventName = " + OtherUtil.toMotionEventName(event));
        boolean result = super.dispatchTouchEvent(event);
        Log.d(TAG, "dispatchTouchEvent: result = " + result);
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.d(TAG, "onInterceptTouchEvent: eventName = " + OtherUtil.toMotionEventName(event));
        boolean result = super.onInterceptTouchEvent(event);
        Log.d(TAG, "onInterceptTouchEvent: result = " + result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: eventName = " + OtherUtil.toMotionEventName(event));
        boolean result = super.onTouchEvent(event);
        Log.d(TAG, "onTouchEvent: result = " + result);
        return result;
    }
}
