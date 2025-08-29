package com.example.administrator.androidtest.demo.MediaDemo.PlayerDemo

import android.content.Context
import android.util.Log
import com.bear.libcommon.util.FileUtil
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File

class UniversalPlayer(private val context: Context) {
    private var exoPlayer: ExoPlayer? = null

    val isPlaying: Boolean
        get() = exoPlayer?.isPlaying == true

    private lateinit var dataSourceFactory: DataSource.Factory

    private val playLogListener: Player.Listener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            Log.d(TAG, "onPlaybackStateChanged: playbackState = $playbackState, stateStr = ${getPlayerStateString(playbackState)}")
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            Log.d(TAG, "onIsPlayingChanged: isPlaying = $isPlaying")
        }

        override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
            Log.d(TAG, "onPlayWhenReadyChanged: playWhenReady = $playWhenReady, reason = $reason")
        }

        override fun onRenderedFirstFrame() {
            Log.d(TAG, "onRenderedFirstFrame called")
        }

        override fun onPlayerError(error: PlaybackException) {
            Log.d(TAG, "onPlayerError: error = $error, errorCode = ${error.errorCode}")
        }

        override fun onPlayerErrorChanged(error: PlaybackException?) {
            Log.d(TAG, "onPlayerErrorChanged: error = $error, errorCode = ${error?.errorCode ?: -1}")
        }

        override fun onIsLoadingChanged(isLoading: Boolean) {
            Log.d(TAG, "onIsLoadingChanged: isLoading = $isLoading")
        }
    }

    init {
        initPlayer(context)
        initDataSourceFactory()
    }

    private fun initPlayer(context: Context) {
        val defaultRenderersFactory = DefaultRenderersFactory(context).setEnableDecoderFallback(true)
        val loadControl = DefaultLoadControl.Builder()
            .setPrioritizeTimeOverSizeThresholds(true) //缓冲时时间优先级高于大小
//        .setBufferDurationsMs(MIN_BUFFER_MS, MAX_BUFFER_MS, PLAYBACK_BUFFER_MS, REBUFFER_MS) // 一般不需要去设置，这里用于展示参数效果才设置
            .build()
        exoPlayer = ExoPlayer.Builder(context, defaultRenderersFactory).setLoadControl(loadControl).build().apply {
            repeatMode = ExoPlayer.REPEAT_MODE_OFF
            addListener(playLogListener)
        }
    }

    private fun initDataSourceFactory() {
        val cache = getCache(context)
        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
            // .setUserAgent(mUserAgent)
            .setAllowCrossProtocolRedirects(true)
        dataSourceFactory = if (cache != null) {
            CacheDataSource.Factory()
                .setCache(cache)
                .setUpstreamDataSourceFactory(httpDataSourceFactory)
                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
        } else {
            DefaultDataSource.Factory(context, httpDataSourceFactory)
        }
    }

    fun attachPlayView(playerView: StyledPlayerView) {
        playerView.player = exoPlayer
    }

    @JvmOverloads
    fun load(url: String?, autoPlay: Boolean = false) {
        if (url.isNullOrEmpty()) {
            return
        }
        exoPlayer?.apply {
            setDataSource(url, autoPlay)
            prepare()
        }
    }

    private fun setDataSource(url: String, autoPlay: Boolean = false) {
        exoPlayer?.apply {
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(url))
            setMediaSource(mediaSource)
            playWhenReady = autoPlay
        }
    }

    fun toggle() {
        exoPlayer?.apply {
            if (isPlaying) {
                pause()
            } else {
                play()
            }
        }
    }

    fun play() {
        exoPlayer?.apply {
            if (!isPlaying) {
                playWhenReady = true
            }
        }
    }

    fun pause() {
        exoPlayer?.apply {
            if (isPlaying) {
                playWhenReady = false
            }
        }
    }

    fun stop() {
        exoPlayer?.stop()
    }

    fun release() {
        exoPlayer?.apply {
            release()
            removeListener(playLogListener)
        }
        exoPlayer = null
    }

    fun addListener(listener: Player.Listener) {
        exoPlayer?.addListener(listener)
    }

    fun seekTo(positionMs: Long) {
        exoPlayer?.seekTo(positionMs)
    }

    fun seekToPercentage(percentage: Float) {
        val duration = getDuration()
        if (duration > 0) {
            val position = (duration * percentage.coerceIn(0f, 1f)).toLong()
            seekTo(position)
        }
    }

    fun setPlaybackSpeed(speed: Float) {
        exoPlayer?.playbackParameters = PlaybackParameters(speed)
    }

    fun setVolume(volume: Float) {
        exoPlayer?.volume = volume.coerceIn(0f, 1f)
    }

    fun setMute(mute: Boolean) {
        exoPlayer?.volume = if (mute) 0f else 1f
    }

    fun setRepeatMode(@Player.RepeatMode repeatMode: Int) {
        exoPlayer?.repeatMode = repeatMode
    }

    fun getDuration() = exoPlayer?.duration ?: 0L

    fun getCurrentPosition() = exoPlayer?.currentPosition ?: 0L

    fun getPlaybackProgress(): Float {
        val duration = getDuration()
        return if (duration > 0) {
            getCurrentPosition().toFloat() / duration
        } else 0F
    }

    fun getPlayerState() = exoPlayer?.playbackState ?: Player.STATE_IDLE

    fun getPlayerStateString(playbackState: Int? = null): String {
        val state = playbackState ?: getPlayerState()
        return when (state) {
            Player.STATE_IDLE -> "IDLE"
            Player.STATE_BUFFERING -> "BUFFERING"
            Player.STATE_READY -> "READY"
            Player.STATE_ENDED -> "ENDED"
            else -> "UNKNOWN"
        }
    }

    companion object {
        private const val TAG = "UniversalPlayer"
        private const val MAX_CACHE_BYTE = 512 * 1024 * 1024
        private const val MIN_BUFFER_MS = 1000 // 最小缓冲时间，
        private const val MAX_BUFFER_MS = 1000 // 最大缓冲时间
        private const val PLAYBACK_BUFFER_MS = 700 // 最小播放缓冲时间，只有缓冲到达这个时间后才是可播放状态
        private const val REBUFFER_MS = 700 // 当缓冲用完，再次缓冲的时间

        private var cache: Cache? = null
        private fun getCache(context: Context): Cache? {
            if (cache == null) {
                val cacheDir = File(context.cacheDir, "exoplayer_cache")
                if (FileUtil.createDir(cacheDir)) {
                    // 构建缓存实例
                    cache = SimpleCache(cacheDir, LeastRecentlyUsedCacheEvictor(MAX_CACHE_BYTE.toLong()), StandaloneDatabaseProvider(context))
                }
            }
            return cache
        }
    }
}
