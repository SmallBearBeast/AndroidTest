package com.example.administrator.androidtest.Test.MainTest.BootOptTest.BootTask;

import android.util.Log;

import androidx.annotation.NonNull;

public class AnchorDPOptTask extends BaseBootTask {
    public AnchorDPOptTask() {
        super(BootConstant.TASK_TYPE_ANCHORDPOPT, true);
        setPriority(BootConstant.PRIORITY_HIGH);
    }

    @Override
    protected void run(@NonNull String s) {
        // 类预加载优化(Activity，启动相关耗时的类)
        long costTime = 1000L;
        sleep(costTime);
        Log.d(TAG, "run: AnchorDPOptTask cost " + costTime + "ms");
    }
}
