package com.example.administrator.androidtest.Test.MainTest.ViewDemo.ViewGetSizeTest;

import android.annotation.SuppressLint;
import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestComponent;

public class ViewGetSizeTestComponent extends TestComponent {

    @Override
    protected void onCreate() {
        super.onCreate();
        setOnClickListener(this, R.id.viewSizeTestButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewSizeTestButton:
                ViewGetSizeTestAct.start(getContext());
                break;

            default:
                break;
        }
    }
}
