package com.example.administrator.androidtest.Test.ViewDragHelper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.androidtest.Common.Util.Core.ToastUtils;
import com.example.administrator.androidtest.R;

public class ViewDragHelperLayout extends LinearLayout implements View.OnClickListener {
    private static final String TAG = "ViewDragHelperLayout";
    private ViewDragHelper mViewDragHelper;
    private TextView mTv_1;
    private TextView mTv_2;
    private TextView mTv_3;

    public ViewDragHelperLayout(Context context) {
        this(context, null);
    }

    public ViewDragHelperLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                Log.i(TAG, "tryCaptureView(): pointerId = " + pointerId);
                return mTv_1 == child || mTv_3 == child;
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                Log.i(TAG, "clampViewPositionHorizontal: left = " + left + " dx = " + dx);
                return left;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                Log.i(TAG, "clampViewPositionVertical: top = " + top + " dy = " + dy);
                return top;
            }

            @Override
            public void onViewDragStateChanged(int state) {
                if (state == ViewDragHelper.STATE_IDLE) {
                    Log.i(TAG, "onViewDragStateChanged: state = STATE_IDLE");
                } else if (state == ViewDragHelper.STATE_DRAGGING) {
                    Log.i(TAG, "onViewDragStateChanged: state = STATE_DRAGGING");
                } else if (state == ViewDragHelper.STATE_SETTLING) {
                    Log.i(TAG, "onViewDragStateChanged: state = STATE_SETTLING");
                }
                super.onViewDragStateChanged(state);
            }

            @Override
            public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
                Log.i(TAG, "onViewPositionChanged: left = " + left + " top = " + top + " dx = " + dx + " dy = " + dy);
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }

            @Override
            public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
                Log.i(TAG, "onViewCaptured: activePointerId = " + activePointerId);
                super.onViewCaptured(capturedChild, activePointerId);
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                Log.i(TAG, "onViewReleased: xvel = " + xvel + " yvel = " + yvel);
                if (releasedChild == mTv_3) {
                    mViewDragHelper.flingCapturedView(0, 0, getWidth() - releasedChild.getWidth(), getHeight() - releasedChild.getHeight());
                    invalidate();
                }
            }

            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                Log.i(TAG, "onEdgeTouched: edgeFlags = " + edgeFlags + " pointerId = " + pointerId);
                super.onEdgeTouched(edgeFlags, pointerId);
            }

            @Override
            public boolean onEdgeLock(int edgeFlags) {
                Log.i(TAG, "onEdgeLock: edgeFlags = " + edgeFlags);
                return edgeFlags == ViewDragHelper.EDGE_RIGHT;
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                Log.i(TAG, "onEdgeDragStarted: edgeFlags = " + edgeFlags + " pointerId = " + pointerId);
                mViewDragHelper.captureChildView(mTv_2, pointerId);
            }

            @Override
            public int getOrderedChildIndex(int index) {
                return index;
            }

            @Override
            public int getViewHorizontalDragRange(@NonNull View child) {
                return 1;
            }

            @Override
            public int getViewVerticalDragRange(@NonNull View child) {
                return 1;
            }
        });
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTv_1 = (TextView) getChildAt(0);
        mTv_2 = (TextView) getChildAt(1);
        mTv_3 = (TextView) getChildAt(2);
        mTv_1.setOnClickListener(this);
        mTv_2.setOnClickListener(this);
        mTv_3.setOnClickListener(this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean result = mViewDragHelper.shouldInterceptTouchEvent(event);
        Log.i(TAG, "onInterceptTouchEvent: result = " + result + " event = " + event);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: event = " + event);
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_1:
                ToastUtils.showToast("I am tv_1");
                break;

            case R.id.tv_2:
                ToastUtils.showToast("I am tv_2");
                break;

            case R.id.tv_3:
                ToastUtils.showToast("I am tv_3");
                break;
        }
    }
}


