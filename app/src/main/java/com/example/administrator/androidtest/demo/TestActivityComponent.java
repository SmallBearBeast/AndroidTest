package com.example.administrator.androidtest.demo;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.bear.libcomponent.component.ActivityComponent;

public class TestActivityComponent extends ActivityComponent implements View.OnClickListener {
    public TestActivityComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    public void onClick(View view) {

    }
}