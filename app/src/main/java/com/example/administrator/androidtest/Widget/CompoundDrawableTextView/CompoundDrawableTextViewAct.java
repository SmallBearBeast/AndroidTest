package com.example.administrator.androidtest.Widget.CompoundDrawableTextView;

import android.os.Bundle;

import com.example.administrator.androidtest.Base.ComponentAct;
import com.example.administrator.androidtest.Common.Util.ToastUtils;
import com.example.administrator.androidtest.R;

public class CompoundDrawableTextViewAct extends ComponentAct {
    private CompoundDrawableTextView cdtvText;
    @Override
    protected int layoutId() {
        return R.layout.act_compound_drawable_tv;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        cdtvText = findViewById(R.id.cdtv_text);
        cdtvText.setClickWrapper(new CompoundDrawableTextView.ClickWrapper(){

            @Override
            void onClick() {
                ToastUtils.showToast("onClick");
            }
        });
    }
}
