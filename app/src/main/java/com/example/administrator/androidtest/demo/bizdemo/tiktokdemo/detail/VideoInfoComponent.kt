package com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.detail

import android.util.Log
import com.bear.libcomponent.component.ui.ViewComponent
import com.bear.libcomponent.core.IComponent
import com.example.administrator.androidtest.databinding.ComponentTiktokVideoInfoBinding
import com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.TiktokDetailInfo

class VideoInfoComponent(binding: ComponentTiktokVideoInfoBinding) : ViewComponent<ComponentTiktokVideoInfoBinding>(binding), IVideoInfoComponent {

    private fun _bindVideoDetailInfo(videoDetailInfo: TiktokDetailInfo) {
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

    override fun bindVideoDetailInfo(videoDetailInfo: TiktokDetailInfo?) {
        videoDetailInfo ?: return
        _bindVideoDetailInfo(videoDetailInfo)
    }
}


interface IVideoInfoComponent : IComponent {
    fun bindVideoDetailInfo(videoDetailInfo: TiktokDetailInfo?)
}
