package com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo.List;

import android.view.View;

import androidx.annotation.NonNull;

import com.bear.librv.MultiTypeDelegate;
import com.bear.librv.MultiTypeHolder;
import com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo.TiktokBean;

public class TikTokListDelegate extends MultiTypeDelegate<TiktokBean, TikTokListDelegate.TikTokListHolder> {

    @NonNull
    @Override
    protected TikTokListHolder onCreateViewHolder(@NonNull View itemView) {
        return new TikTokListHolder(itemView);
    }

    @Override
    protected int layoutId() {
        return 0;
    }

    public static class TikTokListHolder extends MultiTypeHolder<TiktokBean> {

        public TikTokListHolder(View itemView) {
            super(itemView);
        }
    }
}
