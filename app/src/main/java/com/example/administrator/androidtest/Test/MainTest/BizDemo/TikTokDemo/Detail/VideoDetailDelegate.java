package com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo.Detail;

import android.view.View;

import androidx.annotation.NonNull;

import com.bear.librv.MultiTypeDelegate;
import com.bear.librv.MultiTypeHolder;
import com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo.TiktokBean;

public class VideoDetailDelegate extends MultiTypeDelegate<TiktokBean, VideoDetailDelegate.VideoDetailHolder> {

    private final AdapterComponent adapterComponent;

    public VideoDetailDelegate(AdapterComponent component) {
        adapterComponent = component;
    }

    @NonNull
    @Override
    protected VideoDetailHolder onCreateViewHolder(@NonNull View itemView) {
        return new VideoDetailHolder(itemView);
    }

    @Override
    protected int layoutId() {
        return 0;
    }


    public class VideoDetailHolder extends MultiTypeHolder<TiktokBean> {
        public VideoDetailHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onCreate() {
            super.onCreate();
            adapterComponent.regComponent(new VideoPlayComponent(itemView));
            adapterComponent.regComponent(new ActionComponent(itemView));
        }
    }
}
