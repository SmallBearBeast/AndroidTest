package com.example.administrator.androidtest.demo.ViewDemo.MotionTest;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;

import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.other.OtherUtil;

public class MotionEventDemoActivity extends ComponentActivity {
    @Override
    protected int layoutId() {
        return R.layout.act_motion;
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

    @Override
    protected void onUserLeaveHint() {
        Log.d(TAG, "onUserLeaveHint");
        super.onUserLeaveHint();
    }

    @Override
    public void onUserInteraction() {
        Log.d(TAG, "onUserInteraction");
        super.onUserInteraction();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MotionEventDemoActivity.class));
    }
}
