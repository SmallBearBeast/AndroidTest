package com.example.administrator.androidtest.demo.MediaDemo;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.TestActivityComponent;

public class MediaDemoComponent extends TestActivityComponent {
    public MediaDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        getViewBinding().mediaDemoButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mediaDemoButton:
                MediaDemoActivity.go(getContext());
                break;

            default:
                break;
        }
    }
}
