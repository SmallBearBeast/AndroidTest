package com.example.libokhttp;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

import java.io.File;
import java.io.IOException;

public class FileUploadRequestBody extends RequestBody {
    private static final int SEGMENT_SIZE = 1024;
    private String mContentType;
    private File mFile;
    private float mWeight;
    private OkUploadCallback mCallback;

    public FileUploadRequestBody(String contentType, File file) {
        this(contentType, file, 1, null);
    }

    public FileUploadRequestBody(String contentType, File file, OkUploadCallback callback) {
        this(contentType, file, 1, callback);
    }

    public FileUploadRequestBody(String contentType, File file, float weight, OkUploadCallback callback) {
        mContentType = contentType;
        mFile = file;
        mCallback = callback;
        mWeight = weight;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse(mContentType);
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        Source source = null;
        try {
            source = Okio.source(mFile);
            long uploading = 0;
            long read;
            while ((read = source.read(sink.buffer(), SEGMENT_SIZE)) != -1){
                uploading = uploading + read;
                sink.flush();
                if(mCallback != null) {
                    mCallback.onProgress((int) (uploading * mWeight / mFile.length() * 100));
                }
            }
            if(mCallback != null) {
                mCallback.onSuccess();
            }
        }catch (Exception e){
            e.printStackTrace();
            if(mCallback != null) {
                mCallback.onFailure(OkUploadCallback.ERROR_PART_FAIL);
            }
        }finally {
            Util.closeQuietly(source);
        }

    }
}
