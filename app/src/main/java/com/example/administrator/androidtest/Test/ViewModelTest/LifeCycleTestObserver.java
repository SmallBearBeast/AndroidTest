package com.example.administrator.androidtest.Test.ViewModelTest;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.liblog.SLog;

public class LifeCycleTestObserver implements LifecycleObserver {
    private static final String TAG = "LifeCycleTestObserver";

    @OnLifecycleEvent(value = Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        SLog.d(TAG, "onCreate");
    }
}
