package com.example.administrator.androidtest.Test.MainTest.ViewDemo.RefreshViewDemo;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.administrator.androidtest.R;

public class DefaultHeadView extends IView {
    private ImageView ivRotate;

    public DefaultHeadView(@NonNull Context context) {
        this(context, null);
    }

    public DefaultHeadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultHeadView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ivRotate = mContentView.findViewById(R.id.iv_rotate);
    }


    @Override
    public int height() {
        return dp2px(100);
    }

    @Override
    public void change(float progress) {
        super.change(progress);
        ivRotate.setRotation(progress * 360);
        ivRotate.setScaleX(progress / 4 + 1);
        ivRotate.setScaleY(progress / 4 + 1);
        ivRotate.setTranslationY(progress * dp2px(50));
        mPullLoadLayout.move(progress, VIEW_HEAD);
    }

    @Override
    public int layoutId() {
        return R.layout.widget_default_head_view;
    }
}
