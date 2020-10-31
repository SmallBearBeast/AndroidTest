package com.example.administrator.androidtest.Test.OtherTest;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bear.libcomponent.ComponentAct;
import com.example.administrator.androidtest.R;

public class OtherTestAct extends ComponentAct {
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editText = findViewById(R.id.et_no_show_input_keyboard);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_other_test;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_format_text_click:
                TextView textTv = findViewById(R.id.tv_format_text);
                textTv.setText(getString(R.string.format_string_text, "hugo.wu", 22));
                break;

            case R.id.bt_no_show_input_keyboard_mask_click:
                // EditText get focus but not to show input keyboard
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    editText.setShowSoftInputOnFocus(false);
                    editText.setCursorVisible(false);
                }
                break;
        }
    }
}
