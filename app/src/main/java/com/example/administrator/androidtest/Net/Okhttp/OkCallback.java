package com.example.administrator.androidtest.Net.Okhttp;

import com.example.administrator.androidtest.Common.Util.Core.GsonUtil;
import com.example.administrator.androidtest.Common.Util.Core.MainThreadUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class OkCallback<T> implements okhttp3.Callback {

    private Class<T> mDataClz;

    public OkCallback(Class<T> dataClz){
        mDataClz = dataClz;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        onFail();
        e.printStackTrace();
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if(!response.isSuccessful()){
            handleErrCode(response.code());
            return;
        }
        final T data = GsonUtil.toObj(response.body().toString(), mDataClz);
        MainThreadUtil.run(new Runnable() {
            @Override
            public void run() {
                onSuccess(data);
            }
        });
    }

    protected void handleErrCode(int errCode) {}


    protected void onSuccess(T data){}

    protected void onFail(){}
}
