package com.example.administrator.androidtest.demo.ViewDemo.CoordinatorLayoutTest;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.TestActivityComponent;

public class CoordinatorLayoutTestComponent extends TestActivityComponent {

    public CoordinatorLayoutTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        setOnClickListener(this, R.id.coordinatorLayoutTestButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.coordinatorLayoutTestButton:
                CoordinatorLayoutTestActivity.start(getContext());
                break;

            default:
                break;
        }
    }
}
