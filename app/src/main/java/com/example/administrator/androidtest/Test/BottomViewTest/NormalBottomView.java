package com.example.administrator.androidtest.Test.BottomViewTest;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import com.example.administrator.androidtest.R;

import com.example.libbase.Com.BottomView;
import com.example.libbase.Util.ResourceUtil;
import com.example.libbase.Util.ToastUtil;
import com.example.libbase.Util.XmlDrawableUtil;

public class NormalBottomView extends BottomView implements View.OnClickListener{
    public NormalBottomView(Activity activity) {
        super(activity);
        contentView(R.layout.view_normal_bottom_test);
//        findViewById(R.id.tv_1).clickListener(this);
//        findViewById(R.id.tv_2).clickListener(this);
//        findViewById(R.id.tv_2).clickListener(this);
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
        }
    }
}
