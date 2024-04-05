package com.example.administrator.androidtest.Test.MainTest.ScreenTest;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;

public class ScreenTestComponent extends TestActivityComponent {
    public ScreenTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        setOnClickListener(this, R.id.screenTestButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.screenTestButton:
                ScreenAct.start(getContext());
                break;

            default:
                break;
        }
    }
}
