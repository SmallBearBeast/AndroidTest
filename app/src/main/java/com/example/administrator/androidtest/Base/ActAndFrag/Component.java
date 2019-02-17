package com.example.administrator.androidtest.Base.ActAndFrag;

import android.content.Intent;

public abstract class Component<T extends ViewSet>{
    public ComponentAct mComActivity;
    public T mViewSet;

    public Component(){}

    public Component(T viewSet){
        mViewSet = viewSet;
    }

    public void attachViewSet(T viewSet){
        mViewSet = viewSet;
    }

    public void attachActivity(ComponentAct activity){
        mComActivity = activity;
    }

    public void onCreate(){}

    public void onStart(){}

    public void onResume(){}

    public void onPause(){}

    public void onStop(){}

    public void onActivityResult(int requestCode, int resultCode, Intent data){}

    public void onDestory(){
        mComActivity = null;
        if(mViewSet != null){
            mViewSet.clear();
            mViewSet = null;
        }
    }
}
