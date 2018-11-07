package com.example.administrator.androidtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

public abstract class BaseViewSetAct<T extends ViewSet, K> extends BaseAct {
    public K vm;
    public T viewSet;
    public View contentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = LayoutInflater.from(this).inflate(layoutId(), null);
        setContentView(contentView);
        init(savedInstanceState);
    }
}
