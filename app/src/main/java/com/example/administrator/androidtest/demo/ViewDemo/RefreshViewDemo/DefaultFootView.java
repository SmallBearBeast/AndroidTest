package com.example.administrator.androidtest.demo.ViewDemo.RefreshViewDemo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.administrator.androidtest.R;

public class DefaultFootView extends IView {
    private ImageView ivRotate;
    public DefaultFootView(@NonNull Context context) {
        this(context, null);
    }

    public DefaultFootView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultFootView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        mPullLoadLayout.move(progress, VIEW_FOOT);
    }

    @Override
    public int layoutId() {
        return R.layout.widget_default_head_view;
    }
}
