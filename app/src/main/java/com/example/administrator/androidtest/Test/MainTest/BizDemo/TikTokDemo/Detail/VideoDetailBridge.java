package com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo.Detail;

import android.view.View;

import androidx.annotation.NonNull;

import com.bear.librv.VHBridge;
import com.bear.librv.VHolder;

public class VideoDetailBridge extends VHBridge<VideoDetailBridge.VideoDetailViewHolder> {

    private AdapterComponent adapterComponent;

    public VideoDetailBridge(AdapterComponent component) {
        adapterComponent = component;
    }

    @NonNull
    @Override
    protected VideoDetailViewHolder onCreateViewHolder(@NonNull View itemView) {
        return new VideoDetailViewHolder(itemView);
    }

    @Override
    protected int layoutId() {
        return 0;
    }



    public class VideoDetailViewHolder extends VHolder {
        public VideoDetailViewHolder(View itemView) {
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
