package com.example.administrator.androidtest.other.fileMedia.Info;

import com.example.administrator.androidtest.other.fileMedia.Provider.MediaConstant;

public class VideoInfo extends BaseInfo {
    public int mWidth;
    public int mHeight;
    public int mOrientation;
    public long mDuration;

    public VideoInfo() {
        mType = MediaConstant.VIDEO;
    }
}