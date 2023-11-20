package com.example.administrator.androidtest.Test.MainTest.BootOptTest.BootTask;

import android.util.Log;

import androidx.annotation.NonNull;

public class BasicBgThreadTask extends BaseBootTask {
    public BasicBgThreadTask() {
        super(BootConstant.TASK_TYPE_BASIC_BGTHREAD, true);
    }

    @Override
    protected void run(@NonNull String s) {
        // 网络初始化，so库加载，数据库初始化，文件操作初始化(读取文件配置内容)，sp预加载(内部启一个线程去加载的)
        long costTime = 1000L;
        sleep(costTime);
        Log.d(TAG, "run: BasicBgThreadTask cost " + costTime + "ms");
    }
}
