package com.example.administrator.androidtest.Test.MainTest.ComponentDemo;

import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;

public class DemoComponent extends TestActivityComponent {

    @Override
    protected void onCreate() {
        super.onCreate();
        setOnClickListener(this, R.id.componentTestButton);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.componentTestButton:
                ComponentDemoAct.start(getContext());
                break;
        }
    }
}
