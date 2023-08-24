package com.example.administrator.androidtest.Test.MainTest.BottomViewTest;

import android.annotation.SuppressLint;
import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestComponent;

public class BottomViewTestComponent extends TestComponent {

    @Override
    protected void onCreate() {
        setOnClickListener(this, R.id.normalBottomViewButton, R.id.nsBottomViewButton, R.id.rvBottomViewButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.normalBottomViewButton:
                new NormalBottomView(getActivity()).hideVelocity(2000).show();
                break;

            case R.id.nsBottomViewButton:
                new NsBottomView(getActivity()).hideVelocity(1000).show();
                break;

            case R.id.rvBottomViewButton:
                new RvBottomView(getActivity()).show();
                break;

            default:
                break;
        }
    }
}
