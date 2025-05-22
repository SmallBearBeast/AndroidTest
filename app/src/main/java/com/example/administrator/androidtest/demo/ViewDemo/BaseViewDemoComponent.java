package com.example.administrator.androidtest.demo.ViewDemo;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.bear.libcomponent.component.ActivityComponent;
import com.example.administrator.androidtest.databinding.ActViewDemoListBinding;

public class BaseViewDemoComponent extends ActivityComponent<ActViewDemoListBinding> implements View.OnClickListener {

    public BaseViewDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    public void onClick(View view) {

    }
}