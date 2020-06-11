package com.example.administrator.androidtest.Test.WidgetTest;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.administrator.androidtest.R;
import com.example.libbase.Util.AnimatorConfig;
import com.example.libframework.CoreUI.ComponentAct;

public class LikeViewAct extends ComponentAct {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ImageView imageView = findViewById(R.id.iv_test);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatorConfig.make(imageView, "rotation", 300, new float[] {0, -30, 0}).start();
            }
        });

    }

    @Override
    protected int layoutId() {
        return R.layout.act_like_view_test;
    }
}
