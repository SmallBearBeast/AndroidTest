package com.example.administrator.androidtest.Test.Activity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.administrator.androidtest.R;
import com.example.libbase.Util.ScreenUtil;
import com.example.libframework.CoreUI.ComponentAct;

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
        ScreenUtil.normalScreen(this, R.color.cl_transparent, true, tvText);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScreenUtil.normalScreen(this, R.color.cl_transparent, true, null);
    }
}