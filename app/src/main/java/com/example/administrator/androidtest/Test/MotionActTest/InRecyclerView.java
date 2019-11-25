package com.example.administrator.androidtest.Test.MotionActTest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.example.liblog.SLog;

public class InRecyclerView extends RecyclerView {
    private static final String TAG = "InRecyclerView";
    private int mTouchSlop;
    private float mInitDownX;
    private float mInitDownY;
    public InRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public InRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledPagingTouchSlop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInitDownX = e.getX();
                mInitDownY = e.getY();
                requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float xDiff = Math.abs(e.getX() - mInitDownX);
                float yDiff = Math.abs(e.getY() - mInitDownY);
                if (xDiff > mTouchSlop && xDiff * 0.5 > yDiff) {
                    requestDisallowInterceptTouchEvent(false);
                } else {
                    requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                requestDisallowInterceptTouchEvent(false);
                break;

            case MotionEvent.ACTION_CANCEL:
                SLog.d(TAG, "onTouchEvent() called with: ACTION_CANCEL");
                break;
        }
        return super.onTouchEvent(e);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow, int type) {
        SLog.d(TAG, "dispatchNestedScroll() called with: dxConsumed = [" + dxConsumed + "], dyConsumed = [" + dyConsumed + "], dxUnconsumed = [" + dxUnconsumed + "], dyUnconsumed = [" + dyUnconsumed + "], offsetInWindow = [" + offsetInWindow + "], type = [" + type + "]");
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow, int type) {
        SLog.d(TAG, "dispatchNestedPreScroll() called with: dx = [" + dx + "], dy = [" + dy + "], consumed = [" + consumed + "], offsetInWindow = [" + offsetInWindow + "], type = [" + type + "]");
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }

    @Override
    public void stopNestedScroll() {
        SLog.d(TAG, "stopNestedScroll() called");
        super.stopNestedScroll();
    }

    @Override
    public void stopNestedScroll(int type) {
        SLog.d(TAG, "stopNestedScroll() called with: type = [" + type + "]");
        super.stopNestedScroll(type);
    }
}
