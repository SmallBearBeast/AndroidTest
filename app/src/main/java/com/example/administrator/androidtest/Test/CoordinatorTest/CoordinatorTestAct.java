package com.example.administrator.androidtest.Test.CoordinatorTest;

import android.os.Bundle;
import android.util.Log;


import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.administrator.androidtest.R;
import com.example.libbase.Util.DensityUtil;
import com.example.libframework.CoreUI.ComponentAct;
import com.google.android.material.appbar.AppBarLayout;

public class CoordinatorTestAct extends ComponentAct {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = findViewById(R.id.tl_bar);
//        mToolbar.setAlpha(0);
//        final int height = DensityUtil.dp2Px(300);
//        AppBarLayout appBarLayout = findViewById(R.id.abl_content);
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                Log.d(TAG, "onOffsetChanged: verticalOffset  = " + verticalOffset + ", testHeight = " + height);
//                if (height != 0) {
//                    mToolbar.setAlpha(-verticalOffset * 1.0f / height);
//                }
//            }
//        });
    }

    @Override
    protected int layoutId() {
        return R.layout.act_coordinator_test;
    }
}
