package com.example.administrator.androidtest.Test.MainTest.ViewDemo.CoordinatorLayoutTest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;

import com.example.administrator.androidtest.R;

/**
 * Call sequence: onNestedPreScroll -> target.scroll -> onNestedScroll -> onNestedPreFling -> target.fling -> onNestedFling
 */
public class Demo2Behavior extends CoordinatorLayout.Behavior<NestedScrollView> {
    private static final String TAG = "Demo2Behavior";

    public Demo2Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull NestedScrollView child, @NonNull View dependency) {
        return dependency.getId() == R.id.nestedScrollView2;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull NestedScrollView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        // 垂直方向滑动
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull NestedScrollView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {

    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull NestedScrollView child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        // 组要判断type是TYPE_TOUCH，因为fling时候也会在走onNestedScroll方法。
        if (type == ViewCompat.TYPE_TOUCH) {
//            int scrollY = target.getScrollY();
//            child.setScrollY(scrollY);
            child.scrollBy(dxConsumed, dyConsumed);
        }
    }

    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull NestedScrollView child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return true;
    }

    /**
     * Return true will interrupt the event pass.
     */
    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull NestedScrollView child, @NonNull View target, float velocityX, float velocityY) {
        child.fling((int) velocityY);
        return false;
    }
}
