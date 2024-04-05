package com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo.List;

import android.view.View;

import androidx.annotation.NonNull;

import com.bear.librv.VHBridge;
import com.bear.librv.VHolder;

public class TikTokListBridge extends VHBridge<TikTokListBridge.TikTokListViewHolder> {

    @NonNull
    @Override
    protected TikTokListViewHolder onCreateViewHolder(@NonNull View itemView) {
        return new TikTokListViewHolder(itemView);
    }

    @Override
    protected int layoutId() {
        return 0;
    }

    public static class TikTokListViewHolder extends VHolder {

        public TikTokListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
