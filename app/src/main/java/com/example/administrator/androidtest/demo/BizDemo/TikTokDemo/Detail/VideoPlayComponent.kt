package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.Detail

import android.util.Log
import android.view.View
import com.bear.libcomponent.component.ui.ViewComponent
import com.bear.libcomponent.core.IComponent
import com.bumptech.glide.Glide
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.databinding.ComponentTiktokVideoPlayBinding
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.TiktokVideoDetailInfo
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.TiktokVideoInfo
import com.example.administrator.androidtest.demo.MediaDemo.PlayerDemo.UniversalPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout

class VideoPlayComponent(binding: ComponentTiktokVideoPlayBinding) : ViewComponent<ComponentTiktokVideoPlayBinding>(binding), View.OnClickListener,
    IVideoPlayComponent {
    private var universalPlayer: UniversalPlayer? = null

    override fun onCreate() {
        super.onCreate()
        initPlayerView()
        initPlayer()
    }

    private fun initPlayerView() {
        requireBinding().apply {
            videoPlayerView.setUseController(false)
            videoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM)
            playButton.setOnClickListener(this@VideoPlayComponent)
            videoPlayerView.setOnClickListener(this@VideoPlayComponent)
        }
    }

    private fun initPlayer() {
        requireBinding().apply {
            universalPlayer?.attachPlayView(videoPlayerView)
        }
    }

    override fun onResume() {
        super.onResume()
        universalPlayer?.play()
    }

    override fun onPause() {
        super.onPause()
        universalPlayer?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        universalPlayer?.release()
        universalPlayer = null
    }

    private fun playVideo(tiktokVideoInfo: TiktokVideoInfo) {
        universalPlayer?.load(tiktokVideoInfo.videoDownloadUrl)
        universalPlayer?.play()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.videoPlayerView, R.id.playButton -> {
                requireBinding().apply {
                    if (universalPlayer?.isPlaying == true) {
                        universalPlayer?.pause()
                        playButton.setVisibility(View.VISIBLE)
                    } else {
                        universalPlayer?.play()
                        playButton.setVisibility(View.GONE)
                    }
                }
            }
        }
    }

    private fun seek() {

    }

    private fun setSpeed() {

    }

    override fun play(url: String) {
        universalPlayer?.load(url)
        universalPlayer?.play()
    }

    override fun load(url: String) {
        universalPlayer?.load(url)
    }

    override fun showThumb(show: Boolean) {
        requireBinding().apply {
            if (show) {
                thumbIv.visibility = View.VISIBLE
            } else {
                thumbIv.visibility = View.GONE
            }
            Log.d(TAG, "showThumb: show = $show, key = ${key?.tag}")
        }
    }

    override fun bindVideoDetailInfo(videoDetailInfo: TiktokVideoDetailInfo?) {
        requireBinding().apply {
            Glide.with(requireContext()).load(videoDetailInfo?.coverImgUrl).into(thumbIv)
        }
    }
}

interface IVideoPlayComponent : IComponent {
    fun bindVideoDetailInfo(videoDetailInfo: TiktokVideoDetailInfo?)
    fun play(url: String)
    fun load(url: String)
    fun showThumb(show: Boolean)
}
