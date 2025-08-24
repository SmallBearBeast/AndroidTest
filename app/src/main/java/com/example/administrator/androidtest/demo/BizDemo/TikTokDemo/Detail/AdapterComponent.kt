package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.Detail

import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bear.libcomponent.component.ui.ActivityComponent
import com.bear.librv.MultiItemChanger
import com.bear.librv.MultiTypeAdapter
import com.example.administrator.androidtest.databinding.ActTiktokVideoDetailBinding
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.TiktokConstants
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.TiktokDataLoader
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.TiktokVideoDetailInfo

class AdapterComponent : ActivityComponent<ActTiktokVideoDetailBinding>() {
    private var videoDetailViewPager: ViewPager2? = null

    private var videoDetailDelegate: VideoDetailDelegate? = null

    private var changer: MultiItemChanger? = null

    override fun onCreate() {
        super.onCreate()
        initViewpager2()
        initData()
    }

    private fun initViewpager2() {
        requireBinding().apply {
            videoDetailViewPager.offscreenPageLimit = 1
            val adapter = MultiTypeAdapter(lifecycle)
            changer = adapter.changer
            videoDetailDelegate = VideoDetailDelegate(this@AdapterComponent)
            adapter.register(TiktokVideoDetailInfo::class.java, videoDetailDelegate)
            videoDetailViewPager.adapter = adapter

            videoDetailViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    Log.d(TAG, "onPageSelected() called with: position = [$position]")
                    if (position >= TiktokDataLoader.getInstance().sourceTiktokDataList.size - 1) {
                        TiktokDataLoader.getInstance().loadMoreVideoDetailInfoList { tiktokVideoDetailInfoList: List<TiktokVideoDetailInfo?>? ->
                            changer?.addLast(tiktokVideoDetailInfoList)
                        }
                    }
                    showThumb(position, false)
                    if (position > 0) {
                        showThumb(position - 1, true)
                    }
                    if (position < adapter.itemCount - 1) {
                        showThumb(position + 1, true)
                    }
                    play(TiktokDataLoader.getInstance().sourceTiktokDataList[position].videoDownloadUrl)
                }
            })
        }

    }

    private fun initData() {
        val videoDetailList = TiktokDataLoader.getInstance().sourceTiktokVideoDetailList
        changer?.setItems(videoDetailList)
        val currentIndex = requireActivity().intent.getIntExtra(TiktokConstants.CURRENT_INDEX, 0)
        if (currentIndex >= 0 && currentIndex < videoDetailList.size) {
            videoDetailViewPager?.setCurrentItem(currentIndex, false)
            play(TiktokDataLoader.getInstance().sourceTiktokDataList[currentIndex].videoDownloadUrl)
            TiktokDataLoader.getInstance().loadTiktokVideoDetailInfoByRange(currentIndex) { tiktokVideoDetailInfoList: List<TiktokVideoDetailInfo?> ->
                changer?.update(currentIndex, tiktokVideoDetailInfoList[0])
            }
        } else {
            TiktokDataLoader.getInstance().refreshVideoDetailInfoList { infoList: List<TiktokVideoDetailInfo?>? ->
                changer?.setItems(infoList)
            }
        }
    }

    private fun play(url: String) {
        getComponent(IVideoPlayComponent::class.java)?.play(url)
    }

    private fun showThumb(position: Int, show: Boolean) {
        val holdId = videoDetailDelegate?.getHolderId(position) ?: return
        getComponent(IVideoPlayComponent::class.java, holdId.toString())?.showThumb(show)
    }
}