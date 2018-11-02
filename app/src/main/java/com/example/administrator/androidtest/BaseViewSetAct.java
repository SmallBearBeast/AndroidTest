package com.example.administrator.androidtest;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

public class BaseViewSetAct<T extends ViewSet> extends BaseAct {
    public T viewSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(layoutId() != -1){
            View contentView = LayoutInflater.from(this).inflate(layoutId(), null);
            setContentView(contentView);
            viewSet.initView(contentView);
        }
    }
}
