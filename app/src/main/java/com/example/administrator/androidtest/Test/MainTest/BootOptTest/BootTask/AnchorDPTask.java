package com.example.administrator.androidtest.Test.MainTest.BootOptTest.BootTask;

import android.util.Log;

import androidx.annotation.NonNull;

public class AnchorDPTask extends BaseBootTask {
    public AnchorDPTask() {
        super(BootConstant.TASK_TYPE_ANCHORDP, true);
        setPriority(BootConstant.PRIORITY_HIGH);
    }

    @Override
    protected void run(@NonNull String s) {
        // 初始化模块，初始化Third sdk(里面可能有一些反射操作，IO等耗时操作)，配置初始化。
        long costTime = 1000L;
        sleep(costTime);
        Log.d(TAG, "run: AnchorDPTask cost " + costTime + "ms");
    }
}
