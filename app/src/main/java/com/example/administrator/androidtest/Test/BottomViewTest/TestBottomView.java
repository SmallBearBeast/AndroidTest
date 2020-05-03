package com.example.administrator.androidtest.Test.BottomViewTest;

import android.app.Activity;

import com.example.administrator.androidtest.R;
import com.example.libbase.Com.BottomView;

public class TestBottomView extends BottomView {
    protected TestBottomView(Activity activity) {
        super(activity);
        contentView(R.layout.view_bottom_test);
    }
}
