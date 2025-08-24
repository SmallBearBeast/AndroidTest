package com.example.administrator.androidtest.demo.ViewDemo.CoordinatorLayoutTest;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.ViewDemo.BaseViewDemoComponent;

public class BehaviorTestComponent extends BaseViewDemoComponent {

    public BehaviorTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        getViewBinding().behaviorTestButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.behaviorTestButton:
                BehaviorTestActivity.start(getContext());
                break;

            default:
                break;
        }
    }
}
