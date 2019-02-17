package com.example.administrator.androidtest.Net.Okhttp;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHelper {

    private static final int TIME_OUT = 10;
    private static volatile OkHelper sOkHelper;
    private static volatile OkHttpClient sOkClient;
    private static volatile OkRequsetProvider sOkRequestProvider;

    /**
     * 请在Application初始化
     */
    public static void init(){
        sOkHelper = new OkHelper();
        sOkRequestProvider = new OkRequsetProvider();
        sOkClient = initOkHttpClient();
    }

    private static OkHttpClient initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS);
        initInterceptor(builder);
        initNetworkInterceptor(builder);
        initSSLSocket(builder);
        return builder.build();
    }

    private static void initSSLSocket(OkHttpClient.Builder builder) {

    }

    private static void initNetworkInterceptor(OkHttpClient.Builder builder) {

    }

    private static void initInterceptor(OkHttpClient.Builder builder) {

    }

    public static OkHelper getInstance() {
        return sOkHelper;
    }

    public void get(String url, OkCallback callback){
        newCall(sOkRequestProvider.requestGet(url)).enqueue(callback);
    }

    public void get(String url, Map<String, String> params, OkCallback callback){
        newCall(sOkRequestProvider.requestParamGet(url, params)).enqueue(callback);
    }

    public void post(String url, OkCallback callback){
        newCall(sOkRequestProvider.requestPost(url)).enqueue(callback);
    }

    public void post(String url, Map<String, String> params, OkCallback callback){
        newCall(sOkRequestProvider.requestFormPost(url, params)).enqueue(callback);
    }

    public void post(String url, String json, OkCallback callback){
        newCall(sOkRequestProvider.requestJsonPost(url, json)).enqueue(callback);
    }

    public void post(String url, Object jsonObj, OkCallback callback){
        newCall(sOkRequestProvider.requestJsonPost(url, jsonObj)).enqueue(callback);
    }

    public Call newCall(Request request){
        return sOkClient.newCall(request);
    }
}
