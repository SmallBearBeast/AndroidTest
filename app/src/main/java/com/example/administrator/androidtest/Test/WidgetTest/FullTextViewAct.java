package com.example.administrator.androidtest.Test.WidgetTest;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Widget.FullTextView.FullTextView;
import com.example.administrator.androidtest.Widget.FullTextView.TextOpt;
import com.example.libframework.CoreUI.ComponentAct;

public class FullTextViewAct extends ComponentAct {
    private FullTextView mFtvText;

    @Override
    protected int layoutId() {
        return R.layout.act_full_text_view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFtvText = findViewById(R.id.ftv_text);
        TextOpt bgOpt = TextOpt.bgOpt(0, 5, Color.RED);
        TextOpt fgOpt = TextOpt.fgOpt(5, mFtvText.length(), Color.BLUE);
        mFtvText.bg(bgOpt).fg(fgOpt).done();
        findViewById(R.id.bt_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFtvText.setText("9876543210");
            }
        });
    }
}
