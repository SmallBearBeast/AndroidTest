package com.example.administrator.androidtest.Test.OtherTest;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bear.libcomponent.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Widget.FullTextView.FullTextView;
import com.example.administrator.androidtest.Widget.FullTextView.TextOpt;
import com.example.administrator.androidtest.Widget.LikeView.LikeView;

public class OtherTestAct extends ComponentAct {
    private FullTextView ftvFullText;
    private EditText editText;
    private LikeView likeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editText = findViewById(R.id.et_no_show_input_keyboard);
        ftvFullText = findViewById(R.id.ftv_full_text);
        likeView = findViewById(R.id.lv_like_heart);
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

            case R.id.bt_full_text_click:
                TextOpt bgOpt = TextOpt.bgOpt(0, 5, Color.RED);
                TextOpt fgOpt = TextOpt.fgOpt(5, ftvFullText.length(), Color.BLUE);
                ftvFullText.bg(bgOpt).fg(fgOpt).done();
                break;

            case R.id.lv_like_heart:
                if (likeView.isLike()) {
                    likeView.like();
                } else {
                    likeView.unLike();
                }
                break;
        }
    }
}
