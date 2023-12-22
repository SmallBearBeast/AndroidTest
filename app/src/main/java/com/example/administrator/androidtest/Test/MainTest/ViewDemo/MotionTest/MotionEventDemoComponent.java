package com.example.administrator.androidtest.Test.MainTest.ViewDemo.MotionTest;

import android.annotation.SuppressLint;
import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestComponent;

public class MotionEventDemoComponent extends TestComponent {
    @Override
    protected void onCreate() {
        setOnClickListener(this, R.id.motionTestButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.motionTestButton:
                MotionEventDemoAct.start(getContext());
                break;

            default:
                break;
        }
    }
}
