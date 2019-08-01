package com.example.administrator.androidtest.Test.ProgressBarTest;

import android.os.Bundle;
import android.widget.ProgressBar;
import com.example.administrator.androidtest.R;
import com.example.libframework.ActAndFrag.ComponentAct;

public class ProgressBarTestAct extends ComponentAct {
    private ProgressBar mProgressBar;

    @Override
    protected int layoutId() {
        return R.layout.act_progressbar;
    }

    // TODO: 2019/4/23 progressbar 
    // TODO: 2019/4/23 pageradapter回调函数解析
    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
//        mProgressBar = findViewById(R.id.progress_bar);
//        View view = findViewById(R.id.v_rotate);
//        Animation animation = view.getAnimation();
//        if(view.getBackground() instanceof RotateDrawable){
//            RotateDrawable rotateDrawable = (RotateDrawable) view.getBackground();
//            rotateDrawable.setLevel(2500);
//        }

//        mProgressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
//        mProgressBar.setSecondaryProgressTintList(ColorStateList.valueOf(Color.BLACK));
//        mProgressBar.setProgressBackgroundTintList(ColorStateList.valueOf(Color.BLUE));
//        mProgressBar.setIndeterminateTintList(ColorStateList.valueOf(Color.BLUE));

//        Drawable drawable = mProgressBar.getIndeterminateDrawable().mutate();
//        if(drawable instanceof LayerDrawable){
//            LayerDrawable layerDrawable = (LayerDrawable) drawable;
//            Drawable progressDrawable = layerDrawable.findDrawableByLayerId(android.R.id.progress);
//            if(progressDrawable != null){
//                progressDrawable.setColorFilter(Color.BLACK, android.graphics.PorterDuff.Mode.SRC_IN);
//            }
//            Drawable secondProgressDrawable = layerDrawable.findDrawableByLayerId(android.R.id.secondaryProgress);
//            if(secondProgressDrawable != null){
//                secondProgressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
//            }
//        }else if(drawable instanceof AnimationScaleListDrawable){
//            AnimationScaleListDrawable animationScaleListDrawable = (AnimationScaleListDrawable) drawable;
//        }
    }
}
