package com.example.administrator.androidtest.Base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ViewHolder<T> extends RecyclerView.ViewHolder {
    protected T mData;
    protected int mPos;
    public ViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(int pos, T data){
        mData = data;
        mPos = pos;
    }
}
