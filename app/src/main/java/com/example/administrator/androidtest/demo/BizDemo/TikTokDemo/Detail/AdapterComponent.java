package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.Detail;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;

import com.bear.librv.MultiItemChanger;
import com.bear.librv.MultiTypeAdapter;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.TiktokVideoDetailInfo;
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.TiktokConstants;
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.TiktokDataLoader;
import com.example.administrator.androidtest.demo.TestActivityComponent;

import java.util.List;

public class AdapterComponent extends TestActivityComponent {

    private ViewPager2 videoDetailViewPager;
    private VideoDetailDelegate videoDetailDelegate;
    private MultiItemChanger changer;


    public AdapterComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        initViewpager2();
        initData();
    }

    private void initViewpager2() {
        videoDetailViewPager = findViewById(R.id.videoDetailViewPager);
        videoDetailViewPager.setOffscreenPageLimit(1);
        MultiTypeAdapter adapter = new MultiTypeAdapter(getLifecycle());
        changer = adapter.getChanger();
        videoDetailDelegate = new VideoDetailDelegate(this);
        adapter.register(TiktokVideoDetailInfo.class, videoDetailDelegate);
        videoDetailViewPager.setAdapter(adapter);

        videoDetailViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected() called with: position = [" + position + "]");
                if (position >= TiktokDataLoader.getInstance().getSourceTiktokDataList().size() - 1) {
                    TiktokDataLoader.getInstance().loadMoreVideoDetailInfoList(tiktokVideoDetailInfoList -> changer.addLast(tiktokVideoDetailInfoList));
                }
                showThumb(position, false);
                if (position > 0) {
                    showThumb(position - 1, true);
                }
                if (position < adapter.getItemCount() - 1) {
                    showThumb(position + 1, true);
                }
                play(TiktokDataLoader.getInstance().getSourceTiktokDataList().get(position).videoDownloadUrl);
            }
        });
    }

    private void initData() {
        List<TiktokVideoDetailInfo> videoDetailList = TiktokDataLoader.getInstance().getSourceTiktokVideoDetailList();
        changer.setItems(videoDetailList);
        Intent intent = getActivity().getIntent();
        int currentIndex = intent != null ? intent.getIntExtra(TiktokConstants.CURRENT_INDEX, 0) : 0;
        if (currentIndex >= 0 && currentIndex < videoDetailList.size()) {
            videoDetailViewPager.setCurrentItem(currentIndex, false);
            play(TiktokDataLoader.getInstance().getSourceTiktokDataList().get(currentIndex).videoDownloadUrl);
            TiktokDataLoader.getInstance().loadTiktokVideoDetailInfoByRange(currentIndex, tiktokVideoDetailInfoList -> changer.update(currentIndex, tiktokVideoDetailInfoList.get(0)));
        } else {
            TiktokDataLoader.getInstance().refreshVideoDetailInfoList(tiktokVideoDetailInfoList -> changer.setItems(tiktokVideoDetailInfoList));
        }
    }

    private void play(String url) {
        VideoPlayComponent videoPlayComponent = getComponent(VideoPlayComponent.class);
        if (videoPlayComponent != null) {
//            videoPlayComponent.playExt(url);
        }
    }

    private void showThumb(int position, boolean show) {
        int holdId = videoDetailDelegate.getHolderId(position);
        VideoInfoComponent videoInfoComponent = getComponent(VideoInfoComponent.class, holdId);
        if (videoInfoComponent != null) {
            Log.d(TAG, "position = " + position + ", show = " + show);
//            videoInfoComponent.showThumbExt(show);
        }
    }
}
