package com.example.administrator.androidtest.Test.RtlViewPagerTest;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Widget.RtlViewPager.RtlViewPager;
import com.example.libbase.Util.DensityUtil;
import com.example.libframework.ActAndFrag.ComponentAct;
import com.example.libframework.Wrapper.OnPageChangeListenerWrapper;
import com.example.liblog.SLog;

/**
 * ViewPager如果设置padding的话会初始化比没有设置padding多一个。
 * 左往右先减少一个再增加一个，右往左先增加一个再减少一个。
 */
public class RtlViewPagerTestAct extends ComponentAct {
    @Override
    protected int layoutId() {
        return R.layout.act_rtl_viewpager;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        final ViewPager rtlViewPager = findViewById(R.id.rvp_viewpager);
        rtlViewPager.setAdapter(new MyPagerAdapter(this));
        rtlViewPager.addOnPageChangeListener(new OnPageChangeListenerWrapper() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                SLog.d(TAG, "onPageScrolled() called with: position = [" + position + "], positionOffset = [" + positionOffset + "], positionOffsetPixels = [" + positionOffsetPixels + "]");
            }
        });
        rtlViewPager.setPageMargin(DensityUtil.getScreenWidth());
        rtlViewPager.setPageMarginDrawable(R.drawable.girl);
        TextView tvText = findViewById(R.id.tv_text);
        rtlViewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                rtlViewPager.setCurrentItem(5, true);
            }
        }, 10 * 1000);
    }

    class MyPagerAdapter extends PagerAdapter {

        private LayoutInflater mInflater;
        private int[] colors = new int[] {
                Color.RED, Color.GREEN, Color.BLUE,
                Color.RED, Color.GREEN, Color.BLUE,
        };
        private String[] strings = new String[] {
                getString(R.string.new_enter_sms_code, "\u200e+86 130 0205 8534\u200e"),
                getString(R.string.back_confirm, "+86 130 0205 8534"),
                getString(R.string.back_confirm, "\u200e+86 130 0205 8534\u200e"),
                "גרסה \u200e+86 130 0205 8534\u200e זמינה",
                "abc@10.239.224.22",
                "Color.BLACK",
        };

        public MyPagerAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return colors.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            SLog.d(TAG, "instantiateItem() called with: container = [" + container + "], position = [" + position + "]");
            View view = mInflater.inflate(R.layout.item_tv_1, container, false);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                tv.setBackgroundColor(colors[position]);
                tv.setText(strings[position]);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public float getPageWidth(int position) {
            return 0.5f;
        }
    }
}
