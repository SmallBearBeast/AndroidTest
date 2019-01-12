package com.example.administrator.androidtest.Permission;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.androidtest.Base.ComponentAct;
import com.example.administrator.androidtest.Common.Util.PermissionUtil;
import com.example.administrator.androidtest.Common.Util.ScreenUtil;
import com.example.administrator.androidtest.R;

import java.util.Arrays;
import java.util.List;

public class PermissionAct extends ComponentAct {
    private Button btAskPermission;
    private TextView tvText;
    @Override
    protected int layoutId() {
        return R.layout.act_permission;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        btAskPermission = findViewById(R.id.bt_ask_permission);
        tvText = findViewById(R.id.tv_text);
        btAskPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtil.requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CONTACTS,
                }, PermissionAct.this);
            }
        });

        ScreenUtil.normalScreen(this, R.color.cl_transparent, true, tvText);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        ScreenUtil.normalScreen(this, R.color.cl_transparent);
    }

    @Override
    protected void onPermissionRequest(List<String> permissionSuccessArray, List<String> permissionFailArray) {
        String text = Arrays.toString(permissionSuccessArray.toArray()) + "\\n" + Arrays.toString(permissionFailArray.toArray());
        tvText.setText(text);
    }
}
