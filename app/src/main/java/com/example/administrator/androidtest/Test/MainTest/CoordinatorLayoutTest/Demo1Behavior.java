package com.example.administrator.androidtest.Test.MainTest.CoordinatorLayoutTest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class TestBehavior extends CoordinatorLayout.Behavior {
    private static final String TAG = "TestBehavior";

    public TestBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        Log.d(TAG, "onDependentViewChanged: child = " + child + ", dependency = " + dependency);
        return true;
    }

    @Override
    public void onDependentViewRemoved(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {

    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        Log.d(TAG, "layoutDependsOn: child = " + child + ", dependency = " + dependency);
        return true;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        Log.d(TAG, "onStartNestedScroll: child = " + child + ", directTargetChild = " + directTargetChild + ", target = " + target + ", axes = " + axes + ", type = " + type);
        return true;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        Log.d(TAG, "onNestedScroll: child = " + child + ", target = " + target + ", dxConsumed = " + dxConsumed + ", dyConsumed = " + dyConsumed + ", dxUnconsumed = " + dxUnconsumed + ", dyUnconsumed = " + dyUnconsumed + ", type = " + type);
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.d(TAG, "onNestedPreScroll: child = " + child + ", target = " + target + ", dx = " + dx + ", dy = " + dy + ", type = " + type);
    }


}
