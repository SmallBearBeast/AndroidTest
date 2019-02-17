package com.example.administrator.androidtest.Net.Okhttp;
import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkLogInterceptor implements Interceptor {
    private static final String TAG = "OkLogInterceptor";
    private static final String LOG_DIVIDER = " --> ";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logRequest(request);
        Response response = chain.proceed(request);
        logResponse(response);
        return response;
    }

    private void logResponse(Response response) throws IOException{
        StringBuilder builder = new StringBuilder().append("\n");
        builder.append("protocol").append(LOG_DIVIDER).append(response.protocol()).append("\n");
        builder.append("code").append(LOG_DIVIDER).append(response.code()).append("\n");
        builder.append("message").append(LOG_DIVIDER).append(response.message()).append("\n");
        builder.append("headers").append(LOG_DIVIDER).append("\n").append(response.headers());
        ResponseBody body = response.body();
        if(body != null){
            builder.append("body").append(LOG_DIVIDER).append(body.string()).append("\n");
        }
        builder.append("sentRequestAtMillis").append(LOG_DIVIDER).append(response.sentRequestAtMillis()).append("\n");
        builder.append("receivedResponseAtMillis").append(LOG_DIVIDER).append(response.receivedResponseAtMillis()).append("\n");
        String responseInfo = builder.toString();
        Log.d(TAG, responseInfo);

    }

    private void logRequest(Request request) throws IOException{
        StringBuilder builder = new StringBuilder().append("\n");
        builder.append("url").append(LOG_DIVIDER).append(request.url()).append("\n");
        builder.append("method").append(LOG_DIVIDER).append(request.method()).append("\n");
        builder.append("headers").append(LOG_DIVIDER).append("\n").append(request.headers());
        logRequestBody(builder, request.body());
        String requestInfo = builder.toString();
        Log.d(TAG, requestInfo);
    }

    private void logFormBody(FormBody formBody, StringBuilder builder){
        builder.append("formBody").append(LOG_DIVIDER).append("\n");
        builder.append("contentLength").append(LOG_DIVIDER).append(formBody.contentLength()).append("\n");
        builder.append("contentType").append(LOG_DIVIDER).append(formBody.contentType()).append("\n");
        builder.append("content").append(LOG_DIVIDER).append("\n");
        int size = formBody.size();
        for (int i = 0; i < size; i++) {
            builder.append(formBody.encodedName(i) + " : ").append(formBody.encodedValue(i)).append("\n");
        }
    }

    private void logMultipartBody(StringBuilder builder, MultipartBody multipartBody) throws IOException{
        builder.append("multipartBody").append(LOG_DIVIDER).append("\n");
        builder.append("contentLength").append(LOG_DIVIDER).append(multipartBody.contentLength()).append("\n");
        builder.append("contentType").append(LOG_DIVIDER).append(multipartBody.contentType()).append("\n");
        builder.append("content").append(LOG_DIVIDER).append("\n");
        int size = multipartBody.size();
        for (int i = 0; i < size; i++) {
            builder.append("headers").append(LOG_DIVIDER).append("\n").append(multipartBody.part(i).headers());
            builder.append("requestBody").append(LOG_DIVIDER).append("\n");
            logRequestBody(builder, multipartBody.part(i).body());
        }
    }

    private void logRequestBody(StringBuilder builder, RequestBody body) throws IOException{
        if(body instanceof FormBody){
            FormBody formBody = (FormBody) body;
            logFormBody(formBody, builder);
        }
        else if(body instanceof MultipartBody){
            MultipartBody multipartBody = (MultipartBody) body;
            logMultipartBody(builder, multipartBody);
        }else if(body != null){
            builder.append("contentLength").append(LOG_DIVIDER).append(body.contentLength()).append("\n");
            builder.append("contentType").append(LOG_DIVIDER).append(body.contentType()).append("\n");
        }
    }
}
