package com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.detail

import android.util.Log
import com.bear.libcomponent.component.ui.ViewComponent
import com.bear.libcomponent.core.IComponent
import com.example.administrator.androidtest.databinding.ComponentTiktokVideoInfoBinding
import com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.TiktokDetailInfo

class VideoInfoComponent(binding: ComponentTiktokVideoInfoBinding) : ViewComponent<ComponentTiktokVideoInfoBinding>(binding), IVideoInfoComponent {

    private fun _bindVideoDetailInfo(videoDetailInfo: TiktokDetailInfo) {
        requireBinding().apply {
            Log.d(TAG, "_bindVideoDetailInfo: authorName = " + videoDetailInfo.authorName + ", title = " + videoDetailInfo.title)
            authorNameTv.text = videoDetailInfo.authorName
            titleTv.text = videoDetailInfo.title
        }
    }

    override fun bindVideoDetailInfo(videoDetailInfo: TiktokDetailInfo?) {
        videoDetailInfo ?: return
        _bindVideoDetailInfo(videoDetailInfo)
    }
}


interface IVideoInfoComponent : IComponent {
    fun bindVideoDetailInfo(videoDetailInfo: TiktokDetailInfo?)
}
