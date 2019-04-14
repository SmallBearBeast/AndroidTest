package com.example.administrator.androidtest.Net.Okhttp;

import android.util.Log;
import com.example.administrator.androidtest.Common.Util.Core.SPUtil;
import com.example.administrator.androidtest.Common.Util.File.FileUtil;
import com.example.administrator.androidtest.Common.Util.Other.IOUtil;
import com.example.administrator.androidtest.Common.Util.Other.StorageUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHelper {

    private static final String TAG = "OkHelper";
    private static final int TIME_OUT = 10;
    private static final int DOWNLOAD_BUFFER_COUNT = 1024 / 2;
    private OkHttpClient sOkClient;
    private OkRequsetProvider sOkRequestProvider;

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
        //支持https
        return builder.build();
    }

    public static OkHelper getInstance() {
        return SingleTon.sInstance;
    }

    private OkHelper() {
        sOkRequestProvider = new OkRequsetProvider();
        sOkClient = initOkHttpClient();
    }

    private static class SingleTon {
        static OkHelper sInstance = new OkHelper();
    }


    // TODO: 2019/4/14 取消接口主要是下载
    /**
     * 取消接口主要是下载
     */
    public void cancel(){

    }

    public void downloadFile(String url, final String SAVE_PATH, final OkDownloadCallback DOWNLOAD_CALLBACK) {
        final String TOTAL_LENGTH_KEY = SAVE_PATH + "_total_length_key";
        final int CONTENT_LENGTH = (int) SPUtil.getDataFromOther(TOTAL_LENGTH_KEY, 0);
        if (CONTENT_LENGTH == 0) {
            getMethod(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG, "onFailure: with full request");
                    e.printStackTrace();
                    if (DOWNLOAD_CALLBACK != null) {
                        DOWNLOAD_CALLBACK.onFailure(OkDownloadCallback.ERROR_IO);
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: successful with full request");
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
                                        if (DOWNLOAD_CALLBACK != null) {
                                            DOWNLOAD_CALLBACK.onFailure(OkDownloadCallback.ERROR_STORAGE);
                                        }
                                        return;
                                    }
                                    int read = 0;
                                    int downloadingLength = 0;
                                    byte[] buffer = new byte[DOWNLOAD_BUFFER_COUNT];
                                    int readLength = length - downloadingLength < DOWNLOAD_BUFFER_COUNT ? length - downloadingLength : DOWNLOAD_BUFFER_COUNT;
                                    while ((read = ins.read(buffer, 0, readLength)) != -1) {
                                        raf.write(buffer, 0, read);
                                        downloadingLength = downloadingLength + read;
                                        readLength = length - downloadingLength < DOWNLOAD_BUFFER_COUNT ? length - downloadingLength : DOWNLOAD_BUFFER_COUNT;
                                        if (DOWNLOAD_CALLBACK != null) {
                                            DOWNLOAD_CALLBACK.onProgress((int) (downloadingLength * 1f / length * 100));
                                        }
                                        Log.d(TAG, "onResponse: full request downloadingLength = " + downloadingLength);
                                    }
                                    SPUtil.remove(SPUtil.OTHER, TOTAL_LENGTH_KEY);
                                    if (DOWNLOAD_CALLBACK != null) {
                                        DOWNLOAD_CALLBACK.onSuccess();
                                    }
                                } catch (Exception e) {
                                    Log.d(TAG, "onFailure: with full request");
                                    e.printStackTrace();
                                    if (DOWNLOAD_CALLBACK != null) {
                                        DOWNLOAD_CALLBACK.onFailure(OkDownloadCallback.ERROR_FILE);
                                    }
                                } finally {
                                    IOUtil.close(ins, raf);
                                }
                            }
                        }
                    }else {
                        Log.d(TAG, "onFailure: with full request");
                        if (DOWNLOAD_CALLBACK != null) {
                            DOWNLOAD_CALLBACK.onFailure(OkDownloadCallback.ERROR_IO);
                        }
                    }
                }
            });
        } else {
            final long DOWNLOAD_LENGTH = new File(SAVE_PATH).length();
            if (!StorageUtil.hasSpace(CONTENT_LENGTH - DOWNLOAD_LENGTH)) {
                if (DOWNLOAD_CALLBACK != null) {
                    DOWNLOAD_CALLBACK.onFailure(OkDownloadCallback.ERROR_STORAGE);
                }
                return;
            }
            Headers headers = Headers.of("RANGE", "bytes=" + DOWNLOAD_LENGTH + "-" + CONTENT_LENGTH);
            getMethod(url, headers, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG, "onFailure: with part request");
                    if (DOWNLOAD_CALLBACK != null) {
                        DOWNLOAD_CALLBACK.onFailure(OkDownloadCallback.ERROR_IO);
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: successful with part request");
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
                                        if (DOWNLOAD_CALLBACK != null) {
                                            DOWNLOAD_CALLBACK.onProgress((int) ((downloadingLength + DOWNLOAD_LENGTH) * 1f / CONTENT_LENGTH * 100));
                                        }
                                        Log.d(TAG, "onResponse: with part request downloadingLength = " + downloadingLength);
                                    }
                                    SPUtil.remove(SPUtil.OTHER, TOTAL_LENGTH_KEY);
                                    if (DOWNLOAD_CALLBACK != null) {
                                        DOWNLOAD_CALLBACK.onSuccess();
                                    }
                                } catch (Exception e) {
                                    Log.d(TAG, "onFailure: with part request");
                                    e.printStackTrace();
                                    if (DOWNLOAD_CALLBACK != null) {
                                        DOWNLOAD_CALLBACK.onFailure(OkDownloadCallback.ERROR_FILE);
                                    }
                                } finally {
                                    IOUtil.close(ins, raf);
                                }
                            }
                        }
                    }else {
                        Log.d(TAG, "onFailure: with part request");
                        if (DOWNLOAD_CALLBACK != null) {
                            DOWNLOAD_CALLBACK.onFailure(OkDownloadCallback.ERROR_IO);
                        }
                    }
                }
            });
        }
    }

    public void uploadFile(String url, final OkUploadCallback UPLOAD_CALLBACK, File... files) {
        final boolean[] IS_FAILED = new boolean[]{false};
        newCall(sOkRequestProvider.requestMultipartPost(url, null, null, new OkUploadCallback() {
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

    public void getMethod(String url, Callback callback) {
        newCall(sOkRequestProvider.requestGet(url, null, null)).enqueue(callback);
    }

    public void getMethod(String url, Headers headers, Callback callback) {
        newCall(sOkRequestProvider.requestGet(url, null, headers)).enqueue(callback);
    }

    public void getMethod(String url, Map<String, String> params, Callback callback) {
        newCall(sOkRequestProvider.requestGet(url, params, null)).enqueue(callback);
    }

    public void getMethod(String url, Map<String, String> params, Headers headers, Callback callback) {
        newCall(sOkRequestProvider.requestGet(url, params, headers)).enqueue(callback);
    }

    public void postMethod(String url, Callback callback) {
        newCall(sOkRequestProvider.requestPost(url, null, null)).enqueue(callback);
    }

    public void postMethod(String url, Headers headers, Callback callback) {
        newCall(sOkRequestProvider.requestPost(url, null, headers)).enqueue(callback);
    }

    public void postMethod(String url, Map<String, String> params, Callback callback) {
        newCall(sOkRequestProvider.requestPost(url, params, null)).enqueue(callback);
    }

    public void postMethod(String url, Map<String, String> params, Headers headers, Callback callback) {
        newCall(sOkRequestProvider.requestPost(url, params, headers)).enqueue(callback);
    }

    public void postMethod(String url, String json, Callback callback) {
        newCall(sOkRequestProvider.requestJsonPost(url, json, null)).enqueue(callback);
    }

    public void postMethod(String url, String json, Headers headers, Callback callback) {
        newCall(sOkRequestProvider.requestJsonPost(url, json, headers)).enqueue(callback);
    }

    public void postMethod(String url, Object jsonObj, Callback callback) {
        newCall(sOkRequestProvider.requestJsonPost(url, jsonObj, null)).enqueue(callback);
    }

    public void postMethod(String url, Object jsonObj, Headers headers, Callback callback) {
        newCall(sOkRequestProvider.requestJsonPost(url, jsonObj, headers)).enqueue(callback);
    }

    public Call newCall(Request request) {
        return sOkClient.newCall(request);
    }
}
