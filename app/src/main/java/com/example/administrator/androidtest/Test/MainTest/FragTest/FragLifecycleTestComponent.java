package com.example.administrator.androidtest.Test.MainTest.FragTest;

import android.annotation.SuppressLint;
import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestComponent;

public class FragLifecycleTestComponent extends TestComponent {
    @Override
    protected void onCreate() {
        super.onCreate();
        setOnClickListener(this, R.id.fragLifecycleTestButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragLifecycleTestButton:
                FragLifecycleTestAct.start(getContext());
                break;
            default:
                break;
        }
    }
}
