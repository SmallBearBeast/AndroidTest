package com.example.administrator.androidtest.Test.MainTest.BusTest;

import android.annotation.SuppressLint;
import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestComponent;

public class BusTestComponent extends TestComponent {

    @Override
    protected void onCreate() {
        clickListener(this, R.id.busTestButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.busTestButton:
                BusTest1Act.start(getDependence());
                break;
        }
    }
}
