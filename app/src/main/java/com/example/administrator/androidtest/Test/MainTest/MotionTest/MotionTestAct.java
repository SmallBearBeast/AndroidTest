package com.example.administrator.androidtest.Test.MainTest.MotionTest;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;

import com.bear.libcomponent.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.OtherUtil;

public class MotionTestAct extends ComponentAct {
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
        context.startActivity(new Intent(context, MotionTestAct.class));
    }
}
