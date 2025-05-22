package com.example.administrator.androidtest.demo.ViewDemo.CoordinatorLayoutTest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.example.administrator.androidtest.R;

public class Demo1Behavior extends CoordinatorLayout.Behavior<TextView> {
    private static final String TAG = "Demo1Behavior";
    private int textView2Right = Integer.MAX_VALUE;

    public Demo1Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull TextView child, @NonNull View dependency) {
        return dependency.getId() == R.id.textView2;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull TextView child, @NonNull View dependency) {
        if (textView2Right == Integer.MAX_VALUE) {
            textView2Right = dependency.getRight();
        }
        int offsetTopAndBottom = dependency.getTop() - child.getTop();
        int offsetLeftAndRight = textView2Right - dependency.getRight() - child.getLeft();
        ViewCompat.offsetTopAndBottom(child, offsetTopAndBottom);
        ViewCompat.offsetLeftAndRight(child, offsetLeftAndRight);
        return true;
    }
}
