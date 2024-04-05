package com.example.administrator.androidtest.Test.MainTest.WidgetDemo;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;

public class WidgetDemoComponent extends TestActivityComponent {

    public WidgetDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        setOnClickListener(this, R.id.widgetDemoButton);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.widgetDemoButton:
                WidgetDemoAct.start(getContext());
                break;
        }
    }
}
