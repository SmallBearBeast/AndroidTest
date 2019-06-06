package com.example.administrator.androidtest.Test.OkHttpTest;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.androidtest.Base.ActAndFrag.ComponentAct;
import com.example.administrator.androidtest.Common.Util.Core.CollectionUtil;
import com.example.administrator.androidtest.Common.Util.Core.NetWorkUtil;
import com.example.administrator.androidtest.Common.Util.Core.ToastUtils;
import com.example.administrator.androidtest.Common.Util.File.FileUtil;
import com.example.administrator.androidtest.Net.Okhttp.OkCallback;
import com.example.administrator.androidtest.Net.Okhttp.OkDownloadCallback;
import com.example.administrator.androidtest.Net.Okhttp.OkHelper;
import com.example.administrator.androidtest.R;
import okhttp3.Headers;

import java.io.File;
import java.util.Map;

public class OkHttpAct extends ComponentAct {

    private static final String TAG = "OkHttpAct";
    private TextView mTvContent;
    @Override
    protected int layoutId() {
        return R.layout.act_okhttp;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mTvContent = findViewById(R.id.tv_content);
    }


    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_get:
//                testGet_1("https://www.baidu.com/");
//                testGet_2("https://www.mxnzp.com/api/holiday/single/20181121");
                testGet_3("https://www.mxnzp.com/api/qrcode/create/single?content=你好&size=500&type=0");
                break;

            case R.id.bt_post:
                Map<String, String> params = CollectionUtil.asMap(new String[]{"content", "size", "type"}, new String[]{"你好", "500", "0"});

                testPost_1("https://www.mxnzp.com/api/qrcode/create/single", params);
                break;

            case R.id.bt_download:
//                testDownLoad_1("https://cdn.pixabay.com/photo/2015/03/26/09/41/chain-690088_960_720.jpg");
                testDownLoad_1("http://vfx.mtime.cn/Video/2017/03/31/mp4/170331093811717750.mp4");
                break;

            case R.id.bt_cancel_download:
                OkHelper.getInstance().cancel("http://vfx.mtime.cn/Video/2017/03/31/mp4/170331093811717750.mp4");
                break;

            case R.id.bt_upload:
                break;
        }
    }

    private void testGet_3(String url){
        if(NetWorkUtil.isConnected()){
            OkHelper.getInstance().postMethod(url, new OkCallback<QrCodeBean>(QrCodeBean.class){
                @Override
                protected void handleErrCode(int errCode) {
                    super.handleErrCode(errCode);
                }

                @Override
                protected void onSuccess(QrCodeBean data) {
                    super.onSuccess(data);
                    mTvContent.setText(data.toString());
                }

                @Override
                protected void onFail() {
                    super.onFail();
                }
            });
        }else {
            ToastUtils.showToast("网络不好，请检查网络");
        }
    }

    private void testGet_2(String url){
        if(NetWorkUtil.isConnected()){
            OkHelper.getInstance().postMethod(url, new OkCallback<DateInfoBean>(DateInfoBean.class){
                @Override
                protected void handleErrCode(int errCode) {
                    super.handleErrCode(errCode);
                }

                @Override
                protected void onSuccess(DateInfoBean data) {
                    super.onSuccess(data);
                    mTvContent.setText(data.toString());
                }

                @Override
                protected void onFail() {
                    super.onFail();
                }
            });
        }else {
            ToastUtils.showToast("网络不好，请检查网络");
        }
    }



    private void testGet_1(String url){
        if(NetWorkUtil.isConnected()){
            OkHelper.getInstance().getMethod(url, new OkCallback<String>(String.class){
                @Override
                protected void handleErrCode(int errCode) {
                    super.handleErrCode(errCode);
                }

                @Override
                protected void onSuccess(String data) {
                    super.onSuccess(data);
                    mTvContent.setText(data);
                }

                @Override
                protected void onFail() {
                    super.onFail();
                }
            });
        }else {
            ToastUtils.showToast("网络不好，请检查网络");
        }
    }

    private void testPost_1(String url, Map<String, String> params){
        if(NetWorkUtil.isConnected()){
            Map<String, String> headerParams = CollectionUtil.asMap(new String[]{"hello", "world"}, new String[]{"123", "456"});

            OkHelper.getInstance().postMethod(url, params, Headers.of(headerParams), new OkCallback<QrCodeBean>(QrCodeBean.class) {
                @Override
                protected void handleErrCode(int errCode) {
                    super.handleErrCode(errCode);
                }

                @Override
                protected void onSuccess(QrCodeBean data) {
                    super.onSuccess(data);
                }

                @Override
                protected void onFail() {
                    super.onFail();
                }
            });
        }else {
            ToastUtils.showToast("网络不好，请检查网络");
        }
    }

    private void testDownLoad_1(String url){
        final String path = Environment.getExternalStorageDirectory().getPath() + File.separator + "hello" + "." + FileUtil.getSuffix(url);
        final String tempPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "hello_temp" + "." + FileUtil.getSuffix(url);
//        if(FileUtil.deleteFile(path)){
//            Log.d(TAG, "testDownLoad_1(): " + "delete file");
//        }
        if(NetWorkUtil.isConnected()){
            OkHelper.getInstance().downloadFile(url, tempPath, new OkDownloadCallback() {
                @Override
                public void onSuccess() {
                    super.onSuccess();
                    FileUtil.rename(tempPath, path);
                }

                @Override
                public void onFailure(int errorCode) {
                    super.onFailure(errorCode);
                }

                @Override
                public void onProgress(int progress) {
                    super.onProgress(progress);
                    Log.d(TAG, "testDownLoad_1(): progress = " + progress);
                }
            });
        }else {
            ToastUtils.showToast("网络不好，请检查网络");
        }
    }

}
