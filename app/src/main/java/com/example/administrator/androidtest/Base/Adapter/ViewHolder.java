package com.example.administrator.androidtest.Base.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ViewHolder<D> extends RecyclerView.ViewHolder{
    protected D mData;
    protected int mPos;

    public ViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(int pos, D data){
        mData = data;
        mPos = pos;
    }
}
