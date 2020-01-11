package com.example.libframework.Component;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;

import com.example.liblog.SLog;

public final class ActLifeDebug implements GenericLifecycleObserver {
    private String TAG = "ActLifeDebug";

    public ActLifeDebug(String tag) {
        TAG = TAG + "-" + tag;
    }

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        SLog.d(TAG, "event = " + event);
    }
}
