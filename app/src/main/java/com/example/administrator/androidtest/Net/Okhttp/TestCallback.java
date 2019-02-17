package com.example.administrator.androidtest.Net.Okhttp;

import okhttp3.Response;

public class TestCallback extends OkCallback<String> {
    public TestCallback(Class<String> dataClz) {
        super(dataClz);
    }

    @Override
    public void onSuccess(String data, Response response) {

    }
}
