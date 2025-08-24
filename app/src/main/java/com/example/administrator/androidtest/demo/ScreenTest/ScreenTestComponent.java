package com.example.administrator.androidtest.demo.ScreenTest;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.TestActivityComponent;

public class ScreenTestComponent extends TestActivityComponent {
    public ScreenTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        getViewBinding().screenTestButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.screenTestButton:
                ScreenActivity.start(getContext());
                break;

            default:
                break;
        }
    }
}
