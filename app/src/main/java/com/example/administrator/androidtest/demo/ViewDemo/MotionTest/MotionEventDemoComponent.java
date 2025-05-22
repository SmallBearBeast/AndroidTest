package com.example.administrator.androidtest.demo.ViewDemo.MotionTest;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.ViewDemo.BaseViewDemoComponent;

public class MotionEventDemoComponent extends BaseViewDemoComponent {
    public MotionEventDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        getBinding().motionTestButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.motionTestButton:
                MotionEventDemoActivity.start(getContext());
                break;

            default:
                break;
        }
    }
}
