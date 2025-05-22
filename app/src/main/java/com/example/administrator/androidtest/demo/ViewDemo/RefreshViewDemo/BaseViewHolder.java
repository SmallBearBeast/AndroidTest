package com.example.administrator.androidtest.demo.ViewDemo.RefreshViewDemo;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder<T> extends RecyclerView.ViewHolder{
    protected int mPosition;
    protected T mData;
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(int position, T data){
        mPosition = position;
        mData = data;
    }
}
