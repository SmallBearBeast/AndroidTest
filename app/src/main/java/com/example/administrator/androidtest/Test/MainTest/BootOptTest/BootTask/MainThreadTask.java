package com.example.administrator.androidtest.Test.MainTest.BootOptTest.BootTask;

import android.util.Log;

import androidx.annotation.NonNull;

public class MainThreadTask extends BaseBootTask {
    public MainThreadTask() {
        super(BootConstant.TASK_TYPE_MAINTHREAD, false);
    }

    @Override
    protected void run(@NonNull String s) {
        // 必须在主线程初始化的sdk，Post到主线程下一个消息处理时机，保证启动和绘制消息优先完成。
        long costTime = 1000L;
        sleep(costTime);
        Log.d(TAG, "run: MainThreadTask cost " + costTime + "ms");
    }
}
