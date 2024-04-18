package com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo.Detail;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ViewComponent;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo.TiktokVideoDetailInfo;

public class VideoInfoComponent extends ViewComponent {

    private ImageView thumbIv;
    private TextView authorNameTv;
    private TextView titleTv;

    public VideoInfoComponent(View view) {
        super(view);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onAttachView(View view) {
        super.onAttachView(view);
        thumbIv = findViewById(R.id.thumbIv);
        authorNameTv = findViewById(R.id.authorNameTv);
        titleTv = findViewById(R.id.titleTv);
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
