package com.example.administrator.androidtest.demo.FragTest;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.TestActivityComponent;

public class FragLifecycleTestComponent extends TestActivityComponent {
    public FragLifecycleTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

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
