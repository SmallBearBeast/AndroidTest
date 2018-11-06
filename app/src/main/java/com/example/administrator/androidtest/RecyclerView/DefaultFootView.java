package com.example.administrator.androidtest.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

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
        mPullLoadLayout.move(progress, IView.VIEW_FOOT);
    }

    @Override
    public int layoutId() {
        return R.layout.widget_default_head_view;
    }
}
