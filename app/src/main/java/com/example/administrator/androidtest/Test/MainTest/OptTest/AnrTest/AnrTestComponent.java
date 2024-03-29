package com.example.administrator.androidtest.Test.MainTest.OptTest.AnrTest;

import android.view.View;

import com.bear.libcomponent.component.ActivityComponent;
import com.example.administrator.androidtest.R;

public class AnrTestComponent extends ActivityComponent implements View.OnClickListener {
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
