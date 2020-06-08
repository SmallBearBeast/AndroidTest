package com.example.administrator.androidtest.Test.WidgetTest;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Widget.MoreTextView;
import com.example.libbase.Util.DensityUtil;
import com.example.libframework.CoreUI.ComponentAct;

public class MoreTextViewAct extends ComponentAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MoreTextView moreTextView = findViewById(R.id.mtv_text);
//        moreTextView.initWidth(DensityUtil.getScreenWidth());
//        moreTextView.setMaxLines(Integer.MAX_VALUE);
//        moreTextView.setCloseInNewLine(true);
//        moreTextView.setHasAnimation(true);
//        moreTextView.setOriginalText("Hello World最近的需求中，需要用到一个横向、竖向同时可滚动的 ViewPager，记住，是横向、竖向同时滚动，不是横竖切换。我想了想，难点在于竖向。对于竖向的 ViewPager，我似乎记得 Jake Wharton 大神写过一个叫 DirectionalViewPager 的框架");
    }

    @Override
    protected int layoutId() {
        return R.layout.act_more_text_view_act;
    }
}
