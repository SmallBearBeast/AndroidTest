package com.example.administrator.androidtest.demo.FloatServiceTest;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.ShareTest.SystemShareAct;
import com.example.administrator.androidtest.demo.TestActivityComponent;

public class FloatServiceTestComponent extends TestActivityComponent {

    public FloatServiceTestComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        setOnClickListener(this, R.id.startFloatServiceButton, R.id.stopFloatServiceButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startFloatServiceButton:
                SystemShareAct.start(getContext());
//                TestFloatService.startFloatService(getContext(), TestFloatService.class);
                break;
            case R.id.stopFloatServiceButton:
                TestFloatService.stopFloatService(getActivity(), TestFloatService.class);
                break;
        }
    }
}
