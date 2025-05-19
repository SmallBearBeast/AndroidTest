package com.example.administrator.androidtest.demo.ScreenTest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.other.LogUtil;
import com.example.libcommon.Util.ScreenUtil;
import com.permissionx.guolindev.PermissionX;

public class ScreenAct extends ComponentAct {
    @Override
    protected int layoutId() {
        return R.layout.act_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtil.normalScreen(getWindow(), ContextCompat.getColor(this, R.color.cl_blue_5)
                , ContextCompat.getColor(this, R.color.cl_green_9), findViewById(R.id.rootPermissionView));
//        ScreenUtil.immersiveFullScreen(getWindow());
    }

    @Override
    protected void onResume() {
        super.onResume();
//        ScreenUtil.immersiveFullScreen(getWindow());
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getScreenUtilSizeButton:
                onGetScreenUtilSizeButtonClick();
                break;
            case R.id.askPermissionButton:
                onAskPermissionButtonClick();
                break;
            default:
                break;
        }
    }

    private void onGetScreenUtilSizeButtonClick() {
        boolean hasNavigationBar = ScreenUtil.hasNavigationBar(getWindow());
        int navigationBarHeight_1 = ScreenUtil.getNavigationBarHeight(getWindow());
        int navigationBarHeight_2 = ScreenUtil.getNavigationBarHeight();
        LogUtil.getInstance(TAG, "onGetScreenUtilSizeButtonClick")
                .of("hasNavigationBar", hasNavigationBar)
                .of("navigationBarHeight_1", navigationBarHeight_1)
                .of("navigationBarHeight_2", navigationBarHeight_2)
                .logI();
    }

    private void onAskPermissionButtonClick() {
        PermissionX.init(this).permissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_CONTACTS
        ).request((allGranted, grantedList, deniedList) -> LogUtil.getInstance(TAG, "onAskPermissionButtonClick")
                .of("grantedList", grantedList)
                .of("deniedList", deniedList)
                .logI());
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ScreenAct.class));
    }
}
