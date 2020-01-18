package com.example.administrator.androidtest.Test.WidgetTest;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Widget.CompoundDrawableTextView;
import com.example.libbase.Util.ToastUtil;
import com.example.libframework.CoreUI.ComponentAct;

public class CompoundDrawableTextViewAct extends ComponentAct {
    private CompoundDrawableTextView cdtvText;

    @Override
    protected int layoutId() {
        return R.layout.act_compound_drawable_tv;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cdtvText = findViewById(R.id.cdtv_text);
        cdtvText.setClickWrapper(new CompoundDrawableTextView.ClickWrapper() {

            @Override
            protected void onClick() {
                ToastUtil.showToast("onClick");
            }

        });
    }
}
