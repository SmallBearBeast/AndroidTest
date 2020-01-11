package com.example.administrator.androidtest.Test.ViewModelTest;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.example.liblog.SLog;

public class LifeCycleTestObserver implements LifecycleObserver {
    private static final String TAG = "LifeCycleTestObserver";

    @OnLifecycleEvent(value = Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        SLog.d(TAG, "onCreate");
    }
}
