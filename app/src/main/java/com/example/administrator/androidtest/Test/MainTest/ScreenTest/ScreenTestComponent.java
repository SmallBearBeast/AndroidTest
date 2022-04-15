package com.example.administrator.androidtest.Test.MainTest.ScreenTest;

import android.annotation.SuppressLint;
import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestComponent;

public class ScreenTestComponent extends TestComponent {
    @Override
    protected void onCreate() {
        clickListener(this, R.id.screenTestButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.screenTestButton:
                ScreenAct.start(getDependence());
                break;

            default:
                break;
        }
    }
}
