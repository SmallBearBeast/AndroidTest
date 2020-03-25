package com.example.libframework.Rv;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

public abstract class VHBridge<VH extends VHolder> {
    protected String TAG = RvConstant.RV_LOG_TAG + "-" + getClass().getSimpleName();
    //VHAdapter和DataManager是在register赋值。
    protected VHAdapter mAdapter;
    protected DataManager mDataManager;
    //Context和RecyclerView在onAttachedToRecyclerView有值。
    protected Context mContext;
    protected RecyclerView mRecyclerView;
    protected int mType;
    private Map<String, Object> mExtraMap;

    @NonNull
    protected abstract VH onCreateViewHolder(@NonNull View itemView);

    protected abstract @LayoutRes int layoutId();

    protected View itemView(){
        return null;
    }

    protected VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return null;
    }

    protected int getSpanSize(RecyclerView rv) {
        return 1;
    }

    protected boolean isSupportLifecycle() {
        return false;
    }

    protected boolean isFullSpan() {
        return false;
    }

    public int getType(){
        return mType;
    }

    void onInitRvAndContext(RecyclerView rv, Context context) {
        mRecyclerView = rv;
        mContext = context;
    }

    void onInitAdapterAndManager(VHAdapter adapter, DataManager manager) {
        mAdapter = adapter;
        mDataManager = manager;
    }

    protected void put(@NonNull String key, @NonNull Object value) {
        if (mExtraMap == null) {
            mExtraMap = new HashMap<>();
        }
        mExtraMap.put(key, value);
    }

    protected @NonNull <V> V get(@NonNull String key) {
        return (V) mExtraMap.get(key);
    }
}