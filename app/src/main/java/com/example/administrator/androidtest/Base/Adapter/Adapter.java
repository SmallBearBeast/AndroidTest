package com.example.administrator.androidtest.Base.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.example.administrator.androidtest.Base.Component.IComponent;

import java.util.List;

public abstract class Adapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> implements IComponent {

    private DataProvider mDataProvider = new DataProvider();
    private RecyclerView mRecyclerView;

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

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @Override
    public void onCreate() {
        int count = mRecyclerView.getChildCount();
        for (int i = 0; i < count; i++) {
            RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i));
            if(viewHolder instanceof IComponent){
                ((IComponent)viewHolder).onCreate();
            }
        }
    }


    @Override
    public void onStart() {
        int count = mRecyclerView.getChildCount();
        for (int i = 0; i < count; i++) {
            RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i));
            if(viewHolder instanceof IComponent){
                ((IComponent)viewHolder).onStart();
            }
        }
    }

    @Override
    public void onResume() {
        int count = mRecyclerView.getChildCount();
        for (int i = 0; i < count; i++) {
            RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i));
            if(viewHolder instanceof IComponent){
                ((IComponent)viewHolder).onResume();
            }
        }
    }

    @Override
    public void onPause() {
        int count = mRecyclerView.getChildCount();
        for (int i = 0; i < count; i++) {
            RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i));
            if(viewHolder instanceof IComponent){
                ((IComponent)viewHolder).onPause();
            }
        }
    }

    @Override
    public void onStop() {
        int count = mRecyclerView.getChildCount();
        for (int i = 0; i < count; i++) {
            RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i));
            if(viewHolder instanceof IComponent){
                ((IComponent)viewHolder).onStop();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        int count = mRecyclerView.getChildCount();
        for (int i = 0; i < count; i++) {
            RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i));
            if(viewHolder instanceof IComponent){
                ((IComponent)viewHolder).onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onDestory() {
        int count = mRecyclerView.getChildCount();
        for (int i = 0; i < count; i++) {
            RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i));
            if(viewHolder instanceof IComponent){
                ((IComponent)viewHolder).onDestory();
            }
        }
    }
}
