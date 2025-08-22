package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.Detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bear.libcommon.util.ToastUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.administrator.androidtest.databinding.ComponentTiktokVideoActionBinding
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.TiktokVideoDetailInfo
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.bizcompoent.BizComponent
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.bizcompoent.IBizComponentApi

class VideoActionComponent : BizComponent<ComponentTiktokVideoActionBinding>(), VideoActionComponentApi {

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        ComponentTiktokVideoActionBinding.inflate(inflater, container, false)

    override fun initViews(view: View) {
        super.initViews(view)
        requireBinding().apply {
            authorAvatarIv.setOnClickListener {
                ToastUtil.showToast("点击了作者头像")
            }
        }
    }

    private fun _bindVideoDetailInfo(videoDetailInfo: TiktokVideoDetailInfo) {
        requireBinding().apply {
            Glide.with(requireContext()).load(videoDetailInfo.authorImgUrl).transform(CircleCrop()).into(authorAvatarIv)
            Glide.with(requireContext()).load(videoDetailInfo.musicImgUrl).transform(CircleCrop()).into(musicAvatarIv)
            likeCountTv.text = String.format("%d", videoDetailInfo.likeCount)
            commentCountTv.text = String.format("%d", videoDetailInfo.likeCount)
            collectCountTv.text = String.format("%d", videoDetailInfo.likeCount)
            shareCountTv.text = String.format("%d", videoDetailInfo.likeCount)
        }
    }

    override fun bindVideoDetailInfo(videoDetailInfo: TiktokVideoDetailInfo?) {
        videoDetailInfo?:return
        _bindVideoDetailInfo(videoDetailInfo)
    }
}

interface VideoActionComponentApi: IBizComponentApi {
    fun bindVideoDetailInfo(videoDetailInfo: TiktokVideoDetailInfo?)
}
