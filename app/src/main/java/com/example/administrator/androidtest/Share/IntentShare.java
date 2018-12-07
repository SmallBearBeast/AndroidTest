package com.example.administrator.androidtest.Share;

import android.net.Uri;

public class IntentShare {
    public static final String TYPE_IMAGE = "image/*";
    public static final String TYPE_VIDEO = "video/mp4";
    public static final String TYPE_TEXT = "text/plain";

    public static final String PACKAGE_FB = "com.facebook.katana";
    public static final String PACKAGE_TWITTER = "com.twitter.android";
    public static final String PACKAGE_INSTAGRAM = "com.instagram.android";
    public static final String PACKAGE_MESSENGER = "com.facebook.orca"; //"sk.forbis.messenger"
    public static final String PACKAGE_WHATSAPP = "com.whatsapp";
    public static final String PACKAGE_VK = "com.vkontakte.android";
    public static final String PACKAGE_BBM ="com.bbm";
    public static final String PACKAGE_HIKE = "com.bsb.hike";
    public static final String PACKAGE_IMO = "com.imo.android.imoim";
    public static final String PACKAGE_MUSICALLY = "com.zhiliaoapp.musically";
    public static final String PACKAGE_WECHAT = "com.tencent.mm";
    public static final String PACKAGE_QQ = "com.tencent.mobileqq";
    public static final String PACKAGE_QQI = "com.tencent.mobileqqi";
    public static final String PACKAGE_QQLITE = "com.tencent.qqlite";
    public static final String PACKAGE_QZONE = "com.qzone";
    public static final String PACKAGE_WEIBO = "com.sina.weibo";
    public static final String PACKAGE_YOUTUBE = "com.google.android.youtube";
    public static final String PACKAGE_LINE = "jp.naver.line.android";
    public static final String PACKAGE_FACEBOOK_LITE = "com.facebook.lite";
    public static final String PACKAGE_VIBER = "com.viber.voip";
    public static final String PACKAGE_WELIKE = "com.redefine.welike";

    public static final String PACKAGE_TRUECALLER = "com.truecaller";
    public static final String PACKAGE_OK_NOPAY = "ru.ok.android.nopay";
    public static final String PACKAGE_OK = "ru.ok.android";
    public static final String PACKAGE_TELEGRAM = "org.telegram.messenger";
    public static final String PACKAGE_SNAPCHAT = "com.snapchat.android";


    private String mText;
    private Uri mImageUri;
    private Uri mVideoUri;

    public IntentShare(String text, Uri imageUri, Uri videoUri) {
        mText = text;
        mImageUri = imageUri;
        mVideoUri = videoUri;
    }

    public IntentShare setText(String text){
        mText = text;
        return this;
    }

    public IntentShare setImageUri(Uri imageUri){
        mImageUri = imageUri;
        return this;
    }

    public IntentShare setVideoUri(Uri videoUri){
        mVideoUri = videoUri;
        return this;
    }

    public String getText() {
        return mText == null ? "" : mText;
    }

    public Uri getImageUri() {
        return mImageUri;
    }

    public Uri getVideoUri() {
        return mVideoUri;
    }
}
