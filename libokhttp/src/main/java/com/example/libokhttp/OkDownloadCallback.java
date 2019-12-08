package com.example.libokhttp;

public abstract class OkDownloadCallback {
    public static final int ERROR_CANCEL = 1;
    public static final int ERROR_STORAGE = 2;
    public static final int ERROR_FILE = 3;
    public static final int ERROR_TIME_OUT = 4;
    public static final int ERROR_IO = 5;
    public void onSuccess(){};

    public void onFailure(int errorCode){};

    public void onProgress(int progress){};
}
