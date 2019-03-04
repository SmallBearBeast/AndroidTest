package com.example.administrator.androidtest.Net.Okhttp;

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

    private static final int TIME_OUT = 10;
    private static volatile OkHelper sOkHelper;
    private static volatile OkHttpClient sOkClient;
    private static volatile OkRequsetProvider sOkRequestProvider;

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

    private OkHelper(){
        sOkHelper = new OkHelper();
        sOkRequestProvider = new OkRequsetProvider();
        sOkClient = initOkHttpClient();
    }

    private static class SingleTon{
        static OkHelper sInstance = new OkHelper();
    }

    public void downloadFile(String url, final String SAVE_PATH, final OkDownloadCallback DOWNLOAD_CALLBACK) {
        final int CONTENT_LENGTH = (int) SPUtil.getDataFromOther(SAVE_PATH, 0);
        final String TOTAL_LENGTH_KEY = SAVE_PATH + "_total_length_key";
        final String DOWNLOAD_LENGTH_KEY = SAVE_PATH + "_download_length_key";
        if(CONTENT_LENGTH == 0){
            getMethod(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if(DOWNLOAD_CALLBACK != null){
                        DOWNLOAD_CALLBACK.onFailure(OkDownloadCallback.ERROR_IO);
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
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
                                    if(!StorageUtil.hasSpace(length)){
                                        if(DOWNLOAD_CALLBACK != null){
                                            DOWNLOAD_CALLBACK.onFailure(OkDownloadCallback.ERROR_STORAGE);
                                        }
                                        return;
                                    }
                                    int read = 0;
                                    int downloadingLength = 0;
                                    byte[] buffer = new byte[1024];
                                    while ((read = ins.read(buffer, 0, length - downloadingLength)) != -1) {
                                        raf.write(buffer, 0, read);
                                        downloadingLength = downloadingLength + read;
                                        if(DOWNLOAD_CALLBACK != null) {
                                            DOWNLOAD_CALLBACK.onProgress(downloadingLength / length * 100);
                                        }
                                        SPUtil.putDataToOther(DOWNLOAD_LENGTH_KEY, downloadingLength);
                                    }
                                    SPUtil.remove(SPUtil.OTHER, TOTAL_LENGTH_KEY);
                                    SPUtil.remove(SPUtil.OTHER, DOWNLOAD_LENGTH_KEY);
                                    if(DOWNLOAD_CALLBACK != null) {
                                        DOWNLOAD_CALLBACK.onSuccess();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    if(DOWNLOAD_CALLBACK != null) {
                                        DOWNLOAD_CALLBACK.onFailure(OkDownloadCallback.ERROR_FILE);
                                    }
                                } finally {
                                    IOUtil.close(ins, raf);
                                }
                            }
                        }
                    }
                }
            });
        }else {
            final int DOWNLOAD_LENGTH = (int) SPUtil.getDataFromOther(DOWNLOAD_LENGTH_KEY, 0);
            if(!StorageUtil.hasSpace(CONTENT_LENGTH - DOWNLOAD_LENGTH)){
                if(DOWNLOAD_CALLBACK != null){
                    DOWNLOAD_CALLBACK.onFailure(OkDownloadCallback.ERROR_STORAGE);
                }
                return;
            }
            Headers headers = Headers.of("RANGE", "bytes=" + DOWNLOAD_LENGTH + "-" + CONTENT_LENGTH);
            getMethod(url, headers, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if(DOWNLOAD_CALLBACK != null) {
                        DOWNLOAD_CALLBACK.onFailure(OkDownloadCallback.ERROR_IO);
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
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
                                    int downloadingLength = DOWNLOAD_LENGTH;
                                    byte[] buffer = new byte[1024];
                                    while ((read = ins.read(buffer, 0, CONTENT_LENGTH - DOWNLOAD_LENGTH - downloadingLength)) != -1) {
                                        raf.write(buffer, 0, read);
                                        downloadingLength = downloadingLength + read;
                                        if(DOWNLOAD_CALLBACK != null) {
                                            DOWNLOAD_CALLBACK.onProgress(downloadingLength / CONTENT_LENGTH * 100);
                                        }
                                        SPUtil.putDataToOther(DOWNLOAD_LENGTH_KEY, downloadingLength);
                                    }
                                    SPUtil.remove(SPUtil.OTHER, TOTAL_LENGTH_KEY);
                                    SPUtil.remove(SPUtil.OTHER, DOWNLOAD_LENGTH_KEY);
                                    if(DOWNLOAD_CALLBACK != null) {
                                        DOWNLOAD_CALLBACK.onSuccess();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    if(DOWNLOAD_CALLBACK != null) {
                                        DOWNLOAD_CALLBACK.onFailure(OkDownloadCallback.ERROR_FILE);
                                    }
                                } finally {
                                    IOUtil.close(ins, raf);
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    public void uploadFile(String url, final OkUploadCallback UPLOAD_CALLBACK, File... files){
        final boolean[] IS_FAILED = new boolean[]{false};
        newCall(sOkRequestProvider.requestMultipartPost(url, null, null, new OkUploadCallback() {
            @Override
            public void onProgress(int progress) {
                if(UPLOAD_CALLBACK  != null) {
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
                if(UPLOAD_CALLBACK != null){
                    UPLOAD_CALLBACK.onFailure(OkUploadCallback.ERROR_IO);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    if(UPLOAD_CALLBACK != null) {
                        if(IS_FAILED[0]){
                            UPLOAD_CALLBACK.onFailure(OkUploadCallback.ERROR_PART_FAIL);
                        }else {
                            UPLOAD_CALLBACK.onSuccess();
                        }
                    }
                }else {
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
