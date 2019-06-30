package com.example.administrator.androidtest.Base.Component;

import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;

public abstract class ViewSet {
    private static final byte INIT_COUNT = 8;
    private View mContentView;
    private SparseArray<View> mViewIdArray;

    public ViewSet() {

    }

    public ViewSet(View contentView) {
        attachView(contentView);
    }

    public void attachView(View contentView){
        mViewIdArray = new SparseArray<>(INIT_COUNT);
        mContentView = contentView;
        initView(contentView);
    }

    protected void initView(View contentView) {
        //findViewById()操作
    }

    protected <T extends View> T findViewById(@IdRes int viewId){
        View view = mViewIdArray.get(viewId);
        if(view == null){
            view = mContentView.findViewById(viewId);
            mViewIdArray.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * findViewById并设置点击事件
     */
    protected View findViewAndSetListener(@IdRes int viewId, View.OnClickListener listener){
        View view = findViewById(viewId);
        setOnClickListener(listener, viewId);
        return view;
    }

    private void setOnClickListener(View.OnClickListener listener, @IdRes int... viewIds){
        for (int id : viewIds) {
            if(mViewIdArray.get(id) != null){
                mViewIdArray.get(id).setOnClickListener(listener);
            }
        }
    }

    void clear(){
        mContentView = null;
        mViewIdArray.clear();
        mViewIdArray = null;
    }
}
