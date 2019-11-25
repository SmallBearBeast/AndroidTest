package com.example.administrator.androidtest.Test.MotionActTest;

import android.util.Log;
import android.view.MotionEvent;
import com.example.administrator.androidtest.R;
import com.example.libframework.ActAndFrag.ComponentAct;
import com.example.liblog.SLog;

// TODO: 2019-11-12 diff getActionIndex between getPointerId
public class MotionTestAct extends ComponentAct {
    @Override
    protected int layoutId() {
        return R.layout.act_motion;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        SLog.d(TAG, "dispatchTouchEvent() called");
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

    @Override
    protected void onUserLeaveHint() {
        SLog.d(TAG, "onUserLeaveHint() called");
        super.onUserLeaveHint();
    }

    @Override
    public void onUserInteraction() {
        SLog.d(TAG, "onUserInteraction() called");
        super.onUserInteraction();
    }


}
