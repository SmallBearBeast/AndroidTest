package com.example.administrator.androidtest.demo.ViewDemo.CoordinatorLayoutTest;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.TestActivityComponent;

public class BehaviorTestComponent extends TestActivityComponent {

    public BehaviorTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        setOnClickListener(this, R.id.behaviorTestButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.behaviorTestButton:
                BehaviorTestAct.start(getContext());
                break;

            default:
                break;
        }
    }
}
