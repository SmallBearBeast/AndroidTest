package com.example.administrator.androidtest.Permission;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.androidtest.BaseViewSetAct;
import com.example.administrator.androidtest.Common.Util.FullScreenUtils;
import com.example.administrator.androidtest.PermissionDialog;
import com.example.administrator.androidtest.R;

import java.util.Arrays;
import java.util.List;

public class PermissionAct extends BaseViewSetAct{
    PermissionDialog dialog;
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
        dialog = new PermissionDialog(this);
        btAskPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        FullScreenUtils.hideNavigationBar(this);
        FullScreenUtils.hideStatusBar(this);
    }

    @Override
    protected void onPermissionRequest(List<String> permissionSuccessArray, List<String> permissionFailArray) {
        String text = Arrays.toString(permissionSuccessArray.toArray()) + "\\n" + Arrays.toString(permissionFailArray.toArray());
        tvText.setText(text);
    }
}
