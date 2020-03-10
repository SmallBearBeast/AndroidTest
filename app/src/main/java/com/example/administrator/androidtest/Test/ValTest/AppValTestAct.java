package com.example.administrator.androidtest.Test.ValTest;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.administrator.androidtest.R;
import com.example.libframework.CoreUI.ComponentAct;
import com.example.libmmf.AppVal.AppIntVal;

public class AppValTestAct extends ComponentAct {
    private AppIntVal[] appIntVals = new AppIntVal[1000];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < appIntVals.length; i++) {
            appIntVals[i] = new AppIntVal("AppIntVal_" + i, i);
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.act_commom_test;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                for (int i = 0; i < appIntVals.length; i++) {
                    appIntVals[i].set(i);
                }
                break;

            case R.id.bt_2:
                appIntVals[0].dec();
                break;

            case R.id.bt_3:
                appIntVals[0].inc();
                break;

            case R.id.bt_4:
                break;
        }
    }
}
