package com.example.administrator.androidtest.okhtttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.androidtest.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpAct extends AppCompatActivity {

    /*
    拦截器是指在方法Request发送前和Response返回前进行拦截，做一些拦截处理操作，然后再请求和返回数据。
    在Okhttp中拦截器会按照添加的顺序依次调用，通过实现Interceptor接口，实现intercept()方法
    通过chain.proceed()方法将拦截请求传给下一个拦截器，直到最后一个拦截器，最后一个拦截器拦截接受到的Response数据后开始返回
    将Response数据逆方向回传给各个拦截器完成各自的拦截功能
     */
    private OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new RequestInterceptor())
            .addInterceptor(new ResponseOneInterceptor())
            .addInterceptor(new ResponseTwoInterceptor())
            .build();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_okhttp);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_get:
                okHttpGet("https://www.baidu.com/");
                break;

            case R.id.bt_post:
                break;
        }
    }


    private void okHttpGet(String url) {
        final Request request = new Request.Builder()
                .url(url)
                .build();

        new Thread() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String body = response.body().string();
                        body = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void okhttpPost(String url, RequestBody body) {
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        new Thread() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String body = response.body().string();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    private FormBody formBody = new FormBody.Builder()
            .add("word","一目十行")
            .add("key", "")
            .build();


    static class  RequestInterceptor implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            return response;
        }
    }



    static class  ResponseOneInterceptor implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            return response;
        }
    }


    static class  ResponseTwoInterceptor implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            return response;
        }
    }
}
