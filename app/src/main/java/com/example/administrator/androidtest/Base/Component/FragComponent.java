package com.example.administrator.androidtest.Base.Component;

import android.content.Intent;
import android.support.annotation.CallSuper;
import com.example.administrator.androidtest.Base.ActAndFrag.ComponentAct;
import com.example.administrator.androidtest.Base.ActAndFrag.ComponentFrag;

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
