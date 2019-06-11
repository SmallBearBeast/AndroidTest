package com.example.administrator.androidtest.Net.Okhttp;

import com.example.administrator.androidtest.Common.Util.Core.SPUtil;
import com.example.administrator.androidtest.Common.Util.File.FileUtil;
import com.example.administrator.androidtest.Common.Util.Other.IOUtil;
import com.example.administrator.androidtest.Common.Util.Other.StorageUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.example.administrator.androidtest.Log.SLog;
import okhttp3.*;

public class OkHelper {

    private static final String TAG = "OkHelper";
    private static final int TIME_OUT = 3;
    private static final int DOWNLOAD_BUFFER_COUNT = 1024 / 2;
    private OkHttpClient mOkClient;
    private OkRequsetProvider mOkRequestProvider;
    private Map<String, Call> mRunningMap;

    // TODO: 2019/4/14 超时参数理解
    private static OkHttpClient initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS);
        //拦截器
        builder.addInterceptor(new OkLogInterceptor());
        builder.addInterceptor(new OkRetryInterceptor());
        //HttpDns
        builder.dns(new HttpDns());
        //监听器
        builder.eventListener(new OkEventListener());
        builder.cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                return Collections.emptyList();
            }
        });
        //支持https

        //缓存配置
//        builder.cache(new Cache());
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    public static OkHelper getInstance() {
        return SingleTon.sInstance;
    }

    private OkHelper() {
        mOkRequestProvider = new OkRequsetProvider();
        mOkClient = initOkHttpClient();
        mRunningMap = new HashMap<>();
    }

    private static class SingleTon {
        static OkHelper sInstance = new OkHelper();
    }

    /**
     * 取消接口
     */
    public void cancel(String url){
        if(mRunningMap.containsKey(url)){
            SLog.d(TAG, "cancel: url = " + url);
            mRunningMap.get(url).cancel();
            mRunningMap.remove(url);
        }
    }

    private void onDownloadFailure(OkDownloadCallback downloadCallback, int err){
        if (downloadCallback != null) {
            downloadCallback.onFailure(err);
        }
    }

    private void onDownloadSuccess(OkDownloadCallback downloadCallback){
        if(downloadCallback != null){
            downloadCallback.onSuccess();
        }
    }

    private void onDownloadProgress(OkDownloadCallback downloadCallback, int progress){
        if(downloadCallback != null){
            downloadCallback.onProgress(progress);
        }
    }

    public void downloadFile(final String url, final String SAVE_PATH, final OkDownloadCallback DOWNLOAD_CALLBACK) {
        final String TOTAL_LENGTH_KEY = SAVE_PATH.hashCode() + "_total_length_key";
        final int CONTENT_LENGTH = (int) SPUtil.getDataFromOther(TOTAL_LENGTH_KEY, 0);
        if (CONTENT_LENGTH == 0) {
            getMethod(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    SLog.d(TAG, "onFailure: with full request", e);
                    onDownloadFailure(DOWNLOAD_CALLBACK, OkDownloadCallback.ERROR_IO);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        SLog.d(TAG, "onResponse: successful with full request");
                        ResponseBody body = response.body();
                        if (body != null) {
                            if (FileUtil.createFile(SAVE_PATH)) {
                                InputStream ins = null;
                                RandomAccessFile raf = null;
                                try {
                                    ins = body.byteStream();
                                    raf = new RandomAccessFile(SAVE_PATH, "rw");
                                    int length = (int) body.contentLength();
                                    SPUtil.putDataToOther(TOTAL_LENGTH_KEY, length);
                                    if (!StorageUtil.hasSpace(length)) {
                                        onDownloadFailure(DOWNLOAD_CALLBACK, OkDownloadCallback.ERROR_STORAGE);
                                        return;
                                    }
                                    int read;
                                    int downloadingLength = 0;
                                    byte[] buffer = new byte[DOWNLOAD_BUFFER_COUNT];
                                    int readLength = length - downloadingLength < DOWNLOAD_BUFFER_COUNT ? length - downloadingLength : DOWNLOAD_BUFFER_COUNT;
                                    while ((read = ins.read(buffer, 0, readLength)) != -1) {
                                        raf.write(buffer, 0, read);
                                        downloadingLength = downloadingLength + read;
                                        readLength = length - downloadingLength < DOWNLOAD_BUFFER_COUNT ? length - downloadingLength : DOWNLOAD_BUFFER_COUNT;
                                        onDownloadProgress(DOWNLOAD_CALLBACK, (int) (downloadingLength * 1f / length * 100));
                                        SLog.d(TAG, "onResponse: full request downloadingLength = " + downloadingLength);
                                    }
                                    SPUtil.remove(SPUtil.OTHER, TOTAL_LENGTH_KEY);
                                    onDownloadSuccess(DOWNLOAD_CALLBACK);
                                } catch (Exception e) {
                                    SLog.d(TAG, "onFailure: with full request", e);
                                    onDownloadFailure(DOWNLOAD_CALLBACK, OkDownloadCallback.ERROR_FILE);
                                } finally {
                                    IOUtil.close(ins, raf);
                                }
                            }
                        }
                    }else {
                        SLog.d(TAG, "onFailure: with full request");
                        onDownloadFailure(DOWNLOAD_CALLBACK, OkDownloadCallback.ERROR_IO);
                    }
                }
            });
        } else {
            final long DOWNLOAD_LENGTH = new File(SAVE_PATH).length();
            if (!StorageUtil.hasSpace(CONTENT_LENGTH - DOWNLOAD_LENGTH)) {
                onDownloadFailure(DOWNLOAD_CALLBACK, OkDownloadCallback.ERROR_STORAGE);
                return;
            }
            Headers headers = Headers.of("RANGE", "bytes=" + DOWNLOAD_LENGTH + "-" + CONTENT_LENGTH);
            getMethod(url, headers, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    SLog.d(TAG, "onFailure: with part request", e);
                    onDownloadFailure(DOWNLOAD_CALLBACK, OkDownloadCallback.ERROR_IO);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        SLog.d(TAG, "onResponse: successful with part request");
                        // TODO: 2019-06-11 是否支持范围请求
                        if(!"bytes".equals(response.header("Access-Ranges"))){
                            downloadFile(url, SAVE_PATH, DOWNLOAD_CALLBACK);
                            SPUtil.remove(SPUtil.OTHER, TOTAL_LENGTH_KEY);
                            return;
                        }
                        ResponseBody body = response.body();
                        if (body != null) {
                            if (FileUtil.createFile(SAVE_PATH)) {
                                InputStream ins = null;
                                RandomAccessFile raf = null;
                                try {
                                    ins = body.byteStream();
                                    raf = new RandomAccessFile(SAVE_PATH, "rw");
                                    raf.seek(DOWNLOAD_LENGTH);
                                    int read = 0;
                                    long downloadingLength = 0;
                                    byte[] buffer = new byte[DOWNLOAD_BUFFER_COUNT];
                                    int readLength = CONTENT_LENGTH - DOWNLOAD_LENGTH - downloadingLength < DOWNLOAD_BUFFER_COUNT ? (int) (CONTENT_LENGTH - DOWNLOAD_LENGTH - downloadingLength) : DOWNLOAD_BUFFER_COUNT;
                                    while ((read = ins.read(buffer, 0, readLength)) != -1) {
                                        raf.write(buffer, 0, read);
                                        downloadingLength = downloadingLength + read;
                                        readLength = CONTENT_LENGTH - DOWNLOAD_LENGTH - downloadingLength < DOWNLOAD_BUFFER_COUNT ? (int) (CONTENT_LENGTH - DOWNLOAD_LENGTH - downloadingLength) : DOWNLOAD_BUFFER_COUNT;
                                        onDownloadProgress(DOWNLOAD_CALLBACK, (int) ((downloadingLength + DOWNLOAD_LENGTH) * 1f / CONTENT_LENGTH * 100));
                                        SLog.d(TAG, "onResponse: with part request downloadingLength = " + downloadingLength);
                                    }
                                    SPUtil.remove(SPUtil.OTHER, TOTAL_LENGTH_KEY);
                                    onDownloadSuccess(DOWNLOAD_CALLBACK);
                                } catch (Exception e) {
                                    SLog.d(TAG, "onFailure: with part request", e);
                                    onDownloadFailure(DOWNLOAD_CALLBACK, OkDownloadCallback.ERROR_FILE);
                                } finally {
                                    IOUtil.close(ins, raf);
                                }
                            }
                        }
                    }else {
                        SLog.d(TAG, "onFailure: with part request");
                        onDownloadFailure(DOWNLOAD_CALLBACK, OkDownloadCallback.ERROR_IO);
                    }
                }
            });
        }
    }

    // TODO: 2019-06-09 如何测试上传文件
    public void uploadFile(String url, final OkUploadCallback UPLOAD_CALLBACK, File... files) {
        final boolean[] IS_FAILED = new boolean[]{false};
        newCall(mOkRequestProvider.requestMultipartPost(url, null, null, new OkUploadCallback() {
            @Override
            public void onProgress(int progress) {
                if (UPLOAD_CALLBACK != null) {
                    UPLOAD_CALLBACK.onProgress(progress);
                }
            }

            @Override
            public void onFailure(int errcode) {
                IS_FAILED[0] = true;
            }

        }, files)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (UPLOAD_CALLBACK != null) {
                    UPLOAD_CALLBACK.onFailure(OkUploadCallback.ERROR_IO);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (UPLOAD_CALLBACK != null) {
                        if (IS_FAILED[0]) {
                            UPLOAD_CALLBACK.onFailure(OkUploadCallback.ERROR_PART_FAIL);
                        } else {
                            UPLOAD_CALLBACK.onSuccess();
                        }
                    }
                } else {
                    UPLOAD_CALLBACK.onFailure(OkUploadCallback.ERROR_IO);
                }
            }
        });
    }

    private void doCall(Call call, final String URL, final Callback CALLBACK){
        if(call.isCanceled()){
            mRunningMap.remove(URL);
            SLog.d(TAG, "doCall: isCanceled url = " + URL);
            return;
        }
        if(call.isExecuted()){
            SLog.d(TAG, "doCall: isExecuted url = " + URL);
            return;
        }
        mRunningMap.put(URL, call);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                CALLBACK.onFailure(call, e);
                mRunningMap.remove(URL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                CALLBACK.onResponse(call, response);
                mRunningMap.remove(URL);
            }
        });
    }

    public void getMethod(final String url, final Callback callback) {
        Call call = mRunningMap.get(url);
        if(call == null){
            call = newCall(mOkRequestProvider.requestGet(url, null, null));
        }
        doCall(call, url, callback);
    }

    public void getMethod(String url, Headers headers, Callback callback) {
        Call call = mRunningMap.get(url);
        if(call == null){
            call = newCall(mOkRequestProvider.requestGet(url, null, headers));
        }
        doCall(call, url, callback);
    }

    public void getMethod(String url, Map<String, String> params, Callback callback) {
        Call call = mRunningMap.get(url);
        if(call == null){
            call = newCall(mOkRequestProvider.requestGet(url, params, null));
        }
        doCall(call, url, callback);
    }

    public void getMethod(String url, Map<String, String> params, Headers headers, Callback callback) {
        Call call = mRunningMap.get(url);
        if(call == null){
            call = newCall(mOkRequestProvider.requestGet(url, params, headers));
        }
        doCall(call, url, callback);
    }

    public void postMethod(String url, Callback callback) {
        Call call = mRunningMap.get(url);
        if(call == null){
            call = newCall(mOkRequestProvider.requestPost(url, null, null));
        }
        doCall(call, url, callback);
    }

    public void postMethod(String url, Headers headers, Callback callback) {
        Call call = mRunningMap.get(url);
        if(call == null){
            call = newCall(mOkRequestProvider.requestPost(url, null, headers));
        }
        doCall(call, url, callback);
    }

    public void postMethod(String url, Map<String, String> params, Callback callback) {
        Call call = mRunningMap.get(url);
        if(call == null){
            call = newCall(mOkRequestProvider.requestPost(url, params, null));
        }
        doCall(call, url, callback);
    }

    public void postMethod(String url, Map<String, String> params, Headers headers, Callback callback) {
        Call call = mRunningMap.get(url);
        if(call == null){
            call = newCall(mOkRequestProvider.requestPost(url, params, headers));
        }
        doCall(call, url, callback);
    }

    public void postMethod(String url, String json, Callback callback) {
        Call call = mRunningMap.get(url);
        if(call == null){
            call = newCall(mOkRequestProvider.requestJsonPost(url, json, null));
        }
        doCall(call, url, callback);
    }

    public void postMethod(String url, String json, Headers headers, Callback callback) {
        Call call = mRunningMap.get(url);
        if(call == null){
            call =  newCall(mOkRequestProvider.requestJsonPost(url, json, headers));
        }
        doCall(call, url, callback);
    }

    public void postMethod(String url, Object jsonObj, Callback callback) {
        Call call = mRunningMap.get(url);
        if(call == null){
            call =  newCall(mOkRequestProvider.requestJsonPost(url, jsonObj, null));
        }
        doCall(call, url, callback);
    }

    public void postMethod(String url, Object jsonObj, Headers headers, Callback callback) {
        Call call = mRunningMap.get(url);
        if(call == null){
            call =  newCall(mOkRequestProvider.requestJsonPost(url, jsonObj, headers));
        }
        doCall(call, url, callback);
    }

    private Call newCall(Request request) {
        return mOkClient.newCall(request);
    }
}
