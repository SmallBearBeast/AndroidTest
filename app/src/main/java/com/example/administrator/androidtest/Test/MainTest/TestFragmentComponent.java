package com.example.administrator.androidtest.Test.MainTest;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.bear.libcomponent.component.FragmentComponent;

public class TestFragmentComponent extends FragmentComponent implements View.OnClickListener {
    public TestFragmentComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    public void onClick(View view) {

    }
}