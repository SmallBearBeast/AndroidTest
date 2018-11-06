package com.example.administrator.androidtest;

import android.os.Bundle;
import android.support.annotation.Nullable;

public class BaseDefaultAct extends BaseAct {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (layoutId() != -1) {
            setContentView(layoutId());
            init(savedInstanceState);
        }
    }
}
