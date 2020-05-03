package com.example.administrator.androidtest.Test.BottomViewTest;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.administrator.androidtest.R;
import com.example.libframework.CoreUI.ComponentAct;

public class BottomViewTestAct extends ComponentAct {
    private TestBottomView mTestBottomView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTestBottomView = new TestBottomView(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_commom_test;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                mTestBottomView.show();
                break;
        }
    }
}
