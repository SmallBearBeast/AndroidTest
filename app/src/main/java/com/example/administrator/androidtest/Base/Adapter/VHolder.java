package com.example.administrator.androidtest.Base.Adapter;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Intent;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.administrator.androidtest.Base.Component.IComponent;

public abstract class VHolder<DATA> extends RecyclerView.ViewHolder implements IComponent, View.OnClickListener, GenericLifecycleObserver {
    protected String TAG = getClass().getSimpleName();
    protected DATA mData;
    protected int mPos;

    public VHolder(View itemView) {
        super(itemView);
    }

    @CallSuper
    public void bindFull(int pos, DATA data){
        mData = data;
        mPos = pos;
    }

    public void bindPartial(DATA data, @NonNull Notify obj){

    }

    @Override
    public void onClick(View v) {

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

    @Override
    public void onDestory() {

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
