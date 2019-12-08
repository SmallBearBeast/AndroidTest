package com.example.administrator.androidtest.Common.Media.Info;

import com.example.administrator.androidtest.Common.Media.Provider.MediaConstant;

public class VideoInfo extends BaseInfo {
    public int mWidth;
    public int mHeight;
    public int mOrientation;
    public long mDuration;

    public VideoInfo() {
        mType = MediaConstant.VIDEO;
    }
}