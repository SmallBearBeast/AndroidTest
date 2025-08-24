package com.example.administrator.androidtest.other.ViewPagerTest;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bear.libcommon.util.CollectionUtil;
import com.bear.libcommon.util.ResourceUtil;
import com.bear.libcomponent.host.ComponentActivity;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.databinding.ActViewpagerTestBinding;

import java.util.List;

public class ViewPagerTestActivity extends ComponentActivity<ActViewpagerTestBinding> {
    private VerticalViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = findViewById(R.id.vp_test);
        mViewPager.addOnPageChangeListener(new VerticalViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled: positionOffset = " + positionOffset);
            }
        });
        mViewPager.setAdapter(new MyAdapter());
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                mViewPager.setAdapter(new MyAdapter());
                mViewPager.setPageTransformer(false, new ParallaxTransformer());
                break;

            case R.id.bt_2:
                mViewPager.setAdapter(new MyAdapter());
                mViewPager.setPageTransformer(false, new AlphaAndScalePageTransformer());
                break;

            case R.id.bt_3:
                mViewPager.setAdapter(new MyAdapter());
                mViewPager.setPageTransformer(false, new ZoomOutTransformer());
                break;

            case R.id.bt_4:
                mViewPager.setAdapter(new MyAdapter());
                mViewPager.setPageTransformer(false, new VerticalPageTransformer());
                break;

            case R.id.bt_5:
                mViewPager.setCurrentItem(10, true);
                break;
        }
    }

    @Override
    protected ActViewpagerTestBinding inflateViewBinding(@NonNull LayoutInflater inflater) {
        return ActViewpagerTestBinding.inflate(inflater);
    }

    private class MyAdapter extends VerticalPagerAdapter {

        private List<String> dataList = CollectionUtil.asListNotNull("AAAAAA", "BBBBBB", "CCCCCC", "DDDDDD", "AAAAAA", "BBBBBB", "CCCCCC", "DDDDDD", "AAAAAA", "BBBBBB", "CCCCCC", "DDDDDD");
        private List<Integer> colorList = CollectionUtil.asListNotNull(
                ResourceUtil.getColor(R.color.cl_black_t_5),
                ResourceUtil.getColor(R.color.cl_red_t_6),
                ResourceUtil.getColor(R.color.cl_blue_5),
                ResourceUtil.getColor(R.color.cl_green_9),
                ResourceUtil.getColor(R.color.cl_black_t_5),
                ResourceUtil.getColor(R.color.cl_red_t_6),
                ResourceUtil.getColor(R.color.cl_blue_5),
                ResourceUtil.getColor(R.color.cl_green_9),
                ResourceUtil.getColor(R.color.cl_black_t_5),
                ResourceUtil.getColor(R.color.cl_red_t_6),
                ResourceUtil.getColor(R.color.cl_blue_5),
                ResourceUtil.getColor(R.color.cl_green_9));

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Log.d(TAG, "instantiateItem: position = " + position);
            TextView tv = new TextView(container.getContext());
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.WHITE);
            tv.setBackgroundColor(colorList.get(position));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv.setText(dataList.get(position));
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            tv.setLayoutParams(lp);
            container.addView(tv);
            return tv;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public float getPageWidth(int position) {
            return 1f;
        }
    }
}
