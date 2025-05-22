package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.bear.libcomponent.component.ActivityComponent;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.databinding.ActBizDemoListBinding;
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.Detail.TikTokVideoDetailActivity;

public class TikTokDemoComponent extends ActivityComponent<ActBizDemoListBinding> implements View.OnClickListener {
    public TikTokDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        getBinding().tiktokDemoButton.setOnClickListener(this);
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
