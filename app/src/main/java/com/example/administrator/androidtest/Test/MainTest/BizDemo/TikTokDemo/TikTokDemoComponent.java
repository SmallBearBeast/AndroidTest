package com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo.List.TikTokDemoAct;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;

public class TikTokDemoComponent extends TestActivityComponent {
    public TikTokDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

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
