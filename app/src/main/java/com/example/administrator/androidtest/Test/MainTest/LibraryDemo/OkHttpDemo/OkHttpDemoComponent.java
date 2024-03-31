package com.example.administrator.androidtest.Test.MainTest.LibraryDemo.OkHttpDemo;

import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;

public class OkHttpDemoComponent extends TestActivityComponent {

    @Override
    protected void onCreate() {
        super.onCreate();
        setOnClickListener(this, R.id.okhttpDemoButton);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.okhttpDemoButton:
                OkHttpDemoAct.start(getContext());
                break;
        }
    }
}
