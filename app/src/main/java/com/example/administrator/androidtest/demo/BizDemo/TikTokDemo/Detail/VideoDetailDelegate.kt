package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.Detail

import android.view.View
import com.bear.librv.MultiTypeDelegate
import com.bear.librv.MultiTypeHolder
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.databinding.ItemTiktokVideoDetailBinding
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.Detail.VideoDetailDelegate.VideoDetailViewHolder
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.TiktokVideoDetailInfo
import java.util.concurrent.atomic.AtomicInteger

class VideoDetailDelegate(private val adapterComponent: AdapterComponent) :
    MultiTypeDelegate<TiktokVideoDetailInfo?, VideoDetailViewHolder?>() {

    private val viewHolderIdAtomic = AtomicInteger(0)

    private val holderIdAndPosMap: MutableMap<Int, Int> = HashMap()

    override fun onCreateViewHolder(itemView: View): VideoDetailViewHolder {
        return VideoDetailViewHolder(itemView, viewHolderIdAtomic.getAndIncrement())
    }

    override fun layoutId(): Int {
        return R.layout.item_tiktok_video_detail
    }

    inner class VideoDetailViewHolder(itemView: View, private val holderId: Int) : MultiTypeHolder<TiktokVideoDetailInfo?>(itemView) {

        init {
            val itemBinding = ItemTiktokVideoDetailBinding.bind(itemView)
            adapterComponent.regComponent(VideoPlayComponent(itemBinding.videoPlayLayout), holderId.toString())
            adapterComponent.regComponent(VideoInfoComponent(itemBinding.videoInfoLayout), holderId.toString())
            adapterComponent.regComponent(VideoActionComponent(itemBinding.videoActionLayout), holderId.toString())
        }

        override fun bindFull(pos: Int, item: TiktokVideoDetailInfo?) {
            super.bindFull(pos, item)
            holderIdAndPosMap[holderId] = pos
            adapterComponent.apply {
                getComponent(IVideoPlayComponent::class.java, holderId.toString())?.bindVideoDetailInfo(item)
                getComponent(IVideoActionComponent::class.java, holderId.toString())?.bindVideoDetailInfo(item)
                getComponent(IVideoInfoComponent::class.java, holderId.toString())?.bindVideoDetailInfo(item)
            }
        }
    }

    override fun isSupportLifecycle(): Boolean {
        return true
    }

    fun getHolderId(pos: Int): Int {
        for ((key, value) in holderIdAndPosMap) {
            if (value == pos) {
                return key
            }
        }
        return 0
    }
}
