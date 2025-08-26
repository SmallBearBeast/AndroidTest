package com.example.administrator.androidtest.demo.MediaDemo.PlayerDemo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bear.libcommon.util.FileUtil;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;

import java.io.File;

public class UniversalPlayer {

    private static final String TAG = "UniversalPlayer";
    private static final int MAX_CACHE_BYTE = 512 * 1024 * 1024;
    private static final int MIN_BUFFER_MS = 1_000; // 最小缓冲时间，
    private static final int MAX_BUFFER_MS = 1_000; // 最大缓冲时间
    private static final int PLAYBACK_BUFFER_MS = 700; // 最小播放缓冲时间，只有缓冲到达这个时间后才是可播放状态
    private static final int REBUFFER_MS = 700; // 当缓冲用完，再次缓冲的时间

    private boolean isCache = true;
    private final Context context;
    private ExoPlayer exoPlayer;
    private Cache cache;
    private HttpDataSource.Factory httpDataSourceFactory;
    private final LoadControl loadControl = new DefaultLoadControl.Builder()
            .setPrioritizeTimeOverSizeThresholds(true) //缓冲时时间优先级高于大小
            .setBufferDurationsMs(MIN_BUFFER_MS, MAX_BUFFER_MS, PLAYBACK_BUFFER_MS, REBUFFER_MS)
            .build();

    private final Player.Listener listener = new Player.Listener() {
        @Override
        public void onPlaybackStateChanged(int playbackState) {
            String stateStr = "UNKNOWN_STATE";
            switch (playbackState) {
                case ExoPlayer.STATE_IDLE:
                    stateStr = "ExoPlayer.STATE_IDLE";
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    stateStr = "ExoPlayer.STATE_BUFFERING";
                    break;
                case ExoPlayer.STATE_READY:
                    stateStr = "ExoPlayer.STATE_READY";
                    break;
                case ExoPlayer.STATE_ENDED:
                    stateStr = "ExoPlayer.STATE_ENDED";
                    break;
            }
            Log.d(TAG, "onPlaybackStateChanged: playbackState = " + playbackState + ", stateStr = " + stateStr);
        }

        @Override
        public void onIsPlayingChanged(boolean isPlaying) {
            Log.d(TAG, "onIsPlayingChanged() called with: isPlaying = [" + isPlaying + "]");
        }

        @Override
        public void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {
            Log.d(TAG, "onPlayWhenReadyChanged() called with: playWhenReady = [" + playWhenReady + "], reason = [" + reason + "]");
        }

        @Override
        public void onRenderedFirstFrame() {
            Log.d(TAG, "onRenderedFirstFrame() called");
        }

        @Override
        public void onPlayerError(PlaybackException error) {
            Log.d(TAG, "onPlayerError() called with: error = [" + error + "], errorCode = " + error.errorCode);
        }

        @Override
        public void onPlayerErrorChanged(@Nullable PlaybackException error) {
            Log.d(TAG, "onPlayerErrorChanged() called with: error = [" + error + "], errorCode = " + (error != null ? error.errorCode : -1));
        }
    };

    public UniversalPlayer(Context context) {
        this.context = context;
        initPlayer(context);
    }

    private void initPlayer(Context context) {
        DefaultRenderersFactory defaultRenderersFactory = new DefaultRenderersFactory(context).setEnableDecoderFallback(true);
        exoPlayer = new ExoPlayer.Builder(context, defaultRenderersFactory).setLoadControl(loadControl).build();
        exoPlayer.addListener(listener);
        exoPlayer.setRepeatMode(ExoPlayer.REPEAT_MODE_ONE);
    }

    public void attachPlayView(StyledPlayerView playerView) {
        playerView.setPlayer(exoPlayer);
    }

    private void setDataSource(String url) {
        if (exoPlayer == null) {
            return;
        }
        DataSource.Factory factory;
        if (isCache) {
            factory = getCacheDataSourceFactory();
        } else {
            factory = getDataSourceFactory();
        }
        // 构建 MediaSource
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(factory).createMediaSource(MediaItem.fromUri(url));
        // 设置给播放器
        exoPlayer.setMediaSource(mediaSource);
    }

    private DataSource.Factory getCacheDataSourceFactory() {
        if (cache == null) {
            // 构建缓存文件夹
            File cacheDir = new File(context.getCacheDir(), "exoplayer_cache");
            if (FileUtil.createDir(cacheDir)) {
                // 构建缓存实例
                cache = new SimpleCache(cacheDir, new LeastRecentlyUsedCacheEvictor(MAX_CACHE_BYTE), new StandaloneDatabaseProvider(context));
            }
        }
        // 构建 DataSourceFactory
        return new CacheDataSource.Factory()
                .setCache(cache)
                .setUpstreamDataSourceFactory(getDataSourceFactory())
                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);
    }

    private DataSource.Factory getDataSourceFactory() {
        return new DefaultDataSource.Factory(context, getHttpDataSourceFactory());
    }

    private DataSource.Factory getHttpDataSourceFactory() {
        if (httpDataSourceFactory == null) {
            httpDataSourceFactory = new DefaultHttpDataSource.Factory()
//                    .setUserAgent(mUserAgent)
                    .setAllowCrossProtocolRedirects(true);
        }
        return httpDataSourceFactory;
    }

    public void load(String url) {
        if (exoPlayer == null) {
            return;
        }
        exoPlayer.stop();
        setDataSource(url);
        exoPlayer.prepare();
    }

    public void play() {
        if (exoPlayer == null) {
            return;
        }
        if (exoPlayer.isPlaying()) {
            return;
        }
        if (exoPlayer.getPlaybackState() == Player.STATE_ENDED) {
            exoPlayer.seekTo(0);
        }
        exoPlayer.setPlayWhenReady(true);
    }

    public void pause() {
        if (exoPlayer == null) {
            return;
        }
        exoPlayer.setPlayWhenReady(false);
    }

    public void stop() {
        if (exoPlayer == null) {
            return;
        }
        exoPlayer.stop();
    }

    public void reset() {
        if (exoPlayer == null) {
            return;
        }
        exoPlayer.stop();
        exoPlayer.clearMediaItems();
        exoPlayer.setVideoSurface(null);
    }

    public void release() {
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer.removeListener(listener);
            exoPlayer = null;
        }
        if (cache != null) {
            cache.release();
            cache = null;
        }
    }

    public void toggle() {
        if (exoPlayer == null) {
            return;
        }
        if (exoPlayer.isPlaying()) {
            exoPlayer.pause();
        } else {
            exoPlayer.play();
        }
    }

    public boolean isPlaying() {
        if (exoPlayer == null) {
            return false;
        }
        return exoPlayer.isPlaying();
    }
}
