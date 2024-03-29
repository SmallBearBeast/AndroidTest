package com.example.administrator.androidtest.Test.MainTest.BizDemo;

import android.annotation.SuppressLint;
import android.view.View;

import com.bear.libcomponent.component.ActivityComponent;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.BizDemo.PlayerDemo.PlayerDemoAct;

public class BizDemoComponent extends ActivityComponent implements View.OnClickListener{
    private static final String TAG = "BizDemoComponent";

    @Override
    protected void onCreate() {
        setOnClickListener(this, R.id.bizDemoButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bizDemoButton:
                PlayerDemoAct.go(getContext());
                break;

            default:
                break;
        }
    }
}
