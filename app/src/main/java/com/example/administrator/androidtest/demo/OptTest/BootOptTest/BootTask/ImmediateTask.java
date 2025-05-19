package com.example.administrator.androidtest.demo.OptTest.BootOptTest.BootTask;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.libcommon.Util.TimeRecordUtil;

//import io.flutter.embedding.engine.FlutterEngine;
//import io.flutter.embedding.engine.FlutterEngineCache;
//import io.flutter.embedding.engine.dart.DartExecutor;

public class ImmediateTask extends BaseBootTask {
    public ImmediateTask() {
        super(BootConstant.TASK_TYPE_IMMEDIATE, false);
    }

    @Override
    protected void run(@NonNull String s) {
        // 初始化crash sdk，日志sdk，统计sdk
        initFlutterEngine();
    }

    private void initFlutterEngine() {
        // 需要放在主线程
        TimeRecordUtil.markStart("InitFlutterEngine");
        // Instantiate a FlutterEngine.
//        FlutterEngine flutterEngine = new FlutterEngine(AndroidTestApplication.getContext());
//        flutterEngine.getDartExecutor().executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault());
//        // Cache the FlutterEngine to be used by FlutterActivity.
//        FlutterEngineCache.getInstance().put("my_engine_id", flutterEngine);
        Log.i(TAG, "initFlutterEngine: cost time " + TimeRecordUtil.getDuration("InitFlutterEngine") + " ms");
    }
}
