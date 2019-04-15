package com.example.administrator.androidtest.Test.DrawableTest;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import com.example.administrator.androidtest.Base.ActAndFrag.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.XmlDrawableTest.TestDrawable;

public class DrawableTestAct extends ComponentAct {
    private View mVTest_1;
    @Override
    protected int layoutId() {
        return R.layout.act_drawable_test;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mVTest_1 = findViewById(R.id.v_test_1);
        mVTest_1.setBackground(new ColorDrawable(Color.BLACK));
    }
}
