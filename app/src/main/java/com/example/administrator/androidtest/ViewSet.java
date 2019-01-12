package com.example.administrator.androidtest;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class ViewSet {
    protected Context mContext;
    private View mContentView;
    private SparseArray<View> mIdWithViewArray;

    public ViewSet(View contentView, Context context) {
        mContext = context;
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
}
