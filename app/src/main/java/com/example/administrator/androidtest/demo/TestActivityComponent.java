package com.example.administrator.androidtest.demo;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.bear.libcomponent.component.ActivityComponent;
import com.example.administrator.androidtest.databinding.ActMainDemoListBinding;

public class TestActivityComponent extends ActivityComponent<ActMainDemoListBinding> implements View.OnClickListener {

    public TestActivityComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    public void onClick(View view) {

    }
}