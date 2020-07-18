package com.example.administrator.androidtest.Test.ValTest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.bear.libcomponent.ComponentAct;
import com.example.administrator.androidtest.R;

public class AppValTestAct extends ComponentAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppVariable.test1_int1.inc();
        AppVariable.test1_int2.dec();
        AppVariable.test1_int3.plus(5);
        AppVariable.test1_int4.plus(5);
        AppVariable.test1_int5.minus(5);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_commom_test;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                Log.d(TAG, "onClick: AppVariable.test1_int1 = " + AppVariable.test1_int1.get() + ", AppVariable.test1_int2 = " + AppVariable.test1_int2.get()
                        + ", AppVariable.test1_int3 = " + AppVariable.test1_int3.get() + ", AppVariable.test1_int4 = " + AppVariable.test1_int4.get() + ", AppVariable.test1_int5 = " + AppVariable.test1_int5.get());
                break;

            case R.id.bt_2:
                Log.d(TAG, "onClick: AppVariable.test0_float1 = " + AppVariable.test0_float1.get());
                break;

            case R.id.bt_3:
                Log.d(TAG, "onClick: AppVariable.test2_bool1 = " + AppVariable.test2_bool1.get() + ", AppVariable.test2_string2 = " + AppVariable.test2_string2.get());
                break;

            case R.id.bt_4:
                AppVariable.test1_int1.inc();
                AppVariable.test1_int2.dec();
                AppVariable.test1_int3.plus(5);
                AppVariable.test1_int4.plus(5);
                AppVariable.test1_int5.minus(5);

                AppVariable.test0_float1.reset();

                AppVariable.test2_bool1.reverse();
                AppVariable.test2_string2.reset();
                Log.d(TAG, "onClick: AppVariable.test1_int1 = " + AppVariable.test1_int1.get() + ", AppVariable.test1_int2 = " + AppVariable.test1_int2.get()
                        + ", AppVariable.test1_int3 = " + AppVariable.test1_int3.get() + ", AppVariable.test1_int4 = " + AppVariable.test1_int4.get() + ", AppVariable.test1_int5 = " + AppVariable.test1_int5.get());
                break;
        }
    }
}
