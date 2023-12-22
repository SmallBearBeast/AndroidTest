package com.example.administrator.androidtest.Test.MainTest.OkHttpTest;

import android.view.View;

import com.example.administrator.androidtest.Test.MainTest.TestComponent;

public class OkHttpTestComponent extends TestComponent {

    @Override
    protected void onCreate() {
        super.onCreate();
    }

    @Override
    public void onClick(View view) {
        OkHttpAct.start(getContext());
    }
}
