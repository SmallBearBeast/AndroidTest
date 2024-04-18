package com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo.Detail;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bear.libcomponent.component.ViewComponent;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo.TiktokVideoDetailInfo;

public class VideoActionComponent extends ViewComponent {
    private ImageView authorAvatarIv;
    private ImageView musicAvatarIv;
    private TextView likeCountTv;
    private TextView commentCountTv;
    private TextView collectCountTv;
    private TextView shareCountTv;

    public VideoActionComponent(View view) {
        super(view);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onAttachView(View view) {
        super.onAttachView(view);
        authorAvatarIv = findViewById(R.id.authorAvatarIv);
        musicAvatarIv = findViewById(R.id.musicAvatarIv);
        likeCountTv = findViewById(R.id.likeCountTv);
        commentCountTv = findViewById(R.id.commentCountTv);
        collectCountTv = findViewById(R.id.collectCountTv);
        shareCountTv = findViewById(R.id.shareCountTv);
    }

    private void bindVideoDetailInfo(TiktokVideoDetailInfo videoDetailInfo) {
        Glide.with(getContext()).load(videoDetailInfo.authorImgUrl).transform(new CircleCrop()).into(authorAvatarIv);
        Glide.with(getContext()).load(videoDetailInfo.musicImgUrl).transform(new CircleCrop()).into(musicAvatarIv);
        likeCountTv.setText(String.format("%d", videoDetailInfo.likeCount));
        commentCountTv.setText(String.format("%d", videoDetailInfo.likeCount));
        collectCountTv.setText(String.format("%d", videoDetailInfo.likeCount));
        shareCountTv.setText(String.format("%d", videoDetailInfo.likeCount));
    }

    public void bindVideoDetailInfoExt(TiktokVideoDetailInfo videoDetailInfo) {
        bindVideoDetailInfo(videoDetailInfo);
    }
}
