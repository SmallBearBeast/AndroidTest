package com.example.administrator.androidtest.Test.MainTest.MotionTest;

import android.annotation.SuppressLint;
import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestComponent;

public class MotionTestComponent extends TestComponent {
    @Override
    protected void onCreate() {
        clickListener(this, R.id.motionTestButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.motionTestButton:
                MotionTestAct.start(getDependence());
                break;

            default:
                break;
        }
    }
}
