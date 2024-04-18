package com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo.Detail;

import android.view.View;

import androidx.annotation.NonNull;

import com.bear.librv.MultiTypeDelegate;
import com.bear.librv.MultiTypeHolder;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo.TiktokVideoDetailInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class VideoDetailDelegate extends MultiTypeDelegate<TiktokVideoDetailInfo, VideoDetailDelegate.VideoDetailViewHolder> {
    private final AtomicInteger viewHolderIdAtomic = new AtomicInteger(0);
    private final Map<Integer, Integer> holderIdAndPosMap = new HashMap<>();

    private final AdapterComponent adapterComponent;

    public VideoDetailDelegate(AdapterComponent component) {
        adapterComponent = component;
    }

    @NonNull
    @Override
    protected VideoDetailViewHolder onCreateViewHolder(@NonNull View itemView) {
        return new VideoDetailViewHolder(itemView, viewHolderIdAtomic.getAndIncrement());
    }

    @Override
    protected int layoutId() {
        return R.layout.item_tiktok_video_detail;
    }

    public class VideoDetailViewHolder extends MultiTypeHolder<TiktokVideoDetailInfo> {
        private final int holderId;

        public VideoDetailViewHolder(View itemView, int id) {
            super(itemView);
            holderId = id;
        }

        @Override
        protected void onCreate() {
            super.onCreate();
//            adapterComponent.regComponent(new VideoPlayComponent(itemView));
            adapterComponent.regComponent(new VideoActionComponent(itemView), holderId);
            adapterComponent.regComponent(new VideoInfoComponent(itemView), holderId);
        }

        @Override
        protected void bindFull(int pos, TiktokVideoDetailInfo videoDetailInfo) {
            super.bindFull(pos, videoDetailInfo);
            holderIdAndPosMap.put(holderId, pos);
            VideoActionComponent videoActionComponent = adapterComponent.getComponent(VideoActionComponent.class, holderId);
            if (videoActionComponent != null) {
                videoActionComponent.bindVideoDetailInfoExt(videoDetailInfo);
            }
            VideoInfoComponent videoInfoComponent = adapterComponent.getComponent(VideoInfoComponent.class, holderId);
            if (videoInfoComponent != null) {
                videoInfoComponent.bindVideoDetailInfoExt(videoDetailInfo);
            }
        }
    }

    @Override
    protected boolean isSupportLifecycle() {
        return true;
    }

    public int getHolderId(int pos) {
        for (Map.Entry<Integer, Integer> entry : holderIdAndPosMap.entrySet()) {
            if (entry.getValue() == pos) {
                return entry.getKey();
            }
        }
        return 0;
    }
}
