package com.example.administrator.androidtest.Test.OtherTest;

import android.view.View;
import android.widget.TextView;

import com.bear.libcomponent.ComponentAct;
import com.example.administrator.androidtest.R;

public class OtherTestAct extends ComponentAct {
    @Override
    protected int layoutId() {
        return R.layout.act_commom_test;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                TextView textTv = findViewById(R.id.tv_text_1);
                textTv.setText(getString(R.string.format_string_text, "hugo.wu", 22));
                break;
        }
    }
}
