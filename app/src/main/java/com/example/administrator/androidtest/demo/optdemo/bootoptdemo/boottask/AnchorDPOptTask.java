package com.example.administrator.androidtest.demo.optdemo.bootoptdemo.boottask;

import android.util.Log;

import androidx.annotation.NonNull;

import com.bear.libcommon.util.TimeRecordUtil;
import com.example.administrator.androidtest.demo.optdemo.bootoptdemo.ClassPreloadExecutor;

public class AnchorDPOptTask extends BaseBootTask {
    public AnchorDPOptTask() {
        super(BootConstant.TASK_TYPE_ANCHORDPOPT, true);
        setPriority(BootConstant.PRIORITY_HIGH);
    }

    @Override
    protected void run(@NonNull String s) {
        // 类预加载优化(Activity，启动相关耗时的类)
        TimeRecordUtil.markStart("AnchorDPOptTask");
        ClassPreloadExecutor.doPreload();
        sleep(2000);
        Log.d(TAG, "run: AnchorDPOptTask cost " + TimeRecordUtil.getDuration("AnchorDPOptTask") + "ms");
    }
}
