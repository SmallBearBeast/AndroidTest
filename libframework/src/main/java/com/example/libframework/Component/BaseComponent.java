package com.example.libframework.Component;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.CallSuper;


public abstract class BaseComponent<M, T extends ViewSet> implements IComponent {
    protected T mViewSet;
    protected M mMain;

    public BaseComponent() {
    }

    public BaseComponent(T viewSet) {
        mViewSet = viewSet;
    }

    public void attachViewSet(T viewSet) {
        mViewSet = viewSet;
    }

    public void attachMain(M main) {
        mMain = main;
    }


    public void onCreate() {

    }


    public void onStart() {

    }


    public void onResume() {

    }


    public void onPause() {

    }


    public void onStop() {

    }

    @CallSuper
    public void onDestory() {
        mMain = null;
        if (mViewSet != null) {
            mViewSet.clear();
            mViewSet = null;
        }
    }

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_CREATE) {
            onCreate();
        } else if (event == Lifecycle.Event.ON_START) {
            onStart();
        } else if (event == Lifecycle.Event.ON_RESUME) {
            onResume();
        } else if (event == Lifecycle.Event.ON_PAUSE) {
            onPause();
        } else if (event == Lifecycle.Event.ON_STOP) {
            onStop();
        } else if (event == Lifecycle.Event.ON_DESTROY) {
            onDestory();
        }
    }
}
