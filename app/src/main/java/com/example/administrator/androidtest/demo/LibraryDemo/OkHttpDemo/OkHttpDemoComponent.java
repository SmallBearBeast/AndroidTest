package com.example.administrator.androidtest.demo.LibraryDemo.OkHttpDemo;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.LibraryDemo.BaseLibraryDemoComponent;

public class OkHttpDemoComponent extends BaseLibraryDemoComponent {

    public OkHttpDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        getBinding().okhttpDemoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.okhttpDemoButton:
                OkHttpDemoActivity.start(getContext());
                break;
        }
    }
}
