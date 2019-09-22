package com.example.libframework.Rv;

import android.annotation.SuppressLint;
import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint({"RestrictedApi"})
@SuppressWarnings("unchecked")
public class VHAdapter<VH extends VHolder> extends RecyclerView.Adapter<VH> implements GenericLifecycleObserver {
    protected String TAG = getClass().getSimpleName();
    private LayoutInflater mInflater;
    private RecyclerView mRecyclerView;
    private DataManager mDataManager;
    private Map<Class, Integer> mClzMap = new HashMap<>();
    private SparseArray<VHBridge> mBridgeMap = new SparseArray<>();
    private int mIncrease;
    private Context mContext; //通过外部传入好还是onAttachedToRecyclerView拿去

    public VHAdapter() {
        mDataManager = new DataManager();
        mDataManager.setAdapter(this);
    }

    @Override
    public int getItemCount() {
        return mDataManager.size();
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mInflater == null){
            mInflater = LayoutInflater.from(parent.getContext());
        }
        VHBridge bridge = mBridgeMap.get(viewType);
        if(bridge != null){
            View view = bridge.itemView();
            if(view != null){
                parent.addView(view);
                return (VH) bridge.onCreateViewHolder(view);
            }
            int layoutId = bridge.layoutId();
            if(layoutId != -1){
                return (VH) bridge.onCreateViewHolder(mInflater.inflate(layoutId, parent, false));
            }
            VHolder vh = bridge.onCreateViewHolder(parent, viewType);
            if(vh != null){
                return (VH) vh;
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bindFull(position, mDataManager.get(position));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position, @NonNull List<Object> payloads) {
        if(payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads);
        }else {
            for (Object payload : payloads) {
                if(payload instanceof Notify){
                    holder.bindPartial(mDataManager.get(position), (Notify) payload);
                }else {
                    super.onBindViewHolder(holder, position, payloads);
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object data = mDataManager.get(position);
        if(mClzMap.containsKey(data.getClass())){
            return mClzMap.get(data.getClass());
        }
        return super.getItemViewType(position);
    }

    // TODO: 2019-06-22 要不要有unRegister
    public void register(Class clz, VHBridge bridge){
        if(mClzMap.containsKey(clz)){
            return;
        }
        bridge.mAdapter = this;
        bridge.mDataManager = mDataManager;
        if(mContext != null){
            bridge.mContext = mContext;
        }
        mIncrease++;
        bridge.mType = mIncrease;
        mBridgeMap.put(mIncrease, bridge);
        mClzMap.put(clz, mIncrease);
    }


    public boolean isRegister(Class clz){
        return mClzMap.containsKey(clz);
    }

    public boolean isRegister(Object obj){
        if(obj == null){
            return false;
        }
        return mClzMap.containsKey(obj.getClass());
    }

    public DataManager getDataProvider(){
        return mDataManager;
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mContext = recyclerView.getContext();
        if(mContext instanceof LifecycleOwner){
            LifecycleOwner owner = (LifecycleOwner) mContext;
            owner.getLifecycle().addObserver(this);
        }
        for (int i = 0, size = mBridgeMap.size(); i < size; i++) {
            VHBridge bridge = mBridgeMap.valueAt(i);
            bridge.mContext = mContext;
            bridge.mRecyclerView = mRecyclerView;
        }
    }

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        if(mRecyclerView != null){
            Log.d(TAG, "onStateChanged: event = " + event);
            int count = mRecyclerView.getChildCount();
            for (int i = 0; i < count; i++) {
                RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i));
                if(viewHolder instanceof GenericLifecycleObserver){
                    ((GenericLifecycleObserver)viewHolder).onStateChanged(source, event);
                }
            }
        }
        if(event == Lifecycle.Event.ON_DESTROY){
            mDataManager.clear();
            source.getLifecycle().removeObserver(this);
        }
    }
}
