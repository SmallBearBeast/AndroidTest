package com.example.administrator.androidtest.demo.FloatServiceTest;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.widget.FloatService;
import com.bear.libcommon.util.ToastUtil;

public class TestFloatService extends FloatService {
    @SuppressLint("SetTextI18n")
    @Override
    protected View floatView() {
        TextView tv = new TextView(this);
        tv.setText("Hello World");
        tv.setBackgroundResource(R.color.cl_red);
        tv.setTextColor(ContextCompat.getColor(this, R.color.cl_white));
        return tv;
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
