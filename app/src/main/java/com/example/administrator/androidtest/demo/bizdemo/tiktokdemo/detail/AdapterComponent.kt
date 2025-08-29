package com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.detail

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bear.libcomponent.component.ui.ActivityComponent
import com.bear.librv.MultiItemChanger
import com.bear.librv.MultiTypeAdapter
import com.bear.librv.Payload
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
            Log.d(TAG, "onPageSelected: new position = $position, last position = $lastPosition")
            if (position == lastPosition) {
                return
            }

            if (lastPosition != -1) {
                if (position > lastPosition) {
                    loadNextVideos(position)
                } else {
                    loadPreviousVideos(position)
                }
            }

            val currentItems = changer.data
            if (lastPosition in currentItems.indices) {
                val item = currentItems[lastPosition] as? VideoDetailItem
                if (item?.isPlaying == true) {
                    item.isPlaying = false
                    changer.update(lastPosition, item, Payload(1))
                }
            }
            if (position in currentItems.indices) {
                val item = currentItems[position] as? VideoDetailItem
                if (item?.isPlaying == false) {
                    item.isPlaying = true
                    changer.update(position, item, Payload(1))
                }
            }

            lastPosition = position
        }
    }

    override fun onCreate() {
        super.onCreate()
        initViews()
        initData()
    }

    override fun onResume() {
        super.onResume()
        val curPosition = requireBinding().videoDetailViewPager.currentItem
        play(curPosition)
    }

    override fun onPause() {
        super.onPause()
        val curPosition = requireBinding().videoDetailViewPager.currentItem
        pause(curPosition)
    }

    private fun initViews() {
        typeAdapter = MultiTypeAdapter(lifecycle).apply {
            register(VideoDetailItem::class.java, videoDetailDelegate)
        }
        changer = typeAdapter.changer
        requireBinding().videoDetailViewPager.apply {
            setPageTransformer(null)
            offscreenPageLimit = 1
            adapter = typeAdapter
            registerOnPageChangeCallback(pageChangeCallback)
            (getChildAt(0) as? RecyclerView)?.let { recyclerView ->
                // 禁用RecyclerView的默认动画，减少闪烁
                recyclerView.itemAnimator = null
                // 设置RecyclerView缓存大小
                recyclerView.setItemViewCacheSize(2)
                // 启用预布局优化
                recyclerView.setHasFixedSize(true)
            }
        }
    }

    private fun initData() {
        val initialDetailInfos = TiktokVideoInfoLoader.getInitialDetailInfos()
        if (initialDetailInfos.isNotEmpty()) {
            val initialDetailItems = initialDetailInfos.map { VideoDetailItem(it, false) }
            val startIndex = requireActivity().intent.getIntExtra(TIKTOK_BUNDLE_KEY_CURRENT_INDEX, 0)
                .coerceIn(initialDetailInfos.indices)
            initialDetailItems[startIndex].isPlaying = true
            changer.setItems(initialDetailItems)
            requireBinding().videoDetailViewPager.setCurrentItem(startIndex, false)
            refreshVideoDetails(startIndex)
        } else {
            loadNextVideos(0)
        }
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
                val indexToUpdate = currentItems.indexOfFirst { (it as? VideoDetailItem)?.info?.id == detail.id }
                if (indexToUpdate != -1) {
                    val item = (currentItems[indexToUpdate] as? VideoDetailItem)?.copy(info = detail)
                    changer.update(indexToUpdate, item)
                }
            }
        }
    }

    private fun addNewItems(newItems: List<TiktokDetailInfo>?, addToEnd: Boolean) {
        if (newItems.isNullOrEmpty()) {
            return
        }
        val existingIds = changer.data.mapNotNull { (it as? VideoDetailItem)?.info?.id }.toSet()
        val uniqueNewItems = newItems.filter { it.id !in existingIds }
        if (uniqueNewItems.isNotEmpty()) {
            if (addToEnd) {
                changer.addLast(uniqueNewItems.map { VideoDetailItem(it, false) })
            } else {
                changer.addFirst(uniqueNewItems.map { VideoDetailItem(it, false) })
            }
        }
    }

    private fun play(position: Int) {
        videoDetailDelegate.getComponent(IVideoPlayComponent::class.java, position) {
            val url = TiktokVideoInfoLoader.getDetailInfo(position)?.videoDownloadUrl
            it.play(url)
        }
    }

    private fun pause(position: Int) {
        videoDetailDelegate.getComponent(IVideoPlayComponent::class.java, position) {
            it.pause()
        }
    }
}