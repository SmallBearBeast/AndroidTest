package com.example.administrator.androidtest.Test.WindowTest;

import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.libframework.CoreUI.ComponentAct;

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
