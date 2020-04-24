package com.example.administrator.androidtest.Test.WindowTest;

import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.libbase.Com.FloatService;
import com.example.libbase.Util.ToastUtil;

public class TestFloatService extends FloatService {
    @Override
    protected int layoutId() {
        return R.layout.window_floating_test;
    }

    @Override
    protected void initView(View contentView) {
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("Hello World");
            }
        });
    }
}
