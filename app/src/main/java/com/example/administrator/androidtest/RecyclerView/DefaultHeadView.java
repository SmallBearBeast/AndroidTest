package com.example.administrator.androidtest.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.administrator.androidtest.R;

public class DefaultHeadView extends IView {
    private ImageView ivRotate;
    public DefaultHeadView(@NonNull Context context) {
        super(context);
    }

    public DefaultHeadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
        ivRotate.setRotation(progress * 360 * 2);
        mPullLoadLayout.move(progress, IView.VIEW_HEAD);
    }

    @Override
    public int layoutId() {
        return R.layout.widget_default_head_view;
    }
}
