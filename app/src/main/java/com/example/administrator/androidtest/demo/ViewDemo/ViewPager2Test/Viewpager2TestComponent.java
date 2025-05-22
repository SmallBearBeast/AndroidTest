package com.example.administrator.androidtest.demo.ViewDemo.ViewPager2Test;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.ViewDemo.BaseViewDemoComponent;

public class Viewpager2TestComponent extends BaseViewDemoComponent {
    public Viewpager2TestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        getBinding().viewpager2TestButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewpager2TestButton:
                ViewPager2Activity.start(getContext());
                break;

            default:
                break;
        }
    }
}
