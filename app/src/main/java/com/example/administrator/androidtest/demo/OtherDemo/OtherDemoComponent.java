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
        setOnClickListener(this, R.id.otherDemoButton);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.otherDemoButton) {
            OtherDemoAct.start(getContext());
        }
    }
}
