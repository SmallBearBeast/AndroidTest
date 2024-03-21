package com.example.administrator.androidtest.Test.MainTest.WidgetDemo;

import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestComponent;

public class WidgetDemoComponent extends TestComponent {

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
