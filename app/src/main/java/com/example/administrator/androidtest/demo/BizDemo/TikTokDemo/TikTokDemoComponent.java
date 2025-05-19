package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.Detail.TikTokVideoDetailActivity;
import com.example.administrator.androidtest.demo.TestActivityComponent;

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
                TikTokVideoDetailActivity.go(getContext());
                break;

            default:
                break;
        }
    }
}
