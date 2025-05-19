package com.example.administrator.androidtest.demo.widgetDemo.LoopViewPagerDemo;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.TestActivityComponent;
import com.example.administrator.androidtest.widget.LoopViewPager.LoopViewPager;
import com.example.libcommon.Util.ToastUtil;

public class LoopViewPagerDemoComponent extends TestActivityComponent {

    public LoopViewPagerDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initLoopViewPager();
    }

    private void initLoopViewPager() {
        LoopViewPager loopViewPager = findViewById(R.id.loopViewpager);
        loopViewPager.setAdapter(new PagerAdapter() {
            private final int[] mColors = new int[]{
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
                tv.setOnClickListener(v -> ToastUtil.showToast("I am TextView " + position));
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
