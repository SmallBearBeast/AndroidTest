package com.example.administrator.androidtest;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.androidtest.frag.visibility.BaseVisiableFrag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App extends Application {

    private static final String TAG = "App";
    public static Map<String, Boolean> FragVisibiableMap = new HashMap<>();

    public static BaseVisiableFrag.FragVisiableListener fragVisiableListener = null;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new Callback());
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
}
