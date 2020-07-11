package com.example.administrator.androidtest.Test.WindowTest;

import android.view.View;

import com.bear.libcomponent.ComponentAct;
import com.example.administrator.androidtest.R;

public class WindowTestAct extends ComponentAct {

    @Override
    protected int layoutId() {
        return R.layout.act_commom_test;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                TestFloatService.startFloatService(this, TestFloatService.class);
                break;

            case R.id.bt_2:
                TestFloatService.stopFloatService(this, TestFloatService.class);
                break;
        }
    }
}
