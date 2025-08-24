package com.example.administrator.androidtest.demo.widgetDemo;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.TestActivityComponent;

public class WidgetDemoComponent extends TestActivityComponent {

    public WidgetDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        getViewBinding().widgetDemoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.widgetDemoButton:
                WidgetDemoActivity.start(getContext());
                break;
        }
    }
}
