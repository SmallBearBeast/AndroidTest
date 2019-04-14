package com.example.administrator.androidtest.Test.NestedScrolling;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.androidtest.R;

import java.util.Arrays;

public class StickyLayout extends LinearLayout{
    private static final String TAG = "StickyLayout";
    private int mIvHeight;
    private int mNsvHeignt;
    private ImageView mIv;
    private NestedScrollView mNsv;
    private StickyLayout mSl;
    public StickyLayout(Context context) {
        this(context, null);
    }

    public StickyLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mIv = findViewById(R.id.iv);
        mNsv = findViewById(R.id.nsv);
        mSl = findViewById(R.id.sticky_layout);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mIvHeight == 0){
            mIvHeight = mIv.getMeasuredHeight();
        }
        if(mNsvHeignt == 0){
            mNsvHeignt = mNsv.getMeasuredHeight();
            LinearLayout.LayoutParams lp = (LayoutParams) mNsv.getLayoutParams();
            lp.height = mNsvHeignt;
            mNsv.setLayoutParams(lp);
        }
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.d(TAG, "onStartNestedScroll: nestedScrollAxes = " + nestedScrollAxes);
        if(nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL){
            return true;
        }
        return false;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        Log.d(TAG, "onNestedScrollAccepted() called with: child = [" + child + "], target = [" + target + "], axes = [" + axes + "]");
        super.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(View child) {
        Log.d(TAG, "onStopNestedScroll() called with: child = [" + child + "]");
        super.onStopNestedScroll(child);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.d(TAG, "onNestedScroll() called with: target = [" + target + "], dxConsumed = [" + dxConsumed + "], dyConsumed = [" + dyConsumed + "], dxUnconsumed = [" + dxUnconsumed + "], dyUnconsumed = [" + dyUnconsumed + "]");
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.d(TAG, "onNestedPreScroll() called with: target = [" + target + "], dx = [" + dx + "], dy = [" + dy + "], consumed = [" + Arrays.toString(consumed) + "]");
        boolean hiddenTop = dy > 0 && getScrollY() < mIvHeight;
        boolean showTop = dy < 0 && getScrollY() >= 0 && !target.canScrollHorizontally(-1);

        if (hiddenTop || showTop) {
            scrollBy(0, dy);
            LinearLayout.LayoutParams lp = (LayoutParams) mNsv.getLayoutParams();
            lp.height = lp.height + dy;
            mNsv.setLayoutParams(lp);
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.d(TAG, "onNestedFling() called with: target = [" + target + "], velocityX = [" + velocityX + "], velocityY = [" + velocityY + "], consumed = [" + consumed + "]");
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.d(TAG, "onNestedPreFling() called with: target = [" + target + "], velocityX = [" + velocityX + "], velocityY = [" + velocityY + "]");
        return super.onNestedPreFling(target, velocityX, velocityY);
    }
}
