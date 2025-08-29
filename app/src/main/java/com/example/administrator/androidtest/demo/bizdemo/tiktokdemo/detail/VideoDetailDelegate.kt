package com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.detail

import android.view.View
import com.bear.libcomponent.core.IComponent
import com.bear.librv.MultiTypeDelegate
import com.bear.librv.MultiTypeHolder
import com.bear.librv.Payload
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.databinding.ItemTiktokVideoDetailBinding
import com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.TiktokDetailInfo
import com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.detail.VideoDetailDelegate.VideoDetailViewHolder
import java.util.concurrent.atomic.AtomicInteger

class VideoDetailDelegate(private val adapterComponent: AdapterComponent) :
    MultiTypeDelegate<VideoDetailItem, VideoDetailViewHolder>() {

    private val viewHolderIdAtomic = AtomicInteger(0)

    private val holderIdAndPosMap: MutableMap<Int, Int> = HashMap()

    override fun onCreateViewHolder(itemView: View): VideoDetailViewHolder {
        return VideoDetailViewHolder(itemView, viewHolderIdAtomic.getAndIncrement())
    }

    override fun layoutId(): Int {
        return R.layout.item_tiktok_video_detail
    }

    inner class VideoDetailViewHolder(itemView: View, private val holderId: Int) : MultiTypeHolder<VideoDetailItem>(itemView) {

        init {
            val itemBinding = ItemTiktokVideoDetailBinding.bind(itemView)
            adapterComponent.regComponent(VideoPlayComponent(itemBinding.videoPlayLayout), holderId.toString())
            adapterComponent.regComponent(VideoInfoComponent(itemBinding.videoInfoLayout), holderId.toString())
            adapterComponent.regComponent(VideoActionComponent(itemBinding.videoActionLayout), holderId.toString())
        }

        override fun bindPartial(pos: Int, data: VideoDetailItem, payload: Payload) {
            super.bindPartial(pos, data, payload)
            if (payload.mType == 1) {
                getComponent(IVideoPlayComponent::class.java, pos) {
                    if (item.isPlaying) {
                        it.play(data.info.videoDownloadUrl)
                    } else {
                        it.pause()
                    }
                }
            }
        }

        override fun bindFull(pos: Int, item: VideoDetailItem) {
            super.bindFull(pos, item)
            holderIdAndPosMap[holderId] = pos
            adapterComponent.apply {
                getComponent(IVideoPlayComponent::class.java, holderId.toString()) {
                    it.bindVideoDetailInfo(item.info, item.isPlaying)
                }
                getComponent(IVideoActionComponent::class.java, holderId.toString()) {
                    it.bindVideoDetailInfo(item.info)
                }
                getComponent(IVideoInfoComponent::class.java, holderId.toString()) {
                    it.bindVideoDetailInfo(item.info)
                }
            }
        }
    }

    override fun isSupportLifecycle(): Boolean {
        return true
    }

    fun <C : IComponent> getComponent(clz: Class<C>, position: Int, onComponentReady: (component: C) -> Unit) {
        val holdId = getHolderId(position)
        if (holdId != -1) {
            adapterComponent.getComponent(clz, holdId.toString()) {
                onComponentReady(it)
            }
        }
    }

    private fun getHolderId(pos: Int): Int {
        for ((key, value) in holderIdAndPosMap) {
            if (value == pos) {
                return key
            }
        }
        return -1
    }
}

data class VideoDetailItem(
    val info: TiktokDetailInfo,
    var isPlaying: Boolean = false,
)