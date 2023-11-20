package com.example.administrator.androidtest.Test.MainTest.BootOptTest.BootTask;

import android.util.Log;

import androidx.annotation.NonNull;

public class NoAnchorDPOptTask extends BaseBootTask {
    public NoAnchorDPOptTask() {
        super(BootConstant.TASK_TYPE_NOANCHORDPOPT, true);
        setPriority(BootConstant.PRIORITY_DEFAULT);
    }

    @Override
    protected void run(@NonNull String s) {
        // 反射优化(ARouter, Retrofit)，预加载操作(图片缓存预加载，View预加载，首页数据序列化数据加载)
        long costTime = 1000L;
        sleep(costTime);
        Log.d(TAG, "run: NoAnchorDPOptTask cost " + costTime + "ms");
    }
}
