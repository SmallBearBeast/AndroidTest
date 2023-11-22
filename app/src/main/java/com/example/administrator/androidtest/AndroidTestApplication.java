package com.example.administrator.androidtest;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.androidtest.Test.MainTest.OptTest.BootOptTest.BootTaskManager;
import com.example.administrator.androidtest.Test.MainTest.OptTest.BootOptTest.MonitorClassLoader;

import java.util.HashMap;
import java.util.Map;

public class AndroidTestApplication extends Application {

    private static final String TAG = "AndroidTestApplication";
    private static Context mContext;
    public static Map<String, Boolean> FragVisibiableMap = new HashMap<>();

    @Override
    protected void attachBaseContext(Context base) {
        BootTaskManager.getInstance().logColdStartUp();
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MonitorClassLoader.hook(this, true);
        Log.i(TAG, "onCreate: enter");
        mContext = this;
        registerActivityLifecycleCallbacks(new Callback());

        BootTaskManager.getInstance().init();
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

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
//        Log.d(TAG, "getSharedPreferences: name = " + name + ", mode = " + mode);
//        SharedPreferences sp = super.getSharedPreferences(name, mode);
//        return PackMMKV.getSharedPreferences(this, name, sp);
        return super.getSharedPreferences(name, mode);
    }
}
