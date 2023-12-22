package com.example.administrator.androidtest.Test.MainTest.ViewDemo.ViewGetSizeTest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.libbase.Util.DensityUtil;

import java.util.Arrays;

public class ViewGetSizeTestAct extends ComponentAct {

    private TextView testTextView;

    @Override
    protected int layoutId() {
        return R.layout.act_view_get_width_and_height_test;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testTextView = findViewById(R.id.testTextView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: getMeasuredWidth = " + testTextView.getMeasuredWidth() + ", getWidth = " + testTextView.getWidth());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.i(TAG, "onWindowFocusChanged: hasFocus = " + hasFocus + ", getMeasuredWidth = " + testTextView.getMeasuredWidth() + ", getWidth = " + testTextView.getWidth());
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.testMeasureButton:
                onMeasureClick();
                break;

            case R.id.testOnPreDrawListenerButton:
                onOnPreDrawListenerClick();
                break;

            case R.id.testOnGlobalLayoutListenerButton:
                onGlobalLayoutListenerClick();
                break;

            case R.id.testOnLayoutChangeListenerButton:
                onLayoutChangeListenerClick();
                break;

            case R.id.testPostButton:
                onPostClick();
                break;

            case R.id.invalidateButton:
                onInvalidateClick();
                break;

            case R.id.requestLayoutButton:
                onRequestLayoutClick();
                break;

            case R.id.changeSizeButton:
                onChangeSizeClick();
                break;

            default:
                break;
        }
    }

    private void onMeasureClick() {
        int[] sizes = new int[2];
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        testTextView.measure(width, height);
        sizes[0] = testTextView.getMeasuredWidth();
        sizes[1] = testTextView.getMeasuredHeight();
        Log.i(TAG, "onMeasureClick: sizes = " + Arrays.toString(sizes));
    }

    private void onOnPreDrawListenerClick() {
        testTextView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                testTextView.getViewTreeObserver().removeOnPreDrawListener(this);
                Log.i(TAG, "onOnPreDrawListenerClick: getWidth = " + testTextView.getWidth() + ", getHeight = " + testTextView.getHeight());
                return true;
            }
        });
    }

    private void onGlobalLayoutListenerClick() {
        testTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                testTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Log.i(TAG, "onGlobalLayoutListenerClick: getWidth = " + testTextView.getWidth() + ", getHeight = " + testTextView.getHeight());
            }
        });
    }

    private void onLayoutChangeListenerClick() {
        testTextView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int l, int t, int r, int b,
                                       int oldL, int oldT, int oldR, int oldB) {
                testTextView.removeOnLayoutChangeListener(this);
                Log.i(TAG, "onLayoutChangeListenerClick: getWidth = " + testTextView.getWidth() + ", getHeight = " + testTextView.getHeight());
            }
        });
    }

    private void onPostClick() {
        testTextView.post(() -> Log.i(TAG, "onPostClick: getWidth = " + testTextView.getWidth() + ", getHeight = " + testTextView.getHeight()));
    }

    private void onInvalidateClick() {
        // 触发onDraw。
        testTextView.invalidate();
    }

    private void onRequestLayoutClick() {
        // 触发onMeasure, onLayout, onDraw。
        testTextView.requestLayout();
    }

    private void onChangeSizeClick() {
        ViewGroup.LayoutParams lp = testTextView.getLayoutParams();
        lp.width = DensityUtil.dp2Px(150F);
        testTextView.setLayoutParams(lp);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ViewGetSizeTestAct.class));
    }
}
