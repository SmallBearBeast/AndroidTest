package com.example.libframework.Component;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.CallSuper;
import com.example.libframework.ActAndFrag.ComponentAct;
import com.example.libframework.ActAndFrag.ComponentFrag;

public abstract class FragComponent<T extends ViewSet> implements IComponent {
    public ComponentAct mComActivity;
    public ComponentFrag mComponentFrag;
    public T mViewSet;

    public FragComponent(){}

    public FragComponent(T viewSet){
        mViewSet = viewSet;
    }

    public void attachViewSet(T viewSet){
        mViewSet = viewSet;
    }

    public void attachActivity(ComponentAct activity){
        mComActivity = activity;
    }

    public void attachFragment(ComponentFrag fragment){
        mComponentFrag = fragment;
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
        mComActivity = null;
        if(mViewSet != null){
            mViewSet.clear();
            mViewSet = null;
        }
    }

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        if(event == Lifecycle.Event.ON_CREATE){
            onCreate();
        }else if(event == Lifecycle.Event.ON_START) {
            onStart();
        }else if(event == Lifecycle.Event.ON_RESUME) {
            onResume();
        }else if(event == Lifecycle.Event.ON_PAUSE) {
            onPause();
        }else if(event == Lifecycle.Event.ON_STOP) {
            onStop();
        }else if(event == Lifecycle.Event.ON_DESTROY){
            onDestory();
        }
    }
}
