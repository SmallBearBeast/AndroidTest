package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.Detail;

import android.view.View;
import android.widget.ImageButton;

import androidx.lifecycle.Lifecycle;

import com.bear.libcomponent.component.ActivityComponent;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.TiktokVideoInfo;
import com.example.administrator.androidtest.demo.MediaDemo.PlayerDemo.UniversalPlayer;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class VideoPlayComponent extends ActivityComponent implements View.OnClickListener {

    private UniversalPlayer universalPlayer;

    private StyledPlayerView videoPlayerView;
    private ImageButton playButton;

    public VideoPlayComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initPlayerView();
        initPlayer();
    }

    private void initPlayerView() {
        videoPlayerView = findViewById(R.id.videoPlayerView);
        videoPlayerView.setUseController(false);
        videoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(this);
        videoPlayerView.setOnClickListener(this);
    }

    private void initPlayer() {
        universalPlayer = new UniversalPlayer(getContext());
        universalPlayer.attachPlayView(videoPlayerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        universalPlayer.play();
    }

    @Override
    protected void onPause() {
        super.onPause();
        universalPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        universalPlayer.release();
        videoPlayerView = null;
        universalPlayer = null;
    }

    private void playVideo(TiktokVideoInfo tiktokVideoInfo) {
        universalPlayer.load(tiktokVideoInfo.videoDownloadUrl);
        universalPlayer.play();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.videoPlayerView:
            case R.id.playButton:
                if (universalPlayer.isPlaying()) {
                    universalPlayer.pause();
                    playButton.setVisibility(View.VISIBLE);
                } else {
                    universalPlayer.play();
                    playButton.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void seek() {

    }

    private void setSpeed() {

    }

    public void playExt(String url) {
        universalPlayer.load(url);
        universalPlayer.play();
    }

    public void loadExt(String url) {
        universalPlayer.load(url);
    }
}
