package com.example.administrator.androidtest.Base;

import android.app.Activity;

public abstract class Component<T extends ViewSet>{
    public ComponentAct mActivity;
    public Activity mContext;
    public T mViewSet;

    public Component(){}

    public Component(T viewSet){
        mViewSet = viewSet;
    }
    public void attachViewSet(T viewSet){
        mViewSet = viewSet;
    }

    public void attachContext(ComponentAct activity){
        mActivity = activity;
        mContext = activity;
    }

    public void onCreate(){}

    public void onStart(){}

    public void onResume(){}

    public void onPause(){}

    public void onStop(){}

    public void onDestory(){
        mActivity = null;
        mContext = null;
        mViewSet.clear();
        mViewSet = null;
    }
}
