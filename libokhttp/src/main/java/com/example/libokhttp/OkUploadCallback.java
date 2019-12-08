package com.example.libokhttp;

public abstract class OkUploadCallback {
    public static final int ERROR_CANCEL = 1;
    public static final int ERROR_FILE = 2;
    public static final int ERROR_TIME_OUT = 3;
    public static final int ERROR_IO = 4;
    public static final int ERROR_PART_FAIL = 5;
    public static final int FAIL_INDEX_NONE = -1;
    public void onSuccess(){}

    public void onFailure(int errorCode){}

    public void onProgress(int progress){}
}
