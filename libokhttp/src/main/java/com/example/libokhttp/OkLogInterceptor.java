package com.example.libokhttp;
import android.util.Log;
import okhttp3.*;

import java.io.IOException;

public class OkLogInterceptor implements Interceptor {
    private static final String TAG = OkConstant.OK_LOG_TAG;
    private static final String LOG_DIVIDER = ": ";
    private static final String TAB = "    ";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logRequest(request);
        Response response = chain.proceed(request);
        logResponse(response);
        return response;
    }

    private void logResponse(Response response) throws IOException{
        StringBuilder builder = new StringBuilder(" ").append("\n");
        builder.append("protocol").append(LOG_DIVIDER).append(response.protocol()).append("\n");
        builder.append("code").append(LOG_DIVIDER).append(response.code()).append("\n");
        builder.append("message").append(LOG_DIVIDER).append(response.message()).append("\n");
        builder.append("headers").append(LOG_DIVIDER).append("\n");
        logHeaders(response.headers(), builder, TAB);
        ResponseBody body = response.body();
        if(body != null){
            builder.append("body").append(LOG_DIVIDER).append("\n");
            builder.append(TAB).append("contentType").append(LOG_DIVIDER).append(body.contentType()).append("\n");
            builder.append(TAB).append("contentLength").append(LOG_DIVIDER).append(body.contentLength()).append("\n");
        }
        builder.append("sentRequestAtMillis").append(LOG_DIVIDER).append(response.sentRequestAtMillis()).append("\n");
        builder.append("receivedResponseAtMillis").append(LOG_DIVIDER).append(response.receivedResponseAtMillis()).append("\n");
        String responseInfo = builder.toString();
        Log.d(TAG, responseInfo);

    }

    private void logRequest(Request request) throws IOException{
        StringBuilder builder = new StringBuilder(" ").append("\n");
        builder.append("url").append(LOG_DIVIDER).append(request.url()).append("\n");
        builder.append("method").append(LOG_DIVIDER).append(request.method()).append("\n");
        builder.append("headers").append(LOG_DIVIDER).append("\n");
        logHeaders(request.headers(), builder, TAB);
        logRequestBody(builder, request.body(), "");
        String requestInfo = builder.toString();
        Log.d(TAG, requestInfo);
    }

    private void logHeaders(Headers headers, StringBuilder builder, String tab){
        if(headers != null) {
            for (int i = 0, size = headers.size(); i < size; i++) {
                builder.append(tab).append(headers.name(i)).append(LOG_DIVIDER).append(headers.value(i)).append("\n");
            }
        }
    }


    private void logFormBody(FormBody formBody, StringBuilder builder, String tab){
        builder.append(tab).append("contentLength").append(LOG_DIVIDER).append(formBody.contentLength()).append("\n");
        builder.append(tab).append("contentType").append(LOG_DIVIDER).append(formBody.contentType()).append("\n");
        builder.append(tab).append("content").append(LOG_DIVIDER).append("\n");
        int size = formBody.size();
        for (int i = 0; i < size; i++) {
            builder.append(tab).append(tab).append(formBody.encodedName(i) + " : ").append(formBody.encodedValue(i)).append("\n");
        }
    }

    private void logMultipartBody(StringBuilder builder, MultipartBody multipartBody, String tab) throws IOException{
        builder.append(tab).append("contentLength").append(LOG_DIVIDER).append(multipartBody.contentLength()).append("\n");
        builder.append(tab).append("contentType").append(LOG_DIVIDER).append(multipartBody.contentType()).append("\n");
        builder.append(tab).append("content").append(LOG_DIVIDER).append("\n");
        int size = multipartBody.size();
        for (int i = 0; i < size; i++) {
            builder.append(tab).append("headers").append(LOG_DIVIDER).append("\n");
            logHeaders(multipartBody.part(i).headers(), builder, tab + TAB);
            logRequestBody(builder, multipartBody.part(i).body(), tab + TAB);
        }
    }

    private void logRequestBody(StringBuilder builder, RequestBody body, String tab) throws IOException{
        if(body instanceof FormBody){
            builder.append(tab).append("formBody").append(LOG_DIVIDER).append("\n");
            FormBody formBody = (FormBody) body;
            logFormBody(formBody, builder, tab + TAB);
        }
        else if(body instanceof MultipartBody){
            builder.append(tab).append("multipartBody").append(LOG_DIVIDER).append("\n");
            MultipartBody multipartBody = (MultipartBody) body;
            logMultipartBody(builder, multipartBody, tab + TAB);
        }else if(body != null){
            builder.append(tab).append("requestBody").append(LOG_DIVIDER).append("\n");
            builder.append("contentLength").append(LOG_DIVIDER).append(body.contentLength()).append("\n");
            builder.append("contentType").append(LOG_DIVIDER).append(body.contentType()).append("\n");
        }
    }
}
