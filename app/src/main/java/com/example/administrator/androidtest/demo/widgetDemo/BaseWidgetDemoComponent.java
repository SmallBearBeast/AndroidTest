package com.example.administrator.androidtest.demo.widgetDemo;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.bear.libcomponent.component.ui.ActivityComponent;
import com.example.administrator.androidtest.databinding.ActWidgetDemoListBinding;

public class BaseWidgetDemoComponent extends ActivityComponent<ActWidgetDemoListBinding> implements View.OnClickListener {

    public BaseWidgetDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    public void onClick(View view) {

    }
}