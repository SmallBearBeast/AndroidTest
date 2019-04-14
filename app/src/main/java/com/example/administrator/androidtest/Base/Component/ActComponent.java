package com.example.administrator.androidtest.Base.Component;

import android.content.Intent;
import android.support.annotation.CallSuper;
import com.example.administrator.androidtest.Base.ActAndFrag.ComponentAct;

public abstract class ActComponent<T extends ViewSet> implements IComponent {
    public ComponentAct mComActivity;
    public T mViewSet;

    public ActComponent(){}

    public ActComponent(T viewSet){
        mViewSet = viewSet;
    }

    public void attachViewSet(T viewSet){
        mViewSet = viewSet;
    }

    public void attachActivity(ComponentAct activity){
        mComActivity = activity;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @CallSuper
    @Override
    public void onDestory() {
        mComActivity = null;
        if(mViewSet != null){
            mViewSet.clear();
            mViewSet = null;
        }
    }
}
