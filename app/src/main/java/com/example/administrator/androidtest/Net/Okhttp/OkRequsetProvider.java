package com.example.administrator.androidtest.Net.Okhttp;


import com.example.administrator.androidtest.Common.Util.Core.GsonUtil;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 构建OKHttp的Request
 */
public class OkRequsetProvider {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType FILE = MediaType.parse("application/octet-stream");

    /**
     * 构建带参数的get请求
     */
    public Request requestParamGet(String url, Map<String, String> params) {
        StringBuilder builder = null;
        if(params != null && params.size() != 0){
            builder = new StringBuilder(url).append("?");
            for (Map.Entry<String, String> entry : params.entrySet()){
                builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            builder.setLength(builder.length() - 1);
        }
        return new Request.Builder()
                .url(builder == null ? url : builder.toString())
                .build();
    }
    /**构建带参数的get请求**/

    /**
     * 构建不带参数的get请求
     */
    public Request requestGet(String url){
        return requestParamGet(url, null);
    }
    /**构建不带参数的get请求**/

    /**
     * 构建带参数的post请求
     */
    public Request requestFormPost(String url, Map<String, String> params){
        FormBody.Builder builder = new FormBody.Builder();
        if(params != null && params.size() != 0){
            for (Map.Entry<String, String> entry : params.entrySet()){
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
    }
    /**构建带参数的post请求**/

    /**
     * 构建不带参数的post请求
     */
    public Request requestPost(String url){
        return requestFormPost(url, null);
    }
    /**构建不带参数的post请求**/

    /**
     * 构建上传对象的post请求，转为json
     */
    public Request requestJsonPost(String url, Object jsonObj){
        return requestJsonPost(url, GsonUtil.toJson(jsonObj));
    }
    /**构建上传对象的post请求，转为json**/

    /**
     * 构建上传json的post请求
     */
    public Request requestJsonPost(String url, String json){
        RequestBody body = RequestBody.create(JSON, json);
        return new Request.Builder()
                .url(url)
                .post(body)
                .build();
    }
    /**构建上传json的post请求**/
}
