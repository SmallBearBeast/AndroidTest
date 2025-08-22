package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.Detail

import android.view.View
import com.bear.librv.MultiTypeDelegate
import com.bear.librv.MultiTypeHolder
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.Detail.VideoDetailDelegate.VideoDetailViewHolder
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.TiktokVideoDetailInfo
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.bizcompoent.BizComponentActivity
import java.util.concurrent.atomic.AtomicInteger

class VideoDetailDelegate(private val componentActivity: BizComponentActivity<*>) :
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
            val videoPlayLayout = itemView.findViewWithTag<View>("videoPlayLayout")
            videoPlayLayout.id = View.generateViewId()
            val videoInfoLayout = itemView.findViewWithTag<View>("videoInfoLayout")
            videoInfoLayout.id = View.generateViewId()
            val videoActionLayout = itemView.findViewWithTag<View>("videoActionLayout")
            videoActionLayout.id = View.generateViewId()
            componentActivity.addComponent(VideoPlayComponent(), videoPlayLayout.id, holderId.toString())
            componentActivity.addComponent(VideoActionComponent(), videoActionLayout.id, holderId.toString())
            componentActivity.addComponent(VideoInfoComponent(), videoInfoLayout.id, holderId.toString())
        }

        override fun bindFull(pos: Int, item: TiktokVideoDetailInfo?) {
            super.bindFull(pos, item)
            holderIdAndPosMap[holderId] = pos
//            componentActivity.getComponentApi(VideoPlayComponentApi::class.java, holderId.toString())?.bindVideoDetailInfo(item)
//            componentActivity.getComponentApi(VideoActionComponentApi::class.java, holderId.toString())?.bindVideoDetailInfo(item)
//            componentActivity.getComponentApi(VideoInfoComponentApi::class.java, holderId.toString())?.bindVideoDetailInfo(item)

            componentActivity.getComponentApi(VideoPlayComponentApi::class.java, holderId.toString()) {
                it.bindVideoDetailInfo(item)
            }
            componentActivity.getComponentApi(VideoActionComponentApi::class.java, holderId.toString()) {
                it.bindVideoDetailInfo(item)
            }
            componentActivity.getComponentApi(VideoInfoComponentApi::class.java, holderId.toString()) {
                it.bindVideoDetailInfo(item)
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
