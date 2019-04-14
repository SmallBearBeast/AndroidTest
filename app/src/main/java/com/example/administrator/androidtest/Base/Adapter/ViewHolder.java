package com.example.administrator.androidtest.Base.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.administrator.androidtest.Base.Component.IComponent;

public abstract class ViewHolder<D> extends RecyclerView.ViewHolder implements IComponent {
    protected D mData;
    protected int mPos;

    public ViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(int pos, D data){
        mData = data;
        mPos = pos;
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
