package com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.detail

import android.util.Log
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bear.libcomponent.component.ui.ActivityComponent
import com.bear.librv.MultiItemChanger
import com.bear.librv.MultiTypeAdapter
import com.example.administrator.androidtest.databinding.ActTiktokVideoDetailBinding
import com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.TIKTOK_BUNDLE_KEY_CURRENT_INDEX
import com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.TiktokDetailInfo
import com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.TiktokVideoInfoLoader

class AdapterComponent : ActivityComponent<ActTiktokVideoDetailBinding>() {
    private lateinit var typeAdapter: MultiTypeAdapter

    private lateinit var changer: MultiItemChanger

    private val videoDetailDelegate by lazy { VideoDetailDelegate(this) }

    private var lastPosition = -1

    private val pageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            if (position == lastPosition) {
                return
            }
            Log.d(TAG, "onPageSelected: new position = $position, last position = $lastPosition")

            if (position > lastPosition) {
                loadNextVideos(position)
            } else {
                loadPreviousVideos(position)
            }

            lastPosition = position
            updatePlaybackState(position)
        }
    }

    override fun onCreate() {
        super.onCreate()
        initViews()
        initData()
    }

    private fun initViews() {
        typeAdapter = MultiTypeAdapter(lifecycle).apply {
            register(TiktokDetailInfo::class.java, videoDetailDelegate)
        }
        changer = typeAdapter.changer
        requireBinding().videoDetailViewPager.apply {
            offscreenPageLimit = 1
            adapter = typeAdapter
            registerOnPageChangeCallback(pageChangeCallback)
        }
    }

    private fun initData() {
        val initialDetailInfos = TiktokVideoInfoLoader.getInitialDetailInfos()
        if (initialDetailInfos.isNotEmpty()) {
            changer.setItems(initialDetailInfos)
            // Consolidate logic for determining the starting index.
            val startIndex = requireActivity().intent.getIntExtra(TIKTOK_BUNDLE_KEY_CURRENT_INDEX, 0)
                .coerceIn(initialDetailInfos.indices) // Ensure index is within bounds.

            requireBinding().videoDetailViewPager.setCurrentItem(startIndex, false)
            // Manually trigger the first playback and data load since onPageSelected might not fire.
            updatePlaybackState(startIndex)
            refreshVideoDetails(startIndex)
        } else {
            // Load initial data if none exists.
            loadNextVideos(0)
        }
    }

    private fun updatePlaybackState(position: Int) {
        if (position > 0) {
            showThumb(position - 1, true)
        }
        if (position < typeAdapter.itemCount - 1) {
            showThumb(position + 1, true)
        }

        showThumb(position, false)
        play(position, TiktokVideoInfoLoader.getDetailInfo(position)?.videoDownloadUrl)
    }

    private fun loadNextVideos(position: Int) {
        TiktokVideoInfoLoader.loadNextRecommendDetailInfos(position) { newDetails ->
            addNewItems(newDetails, addToEnd = true)
        }
    }

    private fun loadPreviousVideos(position: Int) {
        TiktokVideoInfoLoader.loadPreRecommendDetailInfos(position) { newDetails ->
            addNewItems(newDetails?.reversed(), addToEnd = false)
        }
    }

    private fun refreshVideoDetails(centerIndex: Int) {
        TiktokVideoInfoLoader.loadDetailInfosByRange(centerIndex) { detailInfos ->
            if (detailInfos.isNullOrEmpty()) {
                return@loadDetailInfosByRange
            }

            val currentItems = changer.data
            detailInfos.forEach { detail ->
                val indexToUpdate = currentItems.indexOfFirst { (it as? TiktokDetailInfo)?.id == detail.id }
                if (indexToUpdate != -1) {
                    changer.update(indexToUpdate, detail)
                }
            }
        }
    }

    private fun addNewItems(newItems: List<TiktokDetailInfo>?, addToEnd: Boolean) {
        if (newItems.isNullOrEmpty()) {
            return
        }

        val existingIds = changer.data.mapNotNull { (it as? TiktokDetailInfo)?.id }.toSet()
        val uniqueNewItems = newItems.filter { it.id !in existingIds }

        if (uniqueNewItems.isNotEmpty()) {
            if (addToEnd) {
                changer.addLast(uniqueNewItems)
            } else {
                changer.addFirst(uniqueNewItems)
            }
        }
    }

    private fun play(position: Int, url: String?) {
        val holdId = videoDetailDelegate.getHolderId(position) ?: return
        getComponent(IVideoPlayComponent::class.java, holdId.toString()) {
            it.play(url)
        }
    }

    private fun showThumb(position: Int, show: Boolean) {
        val holdId = videoDetailDelegate.getHolderId(position) ?: return
        getComponent(IVideoPlayComponent::class.java, holdId.toString()) {
            it.showThumb(show)
        }
    }
}