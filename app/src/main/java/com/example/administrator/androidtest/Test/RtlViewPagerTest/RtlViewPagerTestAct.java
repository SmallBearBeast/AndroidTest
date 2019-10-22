package com.example.administrator.androidtest.Test.RtlViewPagerTest;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Widget.RtlViewPager.RtlViewPager;
import com.example.libframework.ActAndFrag.ComponentAct;

public class RtlViewPagerTestAct extends ComponentAct {
    @Override
    protected int layoutId() {
        return R.layout.act_rtl_viewpager;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        RtlViewPager rtlViewPager = findViewById(R.id.rvp_viewpager);
        rtlViewPager.setAdapter(new MyPagerAdapter(this));
    }

    class MyPagerAdapter extends PagerAdapter {

        private LayoutInflater mInflater;
        private int[] colors = new int[] {
                Color.RED, Color.GREEN, Color.BLUE, Color.BLACK,
        };

        private String[] strings = new String[] {
                "Color.RED", "Color.GREEN", "Color.BLUE", "Color.BLACK",
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
    }
}
