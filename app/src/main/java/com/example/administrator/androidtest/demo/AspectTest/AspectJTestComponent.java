package com.example.administrator.androidtest.demo.AspectTest;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.TestActivityComponent;

public class AspectJTestComponent extends TestActivityComponent {

    public AspectJTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        getBinding().aspectjTestButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.aspectjTestButton:
                AspectTestActivity.start(getContext());
                break;

            default:
                break;
        }
    }
}
