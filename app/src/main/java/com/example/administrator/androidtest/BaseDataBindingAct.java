package com.example.administrator.androidtest;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class BaseDataBindingAct<T extends ViewDataBinding> extends BaseAct {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding(DataBindingUtil.setContentView(this, layoutId()));
    }

    protected void initBinding(ViewDataBinding t){

    }
}
