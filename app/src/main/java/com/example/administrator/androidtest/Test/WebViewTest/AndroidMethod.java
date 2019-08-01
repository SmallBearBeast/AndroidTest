package com.example.administrator.androidtest.Test.WebViewTest;

import android.webkit.JavascriptInterface;
import com.example.libbase.Util.ToastUtil;

public class AndroidMethod {
    public static final String NAME = "android";

    @JavascriptInterface
    public void showToast(String text){
        ToastUtil.showToast(text);
    }
}
