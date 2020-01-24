package com.example.libframework.Rv;

import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import android.database.Cursor;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.example.libframework.CoreUI.IComponent;

public class VHolder<DATA> extends RecyclerView.ViewHolder implements IComponent, View.OnClickListener, GenericLifecycleObserver {
    protected String TAG = RvConstant.RV_LOG_TAG + "-" + getClass().getSimpleName();
    protected DATA mData;
    protected int mPos;

    public VHolder(View itemView) {
        super(itemView);
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
    public void bindFull(int pos, DATA data) {
        mData = data;
        mPos = pos;
        if (data instanceof Cursor) {
            bindCursor(pos, (Cursor) data);
        }
    }

    protected void bindCursor(int pos, Cursor cursor) {

    }

    protected void bindPartial(DATA data, @NonNull Notify obj) {

    }

    @Override
    public void onClick(View v) {

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

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
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
        }
    }
}
