package com.example.administrator.androidtest.Test.MainTest.MediaDemo.PlayerDemo;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class PlayerComponent extends TestActivityComponent {

    private UniversalPlayer universalPlayer;
    private StyledPlayerView styledPlayerView;

    public PlayerComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        styledPlayerView = findViewById(R.id.styledPlayerView);
        setOnClickListener(this, R.id.playBt, R.id.pauseBt, R.id.loadMp4, R.id.loadMkv, R.id.load3gp, R.id.loadFlv);
        initPlayer();
    }

    private void initPlayer() {
        universalPlayer = new UniversalPlayer(getContext());
        universalPlayer.attachPlayView(styledPlayerView);
        universalPlayer.load("https://www.tootootool.com/wp-content/uploads/2020/11/SampleVideo_1280x720_5mb.mp4");
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
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playBt:
                universalPlayer.play();
                break;
            case R.id.pauseBt:
                universalPlayer.pause();
                break;
            case R.id.loadMp4:
                universalPlayer.load("https://www.tootootool.com/wp-content/uploads/2020/11/SampleVideo_1280x720_5mb.mp4");
                break;
            case R.id.loadMkv:
                universalPlayer.load("https://www.tootootool.com/wp-content/uploads/2020/11/SampleVideo_1280x720_5mb.mkv");
                break;
            case R.id.load3gp:
                universalPlayer.load("https://www.tootootool.com/wp-content/uploads/2020/11/SampleVideo_176x144_5mb.3gp");
                break;
            case R.id.loadFlv:
                universalPlayer.load("https://www.tootootool.com/wp-content/uploads/2020/11/SampleVideo_1280x720_5mb.flv");
                break;
        }
    }
}
