package com.example.administrator.androidtest.Widget.CompoundDrawableTextView;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.administrator.androidtest.BaseViewSetAct;
import com.example.administrator.androidtest.Common.Util.ToastUtils;
import com.example.administrator.androidtest.R;

public class CompoundDrawableTextViewAct extends BaseViewSetAct {
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
