package com.example.administrator.androidtest.Test.ValTest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.bear.libcomponent.ComponentAct;
import com.example.administrator.androidtest.App;
import com.example.administrator.androidtest.R;

public class AppValTestAct extends ComponentAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppData.test1_int1.inc();
        AppData.test1_int2.dec();
        AppData.test1_int3.plus(5);
        AppData.test1_int4.plus(5);
        AppData.test1_int5.minus(5);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_commom_test;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                Log.d(TAG, "onClick: AppData.test1_int1 = " + AppData.test1_int1.get() + ", AppData.test1_int2 = " + AppData.test1_int2.get()
                        + ", AppData.test1_int3 = " + AppData.test1_int3.get() + ", AppData.test1_int4 = " + AppData.test1_int4.get() + ", AppData.test1_int5 = " + AppData.test1_int5.get());
                break;

            case R.id.bt_2:
                Log.d(TAG, "onClick: AppData.test0_float1 = " + AppData.test0_float1.get());
                break;

            case R.id.bt_3:
                Log.d(TAG, "onClick: AppData.test2_bool1 = " + AppData.test2_bool1.get() + ", AppData.test2_string2 = " + AppData.test2_string2.get());
                break;

            case R.id.bt_4:
                AppData.test1_int1.inc();
                AppData.test1_int2.dec();
                AppData.test1_int3.plus(5);
                AppData.test1_int4.plus(5);
                AppData.test1_int5.minus(5);

                AppData.test0_float1.reset();

                AppData.test2_bool1.reverse();
                AppData.test2_string2.reset();
                Log.d(TAG, "onClick: AppData.test1_int1 = " + AppData.test1_int1.get() + ", AppData.test1_int2 = " + AppData.test1_int2.get()
                        + ", AppData.test1_int3 = " + AppData.test1_int3.get() + ", AppData.test1_int4 = " + AppData.test1_int4.get() + ", AppData.test1_int5 = " + AppData.test1_int5.get());
                break;
        }
    }
}
