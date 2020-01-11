package com.example.libframework.Component;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.example.liblog.SLog;

public class FragLifeDebug extends FragmentManager.FragmentLifecycleCallbacks {
    private String TAG = "FragLifeDebug";

    public FragLifeDebug(String tag) {
        TAG = TAG + "-" + tag;
    }

    @Override
    public void onFragmentPreAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
        SLog.d(TAG, "onFragmentPreAttached: ");
    }

    @Override
    public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
        SLog.d(TAG, "onFragmentAttached: ");
    }

    @Override
    public void onFragmentPreCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        SLog.d(TAG, "onFragmentPreCreated: ");
    }

    @Override
    public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        SLog.d(TAG, "onFragmentCreated: ");
    }

    @Override
    public void onFragmentActivityCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        SLog.d(TAG, "onFragmentActivityCreated: ");
    }

    @Override
    public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
        SLog.d(TAG, "onFragmentViewCreated: ");
    }

    @Override
    public void onFragmentStarted(@NonNull FragmentManager fm, @NonNull Fragment f) {
        SLog.d(TAG, "onFragmentStarted: ");
    }

    @Override
    public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        SLog.d(TAG, "onFragmentResumed: ");
    }

    @Override
    public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
        SLog.d(TAG, "onFragmentPaused: ");
    }

    @Override
    public void onFragmentStopped(@NonNull FragmentManager fm, @NonNull Fragment f) {
        SLog.d(TAG, "onFragmentStopped: ");
    }

    @Override
    public void onFragmentSaveInstanceState(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Bundle outState) {
        SLog.d(TAG, "onFragmentSaveInstanceState: ");
    }

    @Override
    public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        SLog.d(TAG, "onFragmentViewDestroyed: ");
    }

    @Override
    public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        SLog.d(TAG, "onFragmentDestroyed: ");
    }

    @Override
    public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
        SLog.d(TAG, "onFragmentDetached: ");
    }
}
