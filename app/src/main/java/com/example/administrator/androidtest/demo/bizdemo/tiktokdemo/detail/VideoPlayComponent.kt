package com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.detail

import android.util.Log
import android.view.View
import com.bear.libcomponent.component.ui.ViewComponent
import com.bear.libcomponent.core.IComponent
import com.bumptech.glide.Glide
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.databinding.ComponentTiktokVideoPlayBinding
import com.example.administrator.androidtest.demo.MediaDemo.PlayerDemo.UniversalPlayer
import com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.TiktokDetailInfo
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout

class VideoPlayComponent(binding: ComponentTiktokVideoPlayBinding) : ViewComponent<ComponentTiktokVideoPlayBinding>(binding),
    View.OnClickListener, IVideoPlayComponent {
    private var lastDetailInfo: TiktokDetailInfo? = null

    private var universalPlayer: UniversalPlayer? = null

    override fun onCreate() {
        super.onCreate()
        initPlayerView()
        initPlayer()
    }

    private fun initPlayerView() {
        requireBinding().apply {
            videoPlayerView.useController = false
            videoPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            videoPlayerView.setOnClickListener(this@VideoPlayComponent)
            playButton.setOnClickListener(this@VideoPlayComponent)
        }
    }

    private fun initPlayer() {
        requireBinding().apply {
            universalPlayer = UniversalPlayer(requireContext())
            universalPlayer?.attachPlayView(videoPlayerView)
            universalPlayer?.setRepeatMode(Player.REPEAT_MODE_ONE)
            universalPlayer?.addListener(object : Player.Listener {
                override fun onRenderedFirstFrame() {
                    showThumb(false)
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    showPlayButton(isPlaying)
                    if (isPlaying) {
                        showThumb(false)
                    }
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        universalPlayer?.release()
        universalPlayer = null
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.videoPlayerView, R.id.playButton -> {
                requireBinding().apply {
                    if (universalPlayer?.isPlaying == true) {
                        universalPlayer?.pause()
                    } else {
                        universalPlayer?.play()
                    }
                }
            }
        }
    }

    override fun load(url: String?) {
        universalPlayer?.load(url)
    }

    override fun play(url: String?) {
        universalPlayer?.apply {
            // 播放状态不是IDLE且不是END表示以及load数据
            if (getPlayerState() != Player.STATE_IDLE && getPlayerState() != Player.STATE_ENDED) {
                play()
            } else {
                load(url, true)
            }
        }
    }

    override fun pause() {
        universalPlayer?.pause()
    }

    private fun showThumb(show: Boolean) {
        requireBinding().apply {
            if (show) {
                thumbIv.visibility = View.VISIBLE
            } else {
                thumbIv.visibility = View.GONE
            }
            Log.d(TAG, "showThumb: show = $show, key = ${key?.tag}")
        }
    }

    private fun showPlayButton(show: Boolean) {
        requireBinding().apply {
            if (show) {
                playButton.visibility = View.GONE
            } else {
                playButton.visibility = View.VISIBLE
            }
            Log.d(TAG, "showPlayButton: show = $show, key = ${key?.tag}")
        }
    }

    override fun bindVideoDetailInfo(videoDetailInfo: TiktokDetailInfo?, playing: Boolean) {
        requireBinding().apply {
            Glide.with(requireContext()).load(videoDetailInfo?.coverImgUrl).into(thumbIv)
            showThumb(universalPlayer?.isPlaying != true)
            if (lastDetailInfo?.id != videoDetailInfo?.id) {
                if (playing) {
                    play(videoDetailInfo?.videoDownloadUrl)
                } else {
                    load(videoDetailInfo?.videoDownloadUrl)
                }
                lastDetailInfo = videoDetailInfo
            }
        }
    }
}

interface IVideoPlayComponent : IComponent {
    fun bindVideoDetailInfo(videoDetailInfo: TiktokDetailInfo?, playing: Boolean)
    fun load(url: String?)
    fun play(url: String?)
    fun pause()
}