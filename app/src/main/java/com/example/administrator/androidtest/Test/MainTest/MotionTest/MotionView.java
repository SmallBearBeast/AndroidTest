package com.example.administrator.androidtest.Test.MainTest.MotionTest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.androidtest.Test.OtherUtil;

public class MotionView extends View {
    private static final String TAG = "MotionView";
    public MotionView(Context context) {
        super(context);
    }

    public MotionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent: eventName = " + OtherUtil.toMotionEventName(event));
        boolean result = super.dispatchTouchEvent(event);
        Log.d(TAG, "dispatchTouchEvent: result = " + result);
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
