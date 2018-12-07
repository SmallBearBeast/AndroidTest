package com.example.administrator.androidtest;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.androidtest.Common.Util.PermissionUtil;

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
        PermissionUtil.requestPermissions(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
        }, mActivity);
    }
}
