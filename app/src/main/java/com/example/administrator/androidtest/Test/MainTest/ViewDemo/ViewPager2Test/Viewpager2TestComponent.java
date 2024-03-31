package com.example.administrator.androidtest.Test.MainTest.ViewDemo.ViewPager2Test;

import android.annotation.SuppressLint;
import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;

public class Viewpager2TestComponent extends TestActivityComponent {
    @Override
    protected void onCreate() {
        setOnClickListener(this, R.id.viewpager2TestButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewpager2TestButton:
                ViewPager2Act.start(getContext());
                break;

            default:
                break;
        }
    }
}
