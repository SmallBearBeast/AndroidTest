package com.example.administrator.androidtest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public abstract class BaseDialog extends DialogFragment {
    protected View mContentView;
    protected Activity mActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentView = LayoutInflater.from(getContext()).inflate(layoutId(), null);
        mActivity = getActivity();
        init(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        initDialogSize();
    }

    private void initDialogSize() {
        getDialog().getWindow().setGravity(getGravity());
        WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
        lp.width = getWidthAndHeight()[0];
        lp.height = getWidthAndHeight()[1];
        getDialog().getWindow().setAttributes(lp);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mContentView != null)
            return mContentView;
        return inflater.inflate(layoutId(), null);
    }

    protected abstract int layoutId();

    public Bundle buildArguments(){
        Bundle bundle = new Bundle();
        return bundle;
    }

    protected abstract int[] getWidthAndHeight();

    protected abstract int getGravity();

    protected void init(Bundle savedInstanceState){
        
    }
}
