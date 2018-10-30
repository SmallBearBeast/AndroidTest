package com.example.administrator.androidtest.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class DefaultHeadView extends PullLoadLayout.IView {
    private PullLoadLayout mPullLoadLayout;
    private int mType;
    public DefaultHeadView(@NonNull Context context) {
        super(context);
    }

    public DefaultHeadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DefaultHeadView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int height() {
        return super.height();
    }

    @Override
    public void change(float progress) {
        super.change(progress);
        mPullLoadLayout.move(progress, mType);
    }
}
