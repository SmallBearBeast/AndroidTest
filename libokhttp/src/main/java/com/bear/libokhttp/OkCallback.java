package com.bear.libokhttp;

import com.google.gson.reflect.TypeToken;

public class OkCallback<T> {
    private TypeToken<T> mTypeToken;

    public OkCallback(TypeToken<T> typeToken) {
        mTypeToken = typeToken;
    }

    protected void handleErrCode(int errCode) {

    }

    void handleContent(String content) {
        T data = InternalUtil.toObj(content, mTypeToken);
        onSuccess(data);
    }

    protected void onSuccess(T data) {

    }

    protected void onFail() {

    }
}
