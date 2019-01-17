package com.example.administrator.androidtest.Common.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.administrator.androidtest.Base.ActAndFrag.ComponentAct;
import com.example.administrator.androidtest.Common.Util.XmlDrawableUtil;
import com.example.administrator.androidtest.R;

public class XmlDrawableAct extends ComponentAct {
    private TextView mTv_1;
    private TextView mTv_2;
    private TextView mTv_3;
    private TextView mTv_4;
    private ToggleButton mTb_5;
    private ToggleButton mTb_6;
    @Override
    protected int layoutId() {
        return R.layout.act_xml_drawable;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mTv_1 = findViewById(R.id.tv_1);
        mTv_2 = findViewById(R.id.tv_2);
        mTv_3 = findViewById(R.id.tv_3);
        mTv_4 = findViewById(R.id.tv_4);
        mTb_5 = findViewById(R.id.tb_5);
        mTb_6 = findViewById(R.id.tb_6);


//        XmlDrawableUtil.shapeCornerRect(R.color.cl_blue_5, 10).setView(mTv_1);
        XmlDrawableUtil.selector(R.drawable.ic_launcher_background, R.drawable.shape_corner_10).setView(mTv_2);
        XmlDrawableUtil.shapeCircle(R.color.cl_red_t_6).setView(mTv_3);
        XmlDrawableUtil.shapeStrokeRect(R.color.cl_blue_5, 10, R.color.cl_red_t_6, 1).setView(mTv_4);
        XmlDrawableUtil.selector(new ColorDrawable(Color.YELLOW), new ColorDrawable(Color.BLACK), new ColorDrawable(Color.BLUE)).setView(mTb_5);
        XmlDrawableUtil.selectorCircle(R.color.cl_blue_5, R.color.cl_oriange, R.color.cl_red).setView(mTb_6);
    }
}
