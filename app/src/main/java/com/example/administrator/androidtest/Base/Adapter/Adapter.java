package com.example.administrator.androidtest.Base.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public abstract class Adapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    private DataProvider mDataProvider = new DataProvider();

    /**
     * 局部刷新，调用这个方法一定要手动调用onBindViewHolder()方法
     */
    @Override
    public void onBindViewHolder(@NonNull T holder, int position, @NonNull List<Object> payloads) {
        if(payloads.isEmpty()){
            onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public int getItemCount() {
        return mDataProvider.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public T onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull T holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return mDataProvider.getItemViewType(position);
    }

    public void setDataProvider(@NonNull DataProvider provider){
        mDataProvider = provider;
    }
}
