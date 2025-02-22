package com.example.administrator.androidtest.other.fileMedia.Info;

import android.graphics.drawable.Drawable;
import com.example.administrator.androidtest.other.fileMedia.Provider.MediaConstant;

public class ApkInfo extends BaseInfo {
    public Drawable mIconDrawable;

    public ApkInfo() {
        mMime = "application/vnd.android.package-archive";
        mSuffix = "apk";
        mType = MediaConstant.APPLICATION;
    }
}
