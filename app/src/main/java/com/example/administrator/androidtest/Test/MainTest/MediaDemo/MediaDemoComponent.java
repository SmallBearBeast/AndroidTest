package com.example.administrator.androidtest.Test.MainTest.MediaDemo;

import android.annotation.SuppressLint;
import android.view.View;

import com.bear.libcomponent.component.ActivityComponent;
import com.example.administrator.androidtest.R;

public class MediaDemoComponent extends ActivityComponent implements View.OnClickListener {
    @Override
    protected void onCreate() {
        setOnClickListener(this, R.id.mediaDemoButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mediaDemoButton:
                MediaDemoAct.go(getContext());
                break;

            default:
                break;
        }
    }
}
