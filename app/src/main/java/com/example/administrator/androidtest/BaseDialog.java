package com.example.administrator.androidtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseDialog extends DialogFragment {
    private View mContentView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentView = LayoutInflater.from(getContext()).inflate(layoutId(), null);
        init(savedInstanceState);
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

    protected void init(Bundle savedInstanceState){

    }
}
