package com.example.administrator.androidtest.Base.Adapter;

import android.content.Intent;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.administrator.androidtest.Base.Component.IComponent;

public abstract class VHolder<DATA> extends RecyclerView.ViewHolder implements IComponent, View.OnClickListener {
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
}
