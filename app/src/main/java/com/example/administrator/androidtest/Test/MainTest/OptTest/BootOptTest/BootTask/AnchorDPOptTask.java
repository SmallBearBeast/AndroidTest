package com.example.administrator.androidtest.Test.MainTest.OptTest.BootOptTest.BootTask;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.administrator.androidtest.Test.MainTest.OptTest.BootOptTest.ClassPreloadExecutor;
import com.example.libbase.Util.TimeUtil;

public class AnchorDPOptTask extends BaseBootTask {
    public AnchorDPOptTask() {
        super(BootConstant.TASK_TYPE_ANCHORDPOPT, true);
        setPriority(BootConstant.PRIORITY_HIGH);
    }

    @Override
    protected void run(@NonNull String s) {
        // 类预加载优化(Activity，启动相关耗时的类)
        TimeUtil.markStart("AnchorDPOptTask");
        ClassPreloadExecutor.doPreload();
        sleep(2000);
        Log.d(TAG, "run: AnchorDPOptTask cost " + TimeUtil.getDuration("AnchorDPOptTask") + "ms");
    }
}
