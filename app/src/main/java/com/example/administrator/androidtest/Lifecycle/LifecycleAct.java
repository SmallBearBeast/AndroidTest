package com.example.administrator.androidtest.Lifecycle;

import com.example.administrator.androidtest.BaseAct;
import com.example.administrator.androidtest.R;

public class LifecycleAct extends BaseAct {
    @Override
    protected void init() {
        getLifecycle().addObserver(new LifecycleObj());
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }
}
