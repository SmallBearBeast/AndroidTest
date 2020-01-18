package com.example.libframework.CoreUI;

import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.example.liblog.SLog;

public class FragLifeDebug implements GenericLifecycleObserver {
    private String TAG = "FragLifeDebug";

    public FragLifeDebug(String tag) {
        TAG = TAG + "-" + tag;
    }

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        SLog.d(TAG, "event = " + event);
    }
}
