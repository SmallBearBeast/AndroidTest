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
import com.example.libframework.Component.IComponent;

public class VHolder<DATA> extends RecyclerView.ViewHolder implements IComponent, View.OnClickListener, GenericLifecycleObserver {
    protected String TAG = getClass().getSimpleName();
    protected DATA mData;
    protected int mPos;

    public VHolder(View itemView) {
        super(itemView);
    }

    public final <T extends View> T findViewById(@IdRes int id) {
        return itemView.findViewById(id);
    }

    @CallSuper
    public void bindFull(int pos, DATA data) {
        mData = data;
        mPos = pos;
        if(data instanceof Cursor){
            bindCursor(pos, (Cursor) data);
        }
    }

    public void bindCursor(int pos, Cursor cursor){

    }

    public void bindPartial(DATA data, @NonNull Notify obj) {

    }

    @Override
    public void onClick(View v) {

    }


    public void onCreate() {

    }


    public void onStart() {

    }


    public void onResume() {

    }


    public void onPause() {

    }


    public void onStop() {

    }

    public void onDestory() {

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
            onDestory();
        }
    }
}
