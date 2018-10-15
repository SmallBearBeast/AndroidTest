package com.example.administrator.androidtest;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseDataBindingFrag<T extends ViewDataBinding> extends BaseFrag {
    protected T dataBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(dataBinding == null){
            dataBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false);
            initView();
        }
        return dataBinding.getRoot();
    }
}
