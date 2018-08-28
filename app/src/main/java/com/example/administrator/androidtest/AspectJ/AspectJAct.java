package com.example.administrator.androidtest.AspectJ;

import android.util.Log;
import android.view.View;

import com.example.administrator.androidtest.BaseAct;
import com.example.administrator.androidtest.R;

/**
 * Created by Administrator on 2018/8/23.
 */

public class AspectJAct extends BaseAct {
    private static final String TAG = "AspectJAct";

    @Override
    protected int layoutId() {
        return R.layout.act_aspectj;
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_aspectj_1:
                onClickAspectJ_1();
                break;

            case R.id.bt_aspectj_2:
                onClickAspectJ_2();
                break;

            case R.id.bt_aspectj_3:
                onClickAspectJ_3();
                break;

            case R.id.bt_aspectj_4:
                onClickAspectJ_4();
                break;
        }
    }

    private void onClickAspectJ_1() {
        Log.e(TAG, "onClick: " + "bt_aspectj_1");
    }

    private void onClickAspectJ_2() {
        Log.e(TAG, "onClick: " + "bt_aspectj_2");
    }

    private void onClickAspectJ_3() {
        Log.e(TAG, "onClick: " + "bt_aspectj_3");
    }

    @DebugTool
    private void onClickAspectJ_4() {
        Log.e(TAG, "onClick: " + "bt_aspectj_4");
    }
}
