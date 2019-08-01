package com.example.administrator.androidtest.Test.ViewTest;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.administrator.androidtest.R;
import com.example.libframework.ActAndFrag.ComponentAct;

public class ViewTestAct extends ComponentAct {
    @Override
    protected int layoutId() {
        return R.layout.act_view_test;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        final TextView tvTest_1 = findViewById(R.id.tv_test_1);
        tvTest_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = tvTest_1.getVisibility();
                int newVisibility = visibility == View.VISIBLE ? View.INVISIBLE
                        : visibility == View.INVISIBLE ? View.GONE
                        : visibility == View.GONE ? View.VISIBLE : View.GONE;
                tvTest_1.setVisibility(newVisibility);
            }
        });
    }
}
