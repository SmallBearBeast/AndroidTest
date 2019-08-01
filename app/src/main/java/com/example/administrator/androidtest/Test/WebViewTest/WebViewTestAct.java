package com.example.administrator.androidtest.Test.WebViewTest;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.*;
import com.example.administrator.androidtest.R;
import com.example.libbase.Util.AppUtil;
import com.example.libframework.ActAndFrag.ComponentAct;

public class WebViewTestAct extends ComponentAct {
    private static final String TAG = "WebViewTestAct";
    private WebView mWebView;

    @Override
    protected int layoutId() {
        return R.layout.act_webview_test;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mWebView = findViewById(R.id.wv_browser);
        initWebView();
        initJsCallback();
        initUserAgent();
        initCookie();
        mWebView.loadUrl("https://www.baidu.com");
    }

    private void initCookie() {
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(mWebView, true);
        }else {
            cookieManager.setAcceptCookie(true);
        }
    }

    private void initUserAgent() {
        mWebView.getSettings().setUserAgentString(mWebView.getSettings().getUserAgentString());
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    private void initJsCallback() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        mWebView.addJavascriptInterface(new AndroidMethod(), AndroidMethod.NAME);
    }

    private void initWebView(){
        mWebView.setWebViewClient(new WebViewClient(){
            //拦截方法，使之在浏览器处理
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }

            //页面加载开始，可以用于start dialog
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d(TAG, "onPageStarted: url = " + url);
            }

            //页面加载结束，可以用于stop dialog
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG, "onPageFinished: url = " + url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if(AppUtil.isAppDebug()){
                    handler.proceed();
                    return;
                }
                super.onReceivedSslError(view, handler, error);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient(){
            //加载进度
            //WebChromeClient与webViewClient的区别
            //webViewClient处理偏界面的操作：打开新界面，界面打开，界面打开结束
            //WebChromeClient处理偏js的操作
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.d(TAG, "onProgressChanged: newProgress = " + newProgress);
            }

            //对应回调js的alert弹窗
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            //对应回调js的confirm弹窗
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            //对应回调js的prompt弹窗
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            //返回上一个页
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }

    public void callJs(){
        //loadUrl()不能从Js返回数据，可以让Js回调android的方法回传参数。
        mWebView.loadUrl("javascript:show('Hello')");

        //4.4以上使用
        mWebView.evaluateJavascript("javascript:show('Hello')", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                //接受js返回值
            }
        });
    }
}
