package com.example.administrator.androidtest.Lifecycle;

import android.os.Bundle;

import com.example.administrator.androidtest.Base.ActAndFrag.BaseAct;
import com.example.administrator.androidtest.R;

public class LifecycleAct extends BaseAct {
    @Override
    protected void init(Bundle savedInstanceState) {
        getLifecycle().addObserver(new LifecycleObj());
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }
}
