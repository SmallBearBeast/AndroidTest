package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.Detail;

import android.widget.ImageView;
import android.widget.TextView;

import com.bear.libcomponent.component.ViewComponent;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.administrator.androidtest.databinding.ItemTiktokVideoDetailBinding;
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.TiktokVideoDetailInfo;

public class VideoActionComponent extends ViewComponent<ItemTiktokVideoDetailBinding> {
    private ImageView authorAvatarIv;
    private ImageView musicAvatarIv;
    private TextView likeCountTv;
    private TextView commentCountTv;
    private TextView collectCountTv;
    private TextView shareCountTv;

    public VideoActionComponent(ItemTiktokVideoDetailBinding binding) {
        super(binding);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onAttachViewBinding(ItemTiktokVideoDetailBinding binding) {
        super.onAttachViewBinding(binding);
        authorAvatarIv = getBinding().authorAvatarIv;
        musicAvatarIv = getBinding().musicAvatarIv;
        likeCountTv = getBinding().likeCountTv;
        commentCountTv = getBinding().commentCountTv;
        collectCountTv = getBinding().collectCountTv;
        shareCountTv = getBinding().shareCountTv;
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
