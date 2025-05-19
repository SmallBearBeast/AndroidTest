package com.example.administrator.androidtest.other.WidgetTest;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.widget.CompoundDrawableTextView;
import com.example.libcommon.util.ToastUtil;

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
