package com.example.administrator.androidtest.Test.DrawableTest;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.administrator.androidtest.R;
import com.example.libframework.ActAndFrag.ComponentAct;

public class DrawableTestAct extends ComponentAct {
    private static final String TAG = "DrawableTestAct";
    private View mVTest_1;
    private View mVTest_2;
    private View mVTest_4;
    @Override
    protected int layoutId() {
        return R.layout.act_drawable_test;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        new BitmapDrawable();
        mVTest_1 = findViewById(R.id.v_test_1);
        mVTest_2 = findViewById(R.id.v_test_2);
        mVTest_4 = findViewById(R.id.v_test_4);
        ColorDrawable drawable_1 = (ColorDrawable) getResources().getDrawable(R.drawable.color_drawable_test);
        ColorDrawable drawable_2 = (ColorDrawable) getResources().getDrawable(R.drawable.color_drawable_test);
//        ColorDrawable drawable_1 = new ColorDrawable(Color.BLACK);
//        ColorDrawable drawable_2 = new ColorDrawable(Color.BLACK);
        Log.i(TAG, "drawable_1.getConstantState() = " + drawable_1.getConstantState() + "drawable_2.getConstantState() = " + drawable_2.getConstantState());
        drawable_1.setColor(Color.RED);
        mVTest_1.setBackground(drawable_1);
        mVTest_2.setBackground(drawable_2);
        Drawable drawable = mVTest_4.getBackground();
        drawable.setLevel(5000);
    }
}
