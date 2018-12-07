package com.example.administrator.androidtest;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BaseHolder<T> extends RecyclerView.ViewHolder {
    protected T mData;
    protected int mPos;
    public BaseHolder(View itemView) {
        super(itemView);
    }

    public void bind(int pos, T data){
        mData = data;
        mPos = pos;
    }
}
