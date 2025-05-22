package com.example.administrator.androidtest.demo.ViewDemo.ViewPager2Test;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bear.libcommon.util.ResourceUtil;
import com.example.administrator.androidtest.R;

class ViewPager1Adapter extends PagerAdapter {
    int[] colors = new int[]{
            ResourceUtil.getColor(R.color.colorFF5722),
            ResourceUtil.getColor(R.color.color03A9F4),
            ResourceUtil.getColor(R.color.color9C27B0),
            ResourceUtil.getColor(R.color.color8BC34A),
            ResourceUtil.getColor(R.color.colorFF9800),
    };

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        TextView tv = new TextView(container.getContext());
        tv.setId(R.id.textview);
        tv.setText("ViewPager: position = " + position);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(16);
        tv.setTextColor(Color.WHITE);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        container.addView(tv);
        tv.setBackgroundColor(colors[position % colors.length]);
        return tv;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

