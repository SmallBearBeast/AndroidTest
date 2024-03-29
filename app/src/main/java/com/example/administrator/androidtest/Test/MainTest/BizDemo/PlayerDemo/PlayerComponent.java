package com.example.administrator.androidtest.Test.MainTest.BizDemo.PlayerDemo;

import com.bear.libcomponent.component.ActivityComponent;
import com.example.administrator.androidtest.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class PlayerComponent extends ActivityComponent {
    private ExoPlayer exoPlayer;

    private StyledPlayerView styledPlayerView;

    @Override
    protected void onCreate() {
        super.onCreate();
        initPlayer();
        styledPlayerView = findViewById(R.id.styledPlayerView);
        styledPlayerView.setPlayer(exoPlayer);
    }

    private void initPlayer() {
        exoPlayer = new ExoPlayer.Builder(getContext()).build();
        MediaItem mediaItem = MediaItem.fromUri("https://www.tootootool.com/wp-content/uploads/2020/11/big_buck_bunny_720p_1mb.mp4");
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.setPlayWhenReady(true);
    }

    private void start() {
        exoPlayer.play();
    }

    private void pause() {
        exoPlayer.pause();
    }
}
