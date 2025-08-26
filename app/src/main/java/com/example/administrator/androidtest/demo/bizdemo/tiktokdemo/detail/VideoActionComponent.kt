package com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.detail

import com.bear.libcommon.util.ToastUtil
import com.bear.libcomponent.component.ui.ViewComponent
import com.bear.libcomponent.core.IComponent
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.administrator.androidtest.databinding.ComponentTiktokVideoActionBinding
import com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.TiktokDetailInfo

class VideoActionComponent(binding: ComponentTiktokVideoActionBinding) : ViewComponent<ComponentTiktokVideoActionBinding>(binding),
    IVideoActionComponent {
    override fun onCreate() {
        super.onCreate()
        requireBinding().apply {
            authorAvatarIv.setOnClickListener {
                ToastUtil.showToast("点击了作者头像")
            }
        }
    }

    private fun _bindVideoDetailInfo(videoDetailInfo: TiktokDetailInfo) {
        requireBinding().apply {
            Glide.with(requireContext()).load(videoDetailInfo.authorImgUrl).transform(CircleCrop()).into(authorAvatarIv)
            Glide.with(requireContext()).load(videoDetailInfo.musicImgUrl).transform(CircleCrop()).into(musicAvatarIv)
            likeCountTv.text = String.format("%d", videoDetailInfo.likeCount)
            commentCountTv.text = String.format("%d", videoDetailInfo.likeCount)
            collectCountTv.text = String.format("%d", videoDetailInfo.likeCount)
            shareCountTv.text = String.format("%d", videoDetailInfo.likeCount)
        }
    }

    override fun bindVideoDetailInfo(videoDetailInfo: TiktokDetailInfo?) {
        videoDetailInfo ?: return
        _bindVideoDetailInfo(videoDetailInfo)
    }
}

interface IVideoActionComponent : IComponent {
    fun bindVideoDetailInfo(videoDetailInfo: TiktokDetailInfo?)
}
