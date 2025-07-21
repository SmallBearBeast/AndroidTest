package com.example.administrator.androidtest.demo.optdemo.anrdemo;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.TestActivityComponent;

public class AnrTestComponent extends TestActivityComponent {

    public AnrTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        getBinding().anrTestButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.anrTestButton:
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {

                }
                break;
        }
    }
}
