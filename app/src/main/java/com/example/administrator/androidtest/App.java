package com.example.administrator.androidtest;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.bear.libkv.AppVal.AppVal;
import com.example.administrator.androidtest.Test.ValTest.AppData;
import com.example.libbase.Util.AppInitUtil;
import com.example.libfresco.FrescoUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.HashMap;
import java.util.Map;

public class App extends Application {

    private static final String TAG = "App";
    private static Context mContext;
    public static Map<String, Boolean> FragVisibiableMap = new HashMap<>();
    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        AppInitUtil.init(this);
        FrescoUtil.init(this);
        registerActivityLifecycleCallbacks(new Callback());
        AppVal.init(this);
        AppVal.preload(AppData.TEST_1, AppData.TEST_2);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not doOnCreate your app in this process.
            return;
        }
        mRefWatcher = LeakCanary.install(this);
    }

    public static Context getContext() {
        return mContext;
    }



    static class Callback implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            String s = null;
        }

        @Override
        public void onActivityStarted(Activity activity) {
            String s = null;
        }

        @Override
        public void onActivityResumed(Activity activity) {
            String s = null;
        }

        @Override
        public void onActivityPaused(Activity activity) {
            String s = null;
        }

        @Override
        public void onActivityStopped(Activity activity) {
            String s = null;
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            String s = null;
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.e(TAG, "onActivityDestroyed: " + "class = " + activity.getClass().getSimpleName() + "     ");
            String s = null;
        }
    }

    public static RefWatcher getRefWatcher(Context context) {
        App application = (App) context.getApplicationContext();
        return application.mRefWatcher;
    }
}
