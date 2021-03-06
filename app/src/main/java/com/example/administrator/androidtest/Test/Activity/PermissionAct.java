package com.example.administrator.androidtest.Test.Activity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bear.libcomponent.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.libbase.Util.ScreenUtil;

import java.util.List;

public class PermissionAct extends ComponentAct {
    private Button btAskPermission;
    private TextView tvText;

    @Override
    protected int layoutId() {
        return R.layout.act_permission;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btAskPermission = findViewById(R.id.bt_ask_permission);
        tvText = findViewById(R.id.tv_text);
        tvText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: checkNavigationBarShow = " + ScreenUtil.hasNavigationBar(getWindow()) + ", navigationBarHeight = " + ScreenUtil.getNavigationBarHeight(getWindow()));
            }
        });
        btAskPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CONTACTS}, new PermissionListener() {
                    @Override
                    public void onPermissionRequest(List<String> permissionSuccessArray, List<String> permissionFailArray) {

                    }
                });
            }
        });
        ScreenUtil.normalScreen(getWindow(), ContextCompat.getColor(this, R.color.cl_blue_5), 0, findViewById(R.id.rootPermissionView));
//        ScreenUtil.immersiveFullScreen(getWindow());
    }

    @Override
    protected void onResume() {
        super.onResume();
//        ScreenUtil.immersiveFullScreen(getWindow());
    }
}
