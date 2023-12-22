package com.example.administrator.androidtest.Test.MainTest.ViewDemo.ViewPager2Test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.libbase.Util.ToastUtil;

/**
 * ViewPager2 scroll is most fast than ViewPager.
 */
public class ViewPager2Act extends ComponentAct {
    private static final String TAG = "ViewPager2Act";
    private ViewPager2 mViewPager2;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewPager();
        initViewPager2();
    }

    private void initViewPager() {
        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(new ViewPager1Adapter());
    }

    private void initViewPager2() {
        mViewPager2 = findViewById(R.id.viewPager2);
        mViewPager2.setAdapter(new ViewPager2Adapter());
        mViewPager2.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        mViewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: position = " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.act_viewpager2_test;
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.resetButton:
                mViewPager.setCurrentItem(0, true);
                mViewPager2.setCurrentItem(0, true);
                break;

            case R.id.findViewButton:
                TextView textView = findViewById(R.id.textview);
                ToastUtil.showToast(textView != null ? String.valueOf(textView.getText()) : "textView is null");
                break;

            default:
                break;
        }
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ViewPager2Act.class));
    }
}
