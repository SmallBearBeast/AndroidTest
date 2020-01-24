package com.example.libframework.CoreUI;

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.annotation.CallSuper;


public abstract class BaseComponent<M> implements IComponent {
    protected M mMain;
    protected View mContentView;
    private SparseArray<View> mViewIdArray;
    private static final byte INIT_COUNT = 16;

    void attachView(View contentView) {
        mContentView = contentView;
        if (contentView != null) {
            mViewIdArray = new SparseArray<>(INIT_COUNT);
        } else {
            if (mViewIdArray != null) {
                mViewIdArray.clear();
                mViewIdArray = null;
            }
        }
    }

    void attachMain(M main) {
        mMain = main;
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

    @CallSuper
    protected void onDestroy() {
        mMain = null;
        attachView(null);
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

    protected View findViewAndSetListener(View.OnClickListener listener, @IdRes int viewId) {
        View view = findViewById(viewId);
        setOnClickListener(listener, viewId);
        return view;
    }

    protected void setOnClickListener(View.OnClickListener listener, @IdRes int... viewIds) {
        for (int id : viewIds) {
            if (mViewIdArray.get(id) != null) {
                mViewIdArray.get(id).setOnClickListener(listener);
            } else {
                findViewById(id).setOnClickListener(listener);
            }
        }
    }

    protected <T extends View> T findViewById(@IdRes int viewId) {
        View view = mViewIdArray.get(viewId);
        if (view == null) {
            view = mContentView.findViewById(viewId);
            mViewIdArray.put(viewId, view);
        }
        return (T) view;
    }
}
