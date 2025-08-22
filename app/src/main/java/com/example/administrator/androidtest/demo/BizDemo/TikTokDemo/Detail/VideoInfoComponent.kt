package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.Detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.androidtest.databinding.ComponentTiktokVideoInfoBinding
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.TiktokVideoDetailInfo
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.bizcompoent.BizComponent
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.bizcompoent.IBizComponentApi

class VideoInfoComponent : BizComponent<ComponentTiktokVideoInfoBinding>(), VideoInfoComponentApi {

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        ComponentTiktokVideoInfoBinding.inflate(inflater, container, false)

    private fun _bindVideoDetailInfo(videoDetailInfo: TiktokVideoDetailInfo) {
//        Glide.with(getContext()).load(videoDetailInfo.coverImgUrl).into(new Target<Drawable>() {
//            @Override
//            public void onLoadStarted(@Nullable Drawable placeholder) {
//
//            }
//
//            @Override
//            public void onLoadFailed(@Nullable Drawable errorDrawable) {
//
//            }
//
//            @Override
//            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                thumbIv.setImageDrawable(resource);
//            }
//
//            @Override
//            public void onLoadCleared(@Nullable Drawable placeholder) {
//
//            }
//
//            @Override
//            public void getSize(@NonNull SizeReadyCallback cb) {
//
//            }
//
//            @Override
//            public void removeCallback(@NonNull SizeReadyCallback cb) {
//
//            }
//
//            @Override
//            public void setRequest(@Nullable Request request) {
//
//            }
//
//            @Nullable
//            @Override
//            public Request getRequest() {
//                return null;
//            }
//
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onStop() {
//
//            }
//
//            @Override
//            public void onDestroy() {
//
//            }
//        });
        requireBinding().apply {
            Log.d(TAG, "_bindVideoDetailInfo: authorName = " + videoDetailInfo.authorName + ", title = " + videoDetailInfo.title)
            authorNameTv.text = videoDetailInfo.authorName
            titleTv.text = videoDetailInfo.title
        }
    }

    override fun bindVideoDetailInfo(videoDetailInfo: TiktokVideoDetailInfo?) {
        videoDetailInfo ?: return
        _bindVideoDetailInfo(videoDetailInfo)
    }
}


interface VideoInfoComponentApi : IBizComponentApi {
    fun bindVideoDetailInfo(videoDetailInfo: TiktokVideoDetailInfo?)
}
