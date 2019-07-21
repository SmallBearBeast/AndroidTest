package com.example.libokhttp;

import android.support.annotation.NonNull;
import com.example.libbase.Util.GsonUtil;
import com.example.libbase.Util.MainThreadUtil;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

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
    public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
        if(!response.isSuccessful()){
            handleErrCode(response.code());
            return;
        }
        ResponseBody body = response.body();
        if(body != null){
            try {
                String content = body.string();
                final T DATA = GsonUtil.toObj(content, mDataClz);
                MainThreadUtil.run(new Runnable() {
                    @Override
                    public void run() {
                        onSuccess(DATA);
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    protected void handleErrCode(int errCode) {}

    protected void onSuccess(T data){}

    protected void onFail(){}
}
