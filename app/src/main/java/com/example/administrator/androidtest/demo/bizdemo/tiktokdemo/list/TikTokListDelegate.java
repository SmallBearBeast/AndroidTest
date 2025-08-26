package com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.list;

import android.view.View;

import androidx.annotation.NonNull;

import com.bear.librv.MultiTypeDelegate;
import com.bear.librv.MultiTypeHolder;
import com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.TiktokItemInfo;

public class TikTokListDelegate extends MultiTypeDelegate<TiktokItemInfo, TikTokListDelegate.TikTokListHolder> {

    @NonNull
    @Override
    protected TikTokListHolder onCreateViewHolder(@NonNull View itemView) {
        return new TikTokListHolder(itemView);
    }

    @Override
    protected int layoutId() {
        return 0;
    }

    public static class TikTokListHolder extends MultiTypeHolder<TiktokItemInfo> {

        public TikTokListHolder(View itemView) {
            super(itemView);
        }
    }
}
