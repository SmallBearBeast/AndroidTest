package com.example.administrator.androidtest.Test.MainTest.BootOptTest.BootTask;

import android.util.Log;

import androidx.annotation.NonNull;

public class ImmediateTask extends BaseBootTask {
    public ImmediateTask() {
        super(BootConstant.TASK_TYPE_IMMEDIATE, false);
    }

    @Override
    protected void run(@NonNull String s) {
        // 初始化crash sdk，日志sdk，统计sdk
        long costTime = 1000L;
        sleep(costTime);
        Log.d(TAG, "run: ImmediateTask cost " + costTime + "ms");
    }
}
