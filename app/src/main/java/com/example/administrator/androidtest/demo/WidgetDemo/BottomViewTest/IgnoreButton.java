package com.example.administrator.androidtest.demo.WidgetDemo.BottomViewTest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

public class IgnoreButton extends Button {
    public IgnoreButton(Context context) {
        super(context);
    }

    public IgnoreButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int lastY;
    private int nowY;

    private int[] consumed = new int[2];
    private int[] offsetInWindow = new int[2];
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        lastY = nowY;
        nowY = (int) event.getRawY();
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            int diff = nowY - lastY;
            dispatchNestedPreScroll(0, diff, consumed, offsetInWindow);
            return false;
        }
        return super.onTouchEvent(event);
    }
}
