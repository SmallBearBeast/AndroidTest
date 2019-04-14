package com.example.administrator.androidtest.Test.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;

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
