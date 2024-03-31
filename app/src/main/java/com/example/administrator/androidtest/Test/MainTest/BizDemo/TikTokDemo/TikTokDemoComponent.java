package com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo;

import android.annotation.SuppressLint;
import android.view.View;

import com.bear.libcomponent.component.ActivityComponent;
import com.example.administrator.androidtest.R;

public class TikTokDemoComponent extends ActivityComponent implements View.OnClickListener {
    @Override
    protected void onCreate() {
        setOnClickListener(this, R.id.tiktokDemoButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tiktokDemoButton:
                TikTokDemoAct.go(getContext());
                break;

            default:
                break;
        }
    }
}
