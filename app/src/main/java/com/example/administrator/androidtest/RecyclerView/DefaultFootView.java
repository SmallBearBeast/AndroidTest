package com.example.administrator.androidtest.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.example.administrator.androidtest.R;

public class DefaultFootView extends IView {
    public DefaultFootView(@NonNull Context context) {
        super(context);
    }

    public DefaultFootView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DefaultFootView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int height() {
        return dp2px(100);
    }

    @Override
    public void change(float progress) {
        super.change(progress);
        mPullLoadLayout.move(progress, IView.VIEW_FOOT);
    }

    @Override
    public int layoutId() {
        return R.layout.widget_default_head_view;
    }
}
