package com.example.administrator.androidtest.demo.OtherDemo;

import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.TestActivityComponent;

public class OtherDemoComponent extends TestActivityComponent {
    public OtherDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        getBinding().otherDemoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.otherDemoButton) {
            OtherDemoActivity.start(getContext());
        }
    }
}
