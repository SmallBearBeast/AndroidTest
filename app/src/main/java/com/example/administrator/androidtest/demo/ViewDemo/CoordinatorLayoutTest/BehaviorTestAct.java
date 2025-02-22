package com.example.administrator.androidtest.demo.ViewDemo.CoordinatorLayoutTest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.libbase.Util.ViewUtil;

public class BehaviorTestAct extends ComponentAct {

    private TextView textView1;
    private TextView textView2;
    private NestedScrollView nestedScrollView1;
    private NestedScrollView nestedScrollView2;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        // ScrollView默认嵌套滚动是关闭的
        // 使用ScrollView去替换时候需要加上nestedScrollView1.setNestedScrollingEnabled(true)
        nestedScrollView1 = findViewById(R.id.nestedScrollView1);
        nestedScrollView2 = findViewById(R.id.nestedScrollView2);
        textView2.setOnTouchListener(new FloatOnTouchListener());
    }

    @Override
    protected int layoutId() {
        return R.layout.act_behavior_test;
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.demo1Button:
                onDemo1Click();
                break;
            case R.id.demo2Button:
                onDemo2Click();
                break;
            case R.id.demo3Button:
                break;
            default:
                break;
        }
    }

    private void onDemo1Click() {
        ViewUtil.gone(nestedScrollView1, nestedScrollView2);
        ViewUtil.visible(textView1, textView2);
    }

    private void onDemo2Click() {
        ViewUtil.gone(textView1, textView2);
        ViewUtil.visible(nestedScrollView1, nestedScrollView2);
    }


    private static class FloatOnTouchListener implements View.OnTouchListener {

        private static final String TAG = "FloatOnTouchListener";
        private int x;
        private int y;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    Log.d(TAG, "onTouch: nowX = " + nowX + ", x = " + x + ", movedX = " + movedX);
                    ViewCompat.offsetLeftAndRight(view, movedX);
                    ViewCompat.offsetTopAndBottom(view, movedY);
                    break;
                case MotionEvent.ACTION_UP:
                    view.performClick();
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, BehaviorTestAct.class));
    }
}
