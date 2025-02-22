package com.example.administrator.androidtest.demo.ViewDemo.ViewGetSizeTest;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TestTestView extends androidx.appcompat.widget.AppCompatTextView {
    private static final String TAG = "TestTestView";
    private int mMeasureWidth;
    private int mMeasureHeight;
    private int mWidth;
    private int mHeight;

    public TestTestView(@NonNull Context context) {
        super(context);
    }

    public TestTestView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestTestView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure: getMeasuredWidth() = " + getMeasuredWidth() + ", getMeasuredHeight() = " + getMeasuredHeight());
        if (mMeasureWidth != getMeasuredWidth()) {
            mMeasureWidth = getMeasuredWidth();
        }
        if (mMeasureHeight != getMeasuredHeight()) {
            mMeasureHeight = getMeasuredHeight();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged: w = " + w + ", h = " + h + ", oldw = " + oldw + ", oldh = " + oldh);
        if (mWidth != w) {
            mWidth = w;
        }
        if (mHeight != h) {
            mHeight = h;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "onLayout: getWidth() = " + getWidth() + ", getHeight() = " + getHeight());
        if (mWidth != getWidth()) {
            mWidth = getWidth();
        }
        if (mHeight != getHeight()) {
            mHeight = getHeight();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: ");
    }
}
