package com.example.administrator.androidtest.demo.LibraryDemo;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.bear.libcomponent.component.ui.ActivityComponent;
import com.example.administrator.androidtest.databinding.ActLibraryDemoListBinding;

public class BaseLibraryDemoComponent extends ActivityComponent<ActLibraryDemoListBinding> implements View.OnClickListener {

    public BaseLibraryDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    public void onClick(View view) {

    }
}