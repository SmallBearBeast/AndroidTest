package com.example.administrator.androidtest.demo.MediaDemo.PlayerDemo;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.bear.libcomponent.component.ui.ActivityComponent;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.databinding.ActPlayerDemoBinding;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class PlayerComponent extends ActivityComponent<ActPlayerDemoBinding> implements View.OnClickListener {

    private UniversalPlayer universalPlayer;
    private StyledPlayerView styledPlayerView;

    public PlayerComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        styledPlayerView = getViewBinding().styledPlayerView;
        styledPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
        styledPlayerView.setControllerAutoShow(false);
//        styledPlayerView.setUseController(false);
//        styledPlayerView.setControllerHideOnTouch(true);
//        styledPlayerView.setDefaultArtwork(new ColorDrawable(Color.RED));
        getViewBinding().playBt.setOnClickListener(this);
        getViewBinding().pauseBt.setOnClickListener(this);
        getViewBinding().loadMp4.setOnClickListener(this);
        getViewBinding().loadMkv.setOnClickListener(this);
        getViewBinding().load3gp.setOnClickListener(this);
        getViewBinding().loadFlv.setOnClickListener(this);
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
