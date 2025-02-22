package com.example.administrator.androidtest.demo.ComponentDemo;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.TestActivityComponent;

public class DemoComponent extends TestActivityComponent {

    public DemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

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
