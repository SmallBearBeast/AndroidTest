package com.example.administrator.androidtest.Test.LoopViewPagerTest;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Widget.LoopViewPager.LoopViewPager;
import com.example.libbase.Util.ToastUtil;
import com.example.libframework.CoreUI.ComponentAct;

public class LoopViewPagerTestAct extends ComponentAct {
    @Override
    protected int layoutId() {
        return R.layout.act_loop_viewpager;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoopViewPager loopViewPager = findViewById(R.id.lvp_container);
        loopViewPager.setAdapter(new PagerAdapter() {
            private int[] mColors = new int[] {
                    Color.BLACK,
                    Color.RED,
                    Color.YELLOW,
                    Color.GRAY,
            };
            @Override
            public int getCount() {
                return mColors.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, final int position) {
                TextView tv = new TextView(container.getContext());
                ViewPager.LayoutParams lp = new ViewPager.LayoutParams();
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                tv.setLayoutParams(lp);
                tv.setBackgroundColor(mColors[position]);
                container.addView(tv);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showToast("I am TextView " + position);
                    }
                });
                return tv;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
        loopViewPager.startLoop();
    }
}
