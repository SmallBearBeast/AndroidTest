package com.example.administrator.androidtest.Test.MotionActTest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.example.liblog.SLog;

public class MotionView extends View {
    private static final String TAG = "MotionView";
    public MotionView(Context context) {
        super(context);
    }

    public MotionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        SLog.d(TAG, "dispatchTouchEvent() called with: ev = [" + ev + "]");
        boolean result = super.dispatchTouchEvent(ev);
        SLog.d(TAG, "dispatchTouchEvent() called result = " + result);
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
