package com.example.administrator.androidtest.Test.MainTest.AspectTest;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;

public class AspectJTestComponent extends TestActivityComponent {

    public AspectJTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

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
