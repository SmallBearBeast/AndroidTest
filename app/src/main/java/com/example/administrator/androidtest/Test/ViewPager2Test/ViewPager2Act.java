package com.example.administrator.androidtest.Test.ViewPager2Test;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.administrator.androidtest.R;
import com.example.libbase.Util.ResourceUtil;
import com.example.libframework.CoreUI.ComponentAct;

public class ViewPager2Act extends ComponentAct {
    private static final String TAG = "ViewPager2Act";
    private ViewPager2 mViewPager2;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = findViewById(R.id.vp_content_1);
        mViewPager2 = findViewById(R.id.vp_content_2);
        mViewPager.setAdapter(new ViewPager1Adapter());
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                mViewPager.setCurrentItem(1, true);
                mViewPager2.setCurrentItem(1, true);
                break;
        }
    }

    private static class ViewPager1Adapter extends PagerAdapter {
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

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            TextView tv = new TextView(container.getContext());
            tv.setText("Hello World");
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

    private static class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Holder> {

        @NonNull
        @Override
        public ViewPager2Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d(TAG, "onCreateViewHolder: viewType = " + viewType);
            if (viewType == 0) {
                TextView tv = new TextView(parent.getContext());
                tv.setText("Hello World");
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(16);
                tv.setTextColor(Color.WHITE);
                tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return new ViewPager2Holder(tv);
            } else {
                return new ViewPager2Holder(createNestView(parent.getContext()));
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewPager2Holder holder, int position) {
            if (getItemViewType(position) == 0) {
                holder.bindView(position);
            }
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == getItemCount() - 2) {
                return 1;
            }
            return 0;
        }

        private View createNestView(Context context) {
            ViewPager2 viewPager2 = new ViewPager2(context);
            viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
            viewPager2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            viewPager2.setAdapter(new RecyclerView.Adapter() {
                @NonNull
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    TextView tv = new TextView(parent.getContext());
                    tv.setText("Hello World");
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(16);
                    tv.setTextColor(Color.WHITE);
                    tv.setBackgroundColor(Color.BLACK);
                    tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    return new RecyclerView.ViewHolder(tv) {
                    };
                }

                @Override
                public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

                }

                @Override
                public int getItemCount() {
                    return 5;
                }
            });
            return viewPager2;
        }
    }

    private static class ViewPager2Holder extends RecyclerView.ViewHolder {

        int[] colors = new int[]{
                ResourceUtil.getColor(R.color.colorFF5722),
                ResourceUtil.getColor(R.color.color03A9F4),
                ResourceUtil.getColor(R.color.color9C27B0),
                ResourceUtil.getColor(R.color.color8BC34A),
                ResourceUtil.getColor(R.color.colorFF9800),
        };

        private ViewPager2Holder(@NonNull View itemView) {
            super(itemView);
        }

        private void bindView(int position) {
            itemView.setBackgroundColor(colors[position % colors.length]);
        }
    }
}
