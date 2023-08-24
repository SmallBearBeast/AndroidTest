package com.example.administrator.androidtest.Test.MainTest.AspectTest;

import android.annotation.SuppressLint;
import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestComponent;

public class AspectJTestComponent extends TestComponent {

    @Override
    protected void onCreate() {
        setOnClickListener(this, R.id.aspectjTestButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.aspectjTestButton:
                AspectTestAct.start(getContext());
                break;

            default:
                break;
        }
    }
}
