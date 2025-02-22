package com.example.administrator.androidtest.other.fileMedia.Info;

import com.example.administrator.androidtest.other.fileMedia.Provider.MediaConstant;

public class ImageInfo extends BaseInfo{
    public int mWidth;
    public int mHeight;
    public int mOrientation;

    public ImageInfo() {
        mType = MediaConstant.IMAGE;
    }
}
