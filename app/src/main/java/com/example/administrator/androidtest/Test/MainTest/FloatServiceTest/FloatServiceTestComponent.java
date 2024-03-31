package com.example.administrator.androidtest.Test.MainTest.FloatServiceTest;

import android.annotation.SuppressLint;
import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.ShareTest.SystemShareAct;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;

public class FloatServiceTestComponent extends TestActivityComponent {

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
