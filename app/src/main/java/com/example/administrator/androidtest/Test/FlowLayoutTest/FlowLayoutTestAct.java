package com.example.administrator.androidtest.Test.FlowLayoutTest;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Widget.FlowLayout;
import com.example.libbase.Util.DensityUtil;
import com.example.libbase.Util.ToastUtil;
import com.example.libbase.Util.XmlDrawableUtil;
import com.example.libframework.CoreUI.ComponentAct;

public class FlowLayoutTestAct extends ComponentAct {
    @Override
    protected int layoutId() {
        return R.layout.act_flow_layout_test;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlowLayout flowLayout = findViewById(R.id.fl_container);
        flowLayout.setFlowClickListener(new FlowLayout.OnFlowClickListener() {
            @Override
            public void onClick(View view) {
                if (view instanceof TextView) {
                    TextView tv = (TextView) view;
                    ToastUtil.showToast(String.valueOf(tv.getText()));
                }
            }
        });
        String[] texts = new String[]{
                "Android", "Java", "PHP", "C++", "Android", "Java", "PHP", "C++", "Android", "Java", "PHP", "C++",
                "Android", "Java", "PHP", "C++", "Android", "Java", "PHP", "C++", "Android", "Java", "PHP", "C++"
        };
        for (int i = 0; i < texts.length; i++) {
            flowLayout.addView(createTv(texts[i]));
        }
    }

    private TextView createTv(String text) {
        TextView tv = new TextView(this);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText(text);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        int padding = DensityUtil.dp2Px(5);
        tv.setPadding(padding, padding, padding, padding);
        XmlDrawableUtil.cornerRect(R.color.cl_blue_5, 3).setView(tv);
        return tv;
    }
}
