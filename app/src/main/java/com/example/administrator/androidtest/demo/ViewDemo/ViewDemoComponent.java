package com.example.administrator.androidtest.demo.ViewDemo;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.TestActivityComponent;

public class ViewDemoComponent extends TestActivityComponent {

    public ViewDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        getBinding().viewDemoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.viewDemoButton) {
            ViewDemoActivity.start(getContext());
        }
    }
}
