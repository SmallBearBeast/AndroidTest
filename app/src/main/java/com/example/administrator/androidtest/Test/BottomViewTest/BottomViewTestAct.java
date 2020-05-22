package com.example.administrator.androidtest.Test.BottomViewTest;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.administrator.androidtest.R;
import com.example.libframework.CoreUI.ComponentAct;

public class BottomViewTestAct extends ComponentAct {

    @Override
    protected int layoutId() {
        return R.layout.act_commom_test;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                new NsBottomView(this).hideVelocity(1000).show();
                break;

            case R.id.bt_2:
                new RvBottomView(this).show();
                break;

            case R.id.bt_3:
                new NormalBottomView(this).hideVelocity(2000).show();
                break;
        }
    }
}
