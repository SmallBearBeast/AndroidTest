package com.example.administrator.androidtest.Test.MainTest.ViewDemo.CoordinatorLayoutTest;

import android.annotation.SuppressLint;
import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;

public class CoordinatorLayoutTestComponent extends TestActivityComponent {

    @Override
    protected void onCreate() {
        setOnClickListener(this, R.id.coordinatorLayoutTestButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.coordinatorLayoutTestButton:
                CoordinatorLayoutTestAct.start(getContext());
                break;

            default:
                break;
        }
    }
}
