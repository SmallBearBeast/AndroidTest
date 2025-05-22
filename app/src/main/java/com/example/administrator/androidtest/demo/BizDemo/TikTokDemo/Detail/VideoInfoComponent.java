package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.Detail;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bear.libcomponent.component.ViewComponent;
import com.bumptech.glide.Glide;
import com.example.administrator.androidtest.databinding.ItemTiktokVideoDetailBinding;
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.TiktokVideoDetailInfo;

public class VideoInfoComponent extends ViewComponent<ItemTiktokVideoDetailBinding> {

    private ImageView thumbIv;
    private TextView authorNameTv;
    private TextView titleTv;

    public VideoInfoComponent(ItemTiktokVideoDetailBinding binding) {
        super(binding);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onAttachViewBinding(ItemTiktokVideoDetailBinding binding) {
        super.onAttachViewBinding(binding);
        thumbIv = getBinding().thumbIv;
        authorNameTv = getBinding().authorNameTv;
        titleTv = getBinding().titleTv;
    }

    private void bindVideoDetailInfo(TiktokVideoDetailInfo videoDetailInfo) {
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
        Glide.with(getContext()).load(videoDetailInfo.coverImgUrl).into(thumbIv);
        Log.d(TAG, "authorName = " + videoDetailInfo.authorName + ", thumbIv is visible: " + (thumbIv.getVisibility() == View.VISIBLE));
        authorNameTv.setText(videoDetailInfo.authorName);
        titleTv.setText(videoDetailInfo.title);
    }

    public void bindVideoDetailInfoExt(TiktokVideoDetailInfo videoDetailInfo) {
        bindVideoDetailInfo(videoDetailInfo);
    }

    public void showThumbExt(boolean show) {
        if (show) {
            thumbIv.setVisibility(View.VISIBLE);
        } else {
            thumbIv.setVisibility(View.GONE);
        }
        Log.d(TAG, "showThumbExt: thumbIv = " + thumbIv);
    }
}
