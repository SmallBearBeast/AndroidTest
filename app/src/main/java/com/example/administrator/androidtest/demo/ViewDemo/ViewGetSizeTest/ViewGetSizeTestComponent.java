package com.example.administrator.androidtest.demo.ViewDemo.ViewGetSizeTest;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.ViewDemo.BaseViewDemoComponent;

public class ViewGetSizeTestComponent extends BaseViewDemoComponent {

    public ViewGetSizeTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        getBinding().viewSizeTestButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewSizeTestButton:
                ViewGetSizeTestActivity.start(getContext());
                break;

            default:
                break;
        }
    }
}
