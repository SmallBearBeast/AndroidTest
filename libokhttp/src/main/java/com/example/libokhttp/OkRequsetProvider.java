package com.example.libokhttp;


import okhttp3.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 构建OKHttp的Request
 */
public class OkRequsetProvider {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType FILE = MediaType.parse("application/octet-stream");

    /**
     * get请求
     */
    public Request requestGet(String url, Map<String, String> params, Headers headers) {
        StringBuilder paramBuilder = null;
        if(!isEmpty(params)){
            paramBuilder = new StringBuilder(url).append("?");
            for (Map.Entry<String, String> entry : params.entrySet()){
                paramBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            paramBuilder.setLength(paramBuilder.length() - 1);
        }
        Request.Builder builder = new Request.Builder().url(paramBuilder == null ? url : paramBuilder.toString());
        if(headers != null){
            builder.headers(headers);
        }
        return builder.build();
    }
    /**get请求**/
    
    /**
     * post请求
     */
    public Request requestPost(String url, Map<String, String> params, Headers headers){
        FormBody.Builder formBuilder = new FormBody.Builder();
        if(!isEmpty(params)){
            for (Map.Entry<String, String> entry : params.entrySet()){
                formBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(formBuilder.build());
        if(headers != null){
            builder.headers(headers);
        }
        return builder.build();
    }
    /**post请求**/

    /**
     * 带有json对象或者json字符串的post
     */
    public Request requestJsonPost(String url, Object jsonObj, Headers headers){
        return requestJsonPost(url, InternalUtil.toJson(jsonObj), headers);
    }

    public Request requestJsonPost(String url, String json, Headers headers){
        RequestBody body = RequestBody.create(JSON, json);
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(body);
        if(headers != null){
            builder.headers(headers);
        }
        return builder.build();
    }
    /**带有json对象或者json字符串的post**/

    /**
     * multipart的post
     */
    public Request requestMultipartPost(String url, Headers headers, Map<String, String> params, final OkUploadCallback UPLOAD_CALLBACK, final File... FILES){
        int length = (params == null ? 0 : params.size()) + FILES.length;
        List<MultipartBody.Part> partArray = new ArrayList<>(length);
        if(params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                partArray.add(MultipartBody.Part.createFormData(entry.getKey(), entry.getValue()));
            }
        }
        long totalLength = 0;
        for (File file : FILES) {
            totalLength = totalLength + file.length();
        }
        final boolean[] UPLOAD_RESULT = new boolean[]{true};
        final int[] UPLOAD_PROGRESS = new int[]{0};
        for (int i = 0; i < FILES.length; i++) {
            final int INDEX = i;
            partArray.add(MultipartBody.Part.create(new FileUploadRequestBody(
                    "application/octet-stream", FILES[i], FILES[i].length() * 1f / totalLength, new OkUploadCallback() {
                @Override
                public void onProgress(int progress) {
                    if(UPLOAD_CALLBACK != null) {
                        UPLOAD_PROGRESS[0] = UPLOAD_PROGRESS[0] + progress;
                        UPLOAD_CALLBACK.onProgress(UPLOAD_PROGRESS[0]);
                    }
                }

                @Override
                public void onSuccess() {
                    if(UPLOAD_CALLBACK != null && INDEX == FILES.length - 1){
                        if(UPLOAD_RESULT[0]){
                            UPLOAD_CALLBACK.onSuccess();
                        }else {
                            UPLOAD_CALLBACK.onFailure(OkUploadCallback.ERROR_PART_FAIL);
                        }
                    }
                }

                @Override
                public void onFailure(int errCode) {
                    UPLOAD_RESULT[0] = false;
                    if(UPLOAD_CALLBACK != null && INDEX == FILES.length - 1){
                        UPLOAD_CALLBACK.onFailure(errCode);
                    }
                }
            })));
        }
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
        for (MultipartBody.Part part : partArray) {
            multipartBuilder.addPart(part);
        }
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(multipartBuilder.build());
        if(headers != null){
            builder.headers(headers);
        }
        return builder.build();
    }

    private boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }
}
