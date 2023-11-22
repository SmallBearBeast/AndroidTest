package com.example.administrator.androidtest.Test.MainTest.OptTest.BootOptTest.BootTask;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bear.libcomponent.component.ComponentService;
import com.bear.libkv.MmkvVal.MmkvVal;
import com.bear.libkv.SpVal.SpHelper;
import com.example.administrator.androidtest.AndroidTestApplication;
import com.example.administrator.androidtest.BuildConfig;
import com.example.administrator.androidtest.Test.MainTest.SpValHelper;
import com.example.libbase.Util.AppInitUtil;
import com.example.libbase.Util.TimeUtil;
import com.example.libfresco.FrescoUtil;

public class AnchorDPTask extends BaseBootTask {
    public AnchorDPTask() {
        super(BootConstant.TASK_TYPE_ANCHORDP, true);
        setPriority(BootConstant.PRIORITY_HIGH);
    }

    @Override
    protected void run(@NonNull String s) {
        // 初始化模块，初始化Third sdk(里面可能有一些反射操作，IO等耗时操作)，配置初始化。
        TimeUtil.markStart("AnchorDPTask");
        Context context = AndroidTestApplication.getContext();
        AppInitUtil.init(context);
        FrescoUtil.init(context);
        SpHelper.init(context);
        MmkvVal.init(context);
        SpHelper.preload(SpValHelper.SP_GLOBAL_CONFIG);
        ComponentService.get().init((Application) context);
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init((Application) context);
        Log.d(TAG, "run: AnchorDPTask cost " + TimeUtil.getDuration("AnchorDPTask") + "ms");
    }
}
