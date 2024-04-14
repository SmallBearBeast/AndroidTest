package com.bear.librv;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

public abstract class MultiTypeDelegate<ITEM, VH extends MultiTypeHolder<ITEM>> {
    protected String TAG = RvLog.RV_LOG_TAG + "-" + getClass().getSimpleName();
    private Map<String, Object> mExtraMap;
    //VHAdapter和DataManager是在register赋值。
    MultiTypeAdapter multiTypeAdapter;
    MultiItemChanger multiItemChanger;
    //Context和RecyclerView在onAttachedToRecyclerView有值。
    Context context;
    RecyclerView recyclerView;

    void attachRecyclerView(RecyclerView rv) {
        recyclerView = rv;
    }

    void attachContext(Context c) {
        context = c;
    }

    void attachAdapter(MultiTypeAdapter adapter) {
        multiTypeAdapter = adapter;
    }

    void attachChanger(MultiItemChanger changer) {
        multiItemChanger = changer;
    }

    protected void put(@NonNull String key, @NonNull Object value) {
        if (mExtraMap == null) {
            mExtraMap = new HashMap<>();
        }
        mExtraMap.put(key, value);
    }

    protected @NonNull <V> V get(@NonNull String key) {
        V value = (V) mExtraMap.get(key);
        if (value == null) {
            throw new RuntimeException("The value from mExtraMap is null");
        }
        return value;
    }

    @NonNull
    protected abstract VH onCreateViewHolder(@NonNull View itemView);

    protected VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return null;
    }

    protected abstract @LayoutRes int layoutId();

    protected View itemView(){
        return null;
    }

    protected int getSpanSize(RecyclerView rv) {
        return 1;
    }

    protected boolean isFullSpan() {
        return false;
    }

    protected boolean isSupportLifecycle() {
        return false;
    }

    protected MultiTypeAdapter getAdapter() {
        return multiTypeAdapter;
    }

    protected MultiItemChanger getChanger() {
        return multiItemChanger;
    }

    protected Context getContext() {
        return context;
    }

    protected RecyclerView getRecyclerView() {
        return recyclerView;
    }
}