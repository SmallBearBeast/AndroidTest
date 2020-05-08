package com.example.administrator.androidtest.Test.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;


import androidx.annotation.Nullable;

import com.example.administrator.androidtest.R;
import com.example.libbase.Util.XmlDrawableUtil;
import com.example.libframework.CoreUI.ComponentAct;

public class XmlDrawableAct extends ComponentAct {
    private TextView mTv_1;
    private TextView mTv_2;
    private TextView mTv_3;
    private TextView mTv_4;
    private TextView mTv_5;
    private TextView mTv_6;
    private TextView mTv_7;
    private TextView mTv_8;

    private ToggleButton mTb_1;
    private ToggleButton mTb_2;

    @Override
    protected int layoutId() {
        return R.layout.act_xml_drawable;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTv_1 = findViewById(R.id.tv_1);
        mTv_2 = findViewById(R.id.tv_2);
        mTv_3 = findViewById(R.id.tv_3);
        mTv_4 = findViewById(R.id.tv_4);
        mTv_5 = findViewById(R.id.tv_5);
        mTv_6 = findViewById(R.id.tv_6);
        mTv_7 = findViewById(R.id.tv_7);
        mTv_8 = findViewById(R.id.tv_8);
        mTb_1 = findViewById(R.id.tb_1);
        mTb_2 = findViewById(R.id.tb_2);

        XmlDrawableUtil.slRect(R.color.colorFF5722, R.color.colorFF9800, 5.0f).setView(mTv_1);
        XmlDrawableUtil.slCircle(R.color.colorFF5722, R.color.colorFF9800).setView(mTv_2);
        XmlDrawableUtil.slAlphaRect(1f, 0.5f, R.color.color2196F3, 5).setView(mTv_3);
        XmlDrawableUtil.slAlphaCircle(1f, 0.5f, R.color.color2196F3).setView(mTv_4);
        XmlDrawableUtil.slGradientRect(new int[]{R.color.color009688, R.color.color4CAF50}, new int[]{R.color.color009688, R.color.color8BC34A}, GradientDrawable.Orientation.BOTTOM_TOP, 5).setView(mTv_5);
        XmlDrawableUtil.slGradientCircle(new int[]{R.color.color009688, R.color.color4CAF50}, new int[]{R.color.color009688, R.color.color8BC34A}, GradientDrawable.Orientation.BOTTOM_TOP).setView(mTv_6);
        XmlDrawableUtil.strokeRect(R.color.colorCDDC39, R.color.color3F51B5, 1, 5).setView(mTv_7);
        XmlDrawableUtil.strokeCircle(R.color.colorCDDC39, R.color.color3F51B5, 1).setView(mTv_8);

        XmlDrawableUtil.slGradientRect(new int[]{R.color.color009688, R.color.color4CAF50}, new int[]{R.color.color009688, R.color.color8BC34A}, GradientDrawable.Orientation.BOTTOM_TOP, 5)
                .setView(findViewById(R.id.tb_1));

        XmlDrawableUtil.slGradientCircle(new int[]{R.color.color009688, R.color.color4CAF50}, new int[]{R.color.color009688, R.color.color8BC34A}, GradientDrawable.Orientation.BOTTOM_TOP)
                .setView(findViewById(R.id.tb_2));
//        XmlDrawableUtil.slGradientCircle(new int[]{R.color.cl_blue_5, R.color.cl_red_t_6}, new int[]{R.color.cl_black_t_5, R.color.cl_red_t_6}, GradientDrawable.Orientation.BOTTOM_TOP)
//                .setView(findViewById(R.id.ll_1));
//
//        XmlDrawableUtil.slGradientCircle(new int[]{R.color.cl_blue_5, R.color.cl_red_t_6}, new int[]{R.color.cl_black_t_5, R.color.cl_red_t_6}, GradientDrawable.Orientation.BOTTOM_TOP)
//                .setView(findViewById(R.id.fl_1));
    }
}
