package com.example.administrator.androidtest.Test.MainTest.ViewPager2Test;

import android.annotation.SuppressLint;
import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestComponent;

public class Viewpager2TestComponent extends TestComponent {
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
