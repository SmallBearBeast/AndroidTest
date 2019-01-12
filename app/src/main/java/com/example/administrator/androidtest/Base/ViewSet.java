package com.example.administrator.androidtest.Base;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public abstract class ViewSet {
    private View mContentView;
    private SparseArray<View> mIdWithViewArray;

    public ViewSet(View contentView) {
        mIdWithViewArray = new SparseArray<>(16);
        mContentView = contentView;
        initView(contentView);
    }

    protected void initView(View contentView) {
        //findViewById()操作
    }

    protected View findViewById(int viewId){
        View view = mIdWithViewArray.get(viewId);
        if(view == null){
            view = mContentView.findViewById(viewId);
            mIdWithViewArray.put(viewId, view);
        }
        return view;
    }

    public void setOnClickListener(View.OnClickListener listener, int... viewIds){
        for (int i = 0; i < viewIds.length; i++) {
            if(mIdWithViewArray.get(viewIds[i]) != null){
                mIdWithViewArray.get(viewIds[i]).setOnClickListener(listener);
            }
        }
    }

    public void clear(){
        mContentView = null;
        mIdWithViewArray.clear();
        mIdWithViewArray = null;
    }
}
