package com.example.administrator.androidtest.Test.MotionActTest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.example.liblog.SLog;

public class MotionViewGroup extends FrameLayout {
    private static final String TAG = "MotionViewGroup";
    public MotionViewGroup(Context context) {
        super(context);
    }

    public MotionViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        SLog.d(TAG, "dispatchTouchEvent() called with: ev = [" + ev + "]");
        boolean result = super.dispatchTouchEvent(ev);
        SLog.d(TAG, "dispatchTouchEvent() called result = " + result);
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        SLog.d(TAG, "onInterceptTouchEvent() called with: ev = [" + ev + "]");
        boolean result = super.onInterceptTouchEvent(ev);
        SLog.d(TAG, "onInterceptTouchEvent() called result = " + result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        SLog.d(TAG, "onTouchEvent() called with: event = [" + event + "]");
        boolean result = super.onTouchEvent(event);
        SLog.d(TAG, "onTouchEvent() called result = " + result);
        return result;
    }
}
