package com.example.administrator.androidtest.Test.MainTest.OptTest.AnrTest;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;

public class AnrTestComponent extends TestActivityComponent {

    public AnrTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        setOnClickListener(this, R.id.anrTestButton);
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
