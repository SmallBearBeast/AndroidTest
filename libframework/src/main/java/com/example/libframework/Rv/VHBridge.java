package com.example.libframework.Rv;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public abstract class VHBridge<VH extends VHolder> {
    protected String TAG = RvConstant.RV_LOG_TAG + "-" + getClass().getSimpleName();
    //VHAdapter和DataManager是在register赋值。
    protected VHAdapter mAdapter;
    protected DataManager mDataManager;
    //Context和RecyclerView在onAttachedToRecyclerView有值。
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

    protected int getSpanSize(RecyclerView rv) {
        return 1;
    }

    protected boolean isFullSpan() {
        return false;
    }

    protected final int getPosition(@NonNull final VHolder holder) {
        return holder.getAdapterPosition();
    }

    public int getType(){
        return mType;
    }

    protected void onInitRvAndContext(RecyclerView rv, Context context) {
        mRecyclerView = rv;
        mContext = context;
    }

    protected void onInitAdapterAndManager(VHAdapter adapter, DataManager manager) {
        mAdapter = adapter;
        mDataManager = manager;
    }
}