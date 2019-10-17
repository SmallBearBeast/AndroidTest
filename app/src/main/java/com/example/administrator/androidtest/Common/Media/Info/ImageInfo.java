package com.example.administrator.androidtest.Common.Media.Info;

import com.example.administrator.androidtest.Common.Media.Provider.MediaConstant;

public class ImageInfo extends BaseInfo{
    public int mWidth;
    public int mHeight;
    public int mOrientation;

    public ImageInfo() {
        mType = MediaConstant.IMAGE;
    }
}
