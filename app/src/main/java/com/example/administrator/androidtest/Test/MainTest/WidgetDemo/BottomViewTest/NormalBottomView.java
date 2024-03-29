package com.example.administrator.androidtest.Test.MainTest.WidgetDemo.BottomViewTest;

import android.app.Activity;
import android.view.View;

import com.example.administrator.androidtest.R;

import com.example.administrator.androidtest.Widget.BottomView;
import com.example.libbase.Util.ToastUtil;

public class NormalBottomView extends BottomView implements View.OnClickListener {
    public NormalBottomView(Activity activity) {
        super(activity);
        contentView(R.layout.view_normal_bottom_test);
        findViewById(R.id.tv_1).setOnClickListener(this);
//        findViewById(R.id.tv_2).setOnClickListener(this);
//        findViewById(R.id.tv_3).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.redView).setOnClickListener(this);
        findViewById(R.id.blackView).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_1:
                ToastUtil.showToast("tv_1");
                break;

            case R.id.tv_2:
                ToastUtil.showToast("tv_2");
                break;

            case R.id.tv_3:
                ToastUtil.showToast("tv_3");
                break;

            case R.id.button:
                ToastUtil.showToast("button");
                break;

            case R.id.redView:
                ToastUtil.showToast("redView");
                break;

            case R.id.blackView:
                ToastUtil.showToast("blackView");
                break;
        }
    }
}
