package com.bear.librv;

import android.content.Context;
import android.database.Cursor;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

public class MultiTypeHolder<ITEM> extends RecyclerView.ViewHolder implements LifecycleEventObserver, View.OnClickListener {
    protected String TAG = RvLog.RV_LOG_TAG + "-" + getClass().getSimpleName();
    private ITEM mItem;
    private int position;
    private MultiTypeAdapter multiTypeAdapter;
    private MultiItemChanger multiItemChanger;
    private Context context;
    private RecyclerView recyclerView;
    private MultiTypeDelegate<ITEM, ?> multiTypeDelegate;

    public MultiTypeHolder(View itemView) {
        super(itemView);
    }

    void attachDelegate(MultiTypeDelegate<ITEM, ?> delegate) {
        multiTypeDelegate = delegate;
        multiTypeAdapter = delegate.multiTypeAdapter;
        multiItemChanger = delegate.multiItemChanger;
        context = delegate.context;
        recyclerView = delegate.recyclerView;
    }

    protected void put(@NonNull String key, @NonNull Object value) {
        multiTypeDelegate.put(key, value);
    }

    protected @NonNull
    <V> V get(String key) {
        return (V) multiTypeDelegate.get(key);
    }

    protected <T extends View> T findViewById(@IdRes int id) {
        return itemView.findViewById(id);
    }

    protected void setOnClickListener(@IdRes int... ids) {
        for (int id : ids) {
            View view = findViewById(id);
            if (view != null) {
                view.setOnClickListener(this);
            }
        }
    }

    protected void setOnClickListener(View... views) {
        for (View view : views) {
            if (view != null) {
                view.setOnClickListener(this);
            }
        }
    }

    @CallSuper
    protected void bindFull(int pos, ITEM item) {
        mItem = item;
        position = pos;
        if (item instanceof Cursor) {
            bindCursor(pos, (Cursor) item);
        }
    }

    protected void bindCursor(int pos, Cursor cursor) {

    }

    protected void bindPartial(ITEM data, @NonNull Payload obj) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_CREATE) {
            onCreate();
        } else if (event == Lifecycle.Event.ON_START) {
            onStart();
        } else if (event == Lifecycle.Event.ON_RESUME) {
            onResume();
        } else if (event == Lifecycle.Event.ON_PAUSE) {
            onPause();
        } else if (event == Lifecycle.Event.ON_STOP) {
            onStop();
        } else if (event == Lifecycle.Event.ON_DESTROY) {
            onDestroy();
            multiTypeAdapter = null;
            multiItemChanger = null;
            context = null;
            recyclerView = null;
            source.getLifecycle().removeObserver(this);
        }
    }

    protected void onCreate() {

    }


    protected void onStart() {

    }


    protected void onResume() {

    }


    protected void onPause() {

    }


    protected void onStop() {

    }

    protected void onDestroy() {

    }

    protected ITEM getItem() {
        return mItem;
    }

    protected int getItemPosition() {
        return position;
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

    protected MultiTypeDelegate getDelegate() {
        return multiTypeDelegate;
    }
}
