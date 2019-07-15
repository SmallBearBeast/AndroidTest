package com.example.administrator.androidtest.Base.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public abstract class VHBridge<VH extends VHolder> {
    //VHAdapter和DataManager是在register赋值。
    protected VHAdapter mAdapter;
    protected DataManager mDataManager;
    //Context在onAttachedToRecyclerView有值。
    protected Context mContext;
    protected RecyclerView mRecyclerView;
    protected int mType;

    @NonNull
    protected abstract VH onCreateViewHolder(@NonNull View itemView);

    protected abstract @LayoutRes int layoutId();

    protected View itemView(){
        return null;
    }

    protected VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return null;
    }

    protected final int getPosition(@NonNull final VHolder holder) {
        return holder.getAdapterPosition();
    }

    public int getType(){
        return mType;
    }
}