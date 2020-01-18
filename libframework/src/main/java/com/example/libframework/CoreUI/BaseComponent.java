package com.example.libframework.CoreUI;

import android.view.View;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.annotation.CallSuper;


public abstract class BaseComponent<M, T extends ViewSet> implements IComponent {
    protected T mViewSet;
    protected M mMain;

    public BaseComponent() {
        mViewSet = createViewSet();
    }

    void attachView(View contentView) {
        if (mViewSet != null && contentView != null) {
            mViewSet.attachView(contentView);
        }
    }

    void attachMain(M main) {
        mMain = main;
    }

    protected void onCreate() {

    }

    protected void onStart() {

    }


    protected void onResume() {

    }


    protected void onPause() {

    }


    protected void onStop() {

    }

    protected T createViewSet() {
        return null;
    }

    @CallSuper
    protected void onDestroy() {
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
            onDestroy();
        }
    }
}
