package com.bear.libokhttp;

import android.app.Application;
import android.util.Log;

import com.bear.liblog.SLog;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class OkHelper {
    private static final String TAG = OkConstant.OK_LOG_TAG;
    private static final int DOWNLOAD_BUFFER_COUNT = 1024 / 2;
    private OkHttpClient mOkClient;
    private OkRequestProvider mOkRequestProvider;
    private Map<String, Call> mRunningCallMap;
    private Map<String, Set<OkCallback>> mRunningCallbackMap;

    public static void init(Application application, OkHttpClient.Builder builder) {
        InternalUtil.init(application);
        getInstance().init(builder);
    }

    private static class SingleTon {
        private static final OkHelper sInstance = new OkHelper();
    }

    public static OkHelper getInstance() {
        return SingleTon.sInstance;
    }

    private void init(OkHttpClient.Builder builder) {
        mOkRequestProvider = new OkRequestProvider();
        mRunningCallMap = new ConcurrentHashMap<>();
        mRunningCallbackMap = new ConcurrentHashMap<>();
        mOkClient = builder == null ? defaultOkHttpClient() : builder.build();
    }

    private OkHttpClient defaultOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 拦截器
        builder.addInterceptor(new OkLogInterceptor());
        // HttpDns
        // builder.dns(new HttpDns());
        // 监听器
        // builder.eventListener(new OkEventListener());
        // 支持https
        // 缓存配置
        // builder.cache(new Cache());
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    /**
     * 取消接口
     */
    public void cancel(String url) {
        if (mRunningCallMap.containsKey(url)) {
            SLog.d(TAG, "cancel: url = " + url);
            mRunningCallMap.get(url).cancel();
            mRunningCallMap.remove(url);
            mRunningCallbackMap.remove(url);
        }
    }

    private void onDownloadFailure(OkDownloadCallback downloadCallback, int err) {
        if (downloadCallback != null) {
            downloadCallback.onFailure(err);
        }
    }

    private void onDownloadSuccess(OkDownloadCallback downloadCallback) {
        if (downloadCallback != null) {
            downloadCallback.onSuccess();
        }
    }

    private void onDownloadProgress(OkDownloadCallback downloadCallback, int progress) {
        if (downloadCallback != null) {
            downloadCallback.onProgress(progress);
        }
    }

    public void downloadFile(final String url, final String SAVE_PATH, final OkDownloadCallback DOWNLOAD_CALLBACK) {
        final String TOTAL_LENGTH_KEY = SAVE_PATH.hashCode() + "_total_length_key";
        final int CONTENT_LENGTH = (int) InternalUtil.getData(TOTAL_LENGTH_KEY, 0);
        if (CONTENT_LENGTH == 0) {
            Call call = newCall(mOkRequestProvider.requestGet(url, null, null));
            call.enqueue(new Callback() {
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
                            if (InternalUtil.createFile(SAVE_PATH)) {
                                InputStream ins = null;
                                RandomAccessFile raf = null;
                                try {
                                    ins = body.byteStream();
                                    raf = new RandomAccessFile(SAVE_PATH, "rw");
                                    int length = (int) body.contentLength();
                                    InternalUtil.putData(TOTAL_LENGTH_KEY, length);
                                    if (!InternalUtil.hasSpace(length)) {
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
                                    InternalUtil.remove(TOTAL_LENGTH_KEY);
                                    onDownloadSuccess(DOWNLOAD_CALLBACK);
                                } catch (Exception e) {
                                    SLog.d(TAG, "onFailure: with full request", e);
                                    onDownloadFailure(DOWNLOAD_CALLBACK, OkDownloadCallback.ERROR_FILE);
                                } finally {
                                    InternalUtil.close(ins, raf);
                                }
                            }
                        }
                    } else {
                        SLog.d(TAG, "onFailure: with full request");
                        onDownloadFailure(DOWNLOAD_CALLBACK, OkDownloadCallback.ERROR_IO);
                    }
                }
            });
        } else {
            final long DOWNLOAD_LENGTH = new File(SAVE_PATH).length();
            if (!InternalUtil.hasSpace(CONTENT_LENGTH - DOWNLOAD_LENGTH)) {
                onDownloadFailure(DOWNLOAD_CALLBACK, OkDownloadCallback.ERROR_STORAGE);
                return;
            }
            Headers headers = Headers.of("RANGE", "bytes=" + DOWNLOAD_LENGTH + "-" + CONTENT_LENGTH);
            Call call = newCall(mOkRequestProvider.requestGet(url, null, headers));
            call.enqueue(new Callback() {
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
                        if (!"bytes".equals(response.header("Access-Ranges"))) {
                            downloadFile(url, SAVE_PATH, DOWNLOAD_CALLBACK);
                            InternalUtil.remove(TOTAL_LENGTH_KEY);
                            return;
                        }
                        ResponseBody body = response.body();
                        if (body != null) {
                            if (InternalUtil.createFile(SAVE_PATH)) {
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
                                    InternalUtil.remove(TOTAL_LENGTH_KEY);
                                    onDownloadSuccess(DOWNLOAD_CALLBACK);
                                } catch (Exception e) {
                                    SLog.d(TAG, "onFailure: with part request", e);
                                    onDownloadFailure(DOWNLOAD_CALLBACK, OkDownloadCallback.ERROR_FILE);
                                } finally {
                                    InternalUtil.close(ins, raf);
                                }
                            }
                        }
                    } else {
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

    private void doCall(Call call, final String url, final Set<OkCallback> callbackSet) {
        if (call.isCanceled()) {
            SLog.d(TAG, "doCall: isCanceled url = " + url);
            mRunningCallMap.remove(url);
            mRunningCallbackMap.remove(url);
            return;
        }
        if (call.isExecuted()) {
            SLog.d(TAG, "doCall: isExecuted url = " + url);
            return;
        }
        mRunningCallMap.put(url, call);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: stackTrace = " + Log.getStackTraceString(e));
                mRunningCallMap.remove(url);
                mRunningCallbackMap.remove(url);
                for (OkCallback callback : callbackSet) {
                    callback.onFail();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mRunningCallMap.remove(url);
                mRunningCallbackMap.remove(url);
                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse: resCode = " + response.code());
                    dispatchErrCode(callbackSet, response.code());
                    return;
                }
                ResponseBody body = response.body();
                if (body != null) {
                    try {
                        dispatchContent(callbackSet, body.string());
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: stackTrace = " + Log.getStackTraceString(e));
                    }
                }
            }

            private void dispatchContent(Set<OkCallback> callbackSet, String content) {
                for (OkCallback callback : callbackSet) {
                    callback.handleContent(content);
                }
            }

            private void dispatchErrCode(Set<OkCallback> callbackSet, int code) {
                for (OkCallback callback : callbackSet) {
                    callback.handleErrCode(code);
                }
            }
        });
    }

    public void getMethod(final String url, final OkCallback callback) {
        Call call = mRunningCallMap.get(url);
        if (call == null) {
            call = newCall(mOkRequestProvider.requestGet(url, null, null));
        }
        Set<OkCallback> set = addAndGetCallback(url, callback);
        doCall(call, url, set);
    }

    public void getMethod(String url, Headers headers, OkCallback callback) {
        Call call = mRunningCallMap.get(url);
        if (call == null) {
            call = newCall(mOkRequestProvider.requestGet(url, null, headers));
        }
        Set<OkCallback> set = addAndGetCallback(url, callback);
        doCall(call, url, set);
    }

    public void getMethod(String url, Map<String, String> params, OkCallback callback) {
        Call call = mRunningCallMap.get(url);
        if (call == null) {
            call = newCall(mOkRequestProvider.requestGet(url, params, null));
        }
        Set<OkCallback> set = addAndGetCallback(url, callback);
        doCall(call, url, set);
    }

    public void getMethod(String url, Map<String, String> params, Headers headers, OkCallback callback) {
        Call call = mRunningCallMap.get(url);
        if (call == null) {
            call = newCall(mOkRequestProvider.requestGet(url, params, headers));
        }
        Set<OkCallback> set = addAndGetCallback(url, callback);
        doCall(call, url, set);
    }

    public void postMethod(String url, OkCallback callback) {
        Call call = mRunningCallMap.get(url);
        if (call == null) {
            call = newCall(mOkRequestProvider.requestPost(url, null, null));
        }
        Set<OkCallback> set = addAndGetCallback(url, callback);
        doCall(call, url, set);
    }

    public void postMethod(String url, Headers headers, OkCallback callback) {
        Call call = mRunningCallMap.get(url);
        if (call == null) {
            call = newCall(mOkRequestProvider.requestPost(url, null, headers));
        }
        Set<OkCallback> set = addAndGetCallback(url, callback);
        doCall(call, url, set);
    }

    public void postMethod(String url, Map<String, String> params, OkCallback callback) {
        Call call = mRunningCallMap.get(url);
        if (call == null) {
            call = newCall(mOkRequestProvider.requestPost(url, params, null));
        }
        Set<OkCallback> set = addAndGetCallback(url, callback);
        doCall(call, url, set);
    }

    public void postMethod(String url, Map<String, String> params, Headers headers, OkCallback callback) {
        Call call = mRunningCallMap.get(url);
        if (call == null) {
            call = newCall(mOkRequestProvider.requestPost(url, params, headers));
        }
        Set<OkCallback> set = addAndGetCallback(url, callback);
        doCall(call, url, set);
    }

    public void postMethod(String url, String json, OkCallback callback) {
        Call call = mRunningCallMap.get(url);
        if (call == null) {
            call = newCall(mOkRequestProvider.requestJsonPost(url, json, null));
        }
        Set<OkCallback> set = addAndGetCallback(url, callback);
        doCall(call, url, set);
    }

    public void postMethod(String url, String json, Headers headers, OkCallback callback) {
        Call call = mRunningCallMap.get(url);
        if (call == null) {
            call = newCall(mOkRequestProvider.requestJsonPost(url, json, headers));
        }
        Set<OkCallback> set = addAndGetCallback(url, callback);
        doCall(call, url, set);
    }

    public void postMethod(String url, Object jsonObj, OkCallback callback) {
        Call call = mRunningCallMap.get(url);
        if (call == null) {
            call = newCall(mOkRequestProvider.requestJsonPost(url, jsonObj, null));
        }
        Set<OkCallback> set = addAndGetCallback(url, callback);
        doCall(call, url, set);
    }

    public void postMethod(String url, Object jsonObj, Headers headers, OkCallback callback) {
        Call call = mRunningCallMap.get(url);
        if (call == null) {
            call = newCall(mOkRequestProvider.requestJsonPost(url, jsonObj, headers));
        }
        Set<OkCallback> set = addAndGetCallback(url, callback);
        doCall(call, url, set);
    }

    private Call newCall(Request request) {
        return mOkClient.newCall(request);
    }

    private Set<OkCallback> addAndGetCallback(String url, OkCallback callback) {
        Set<OkCallback> set = mRunningCallbackMap.get(url);
        if (set == null) {
            set = InternalUtil.createConcurrentSet();
            mRunningCallbackMap.put(url, set);
        }
        set.add(callback);
        return set;
    }
}
