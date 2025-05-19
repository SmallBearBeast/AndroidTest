package com.example.administrator.androidtest.demo.ViewDemo.ViewPager2Test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.administrator.androidtest.R;
import com.bear.libcommon.util.ResourceUtil;

class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.ViewPager2Holder> {
    private static final String TAG = "ViewPager2Adapter";

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public ViewPager2Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: viewType = " + viewType);
        if (viewType == 0) {
            TextView tv = new TextView(parent.getContext());
            tv.setText("ViewPager2 Vertical");
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
        viewPager2.setAdapter(new RecyclerView.Adapter<ViewPager2Holder>() {
            @SuppressLint("SetTextI18n")
            @NonNull
            @Override
            public ViewPager2Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                TextView tv = new TextView(parent.getContext());
                tv.setText("ViewPager2 Horizontal");
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(16);
                tv.setTextColor(Color.WHITE);
                tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return new ViewPager2Holder(tv);
            }

            @Override
            public void onBindViewHolder(@NonNull ViewPager2Holder holder, int position) {
                holder.bindView(position);
            }

            @Override
            public int getItemCount() {
                return 5;
            }
        });
        return viewPager2;
    }

    static class ViewPager2Holder extends RecyclerView.ViewHolder {

        int[] colors = new int[]{
                ResourceUtil.getColor(R.color.colorFF5722),
                ResourceUtil.getColor(R.color.color03A9F4),
                ResourceUtil.getColor(R.color.color9C27B0),
                ResourceUtil.getColor(R.color.color8BC34A),
                ResourceUtil.getColor(R.color.colorFF9800),
        };

        ViewPager2Holder(@NonNull View itemView) {
            super(itemView);
        }

        private void bindView(int position) {
            itemView.setBackgroundColor(colors[position % colors.length]);
        }
    }
}
