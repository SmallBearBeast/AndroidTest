package com.example.administrator.androidtest.Fresco.Listener;

import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import com.example.administrator.androidtest.Log.ILog;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.producers.*;
import com.facebook.imagepipeline.request.ImageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogRequestListener implements RequestListener {
    private static final String TAG = "LogRequestListener";
    private static final String ENTER = "\r\n";
    private static final String DIVIDER = ": ";
    private static final String SPACE = " ";

    private Uri mUri;
    private ILog mLog;
    private List<Pair<String, StringBuilder>> mLogList = new ArrayList<>();
    private StringBuilder mLogBuilder = new StringBuilder();

    @Override
    public void onRequestStart(ImageRequest request, Object callerContext, String requestId, boolean isPrefetch) {
        if(mUri == null){
            mUri = request.getSourceUri();
        }
        mLogBuilder.append(SPACE).append(ENTER);
        mLogBuilder.append("onRequestStart").append(DIVIDER);
        mLogBuilder.append("uri").append(DIVIDER).append(mUri).append(ENTER);
    }

    @Override
    public void onRequestSuccess(ImageRequest request, String requestId, boolean isPrefetch) {
        collectLog();
        mLogBuilder.append("onRequestSuccess").append(ENTER);
        Log.i(TAG, mLogBuilder.toString());
    }

    @Override
    public void onRequestFailure(ImageRequest request, String requestId, Throwable throwable, boolean isPrefetch) {
        collectLog();
        mLogBuilder.append("onRequestFailure").append(ENTER);
        Log.i(TAG, mLogBuilder.toString());
    }

    @Override
    public void onRequestCancellation(String requestId) {
        collectLog();
        mLogBuilder.append("onRequestFailure").append(ENTER);
        Log.i(TAG, mLogBuilder.toString());
    }

    @Override
    public void onProducerStart(String requestId, String producerName) {
        StringBuilder builder = getBuilder(producerName);
        if(builder != null){
            builder.append("onProducerStart").append(DIVIDER).append(producerName).append(ENTER);
        }
    }

    @Override
    public void onProducerEvent(String requestId, String producerName, String eventName) {

    }

    @Override
    public void onProducerFinishWithSuccess(String requestId, String producerName, Map<String, String> extraMap) {
        StringBuilder builder = getBuilder(producerName);
        if(builder != null){
            builder.append("onProducerFinishWithSuccess").append(DIVIDER).append(producerName).append(SPACE);
            if(extraMap != null) {
                for (Map.Entry<String, String> entry : extraMap.entrySet()) {
                    builder.append(entry.getKey()).append(DIVIDER).append(entry.getValue()).append(SPACE);
                }
            }
            builder.append(ENTER);
        }
    }

    @Override
    public void onProducerFinishWithFailure(String requestId, String producerName, Throwable t, Map<String, String> extraMap) {
        StringBuilder builder = getBuilder(producerName);
        if(builder != null){
            builder.append("onProducerFinishWithFailure").append(DIVIDER).append(producerName).append(SPACE);
            if(extraMap != null) {
                for (Map.Entry<String, String> entry : extraMap.entrySet()) {
                    builder.append(entry.getKey()).append(DIVIDER).append(entry.getValue()).append(ENTER);
                }
            }
            builder.append(ENTER);
        }
    }

    @Override
    public void onProducerFinishWithCancellation(String requestId, String producerName, Map<String, String> extraMap) {

    }

    @Override
    public void onUltimateProducerReached(String requestId, String producerName, boolean successful) {
        StringBuilder builder = getBuilder(producerName);
        if(builder != null){
            builder.append("onUltimateProducerReached").append(DIVIDER).append(producerName).append(SPACE);
            builder.append("successful").append(DIVIDER).append(successful).append(ENTER);
        }
    }

    @Override
    public boolean requiresExtraMap(String requestId) {
        return true;
    }


    private void collectLog(){
        for (Pair<String, StringBuilder> pair : mLogList) {
            mLogBuilder.append(pair.second);
        }
    }

    private StringBuilder getBuilder(String producerName){
        if(isLogProducerName(producerName)){
            for (Pair<String, StringBuilder> pair : mLogList) {
                if(pair.first.equals(producerName)){
                    return pair.second;
                }
            }
            Pair<String, StringBuilder> pair = new Pair<>(producerName, new StringBuilder());
            mLogList.add(pair);
            return pair.second;
        }
        return null;
    }

    private boolean isLogProducerName(String producerName){
        boolean result = false;
        if(producerName.equals(BitmapMemoryCacheGetProducer.PRODUCER_NAME)){
            result = true;
        }else if(producerName.equals(BitmapMemoryCacheProducer.PRODUCER_NAME)){
            result = true;
        }else if(producerName.equals(EncodedMemoryCacheProducer.PRODUCER_NAME)){
            result = true;
        }else if(producerName.equals(DiskCacheReadProducer.PRODUCER_NAME)){
            result = true;
        }else if(producerName.equals(NetworkFetchProducer.PRODUCER_NAME)){
            result = true;
        }else if(producerName.equals(DecodeProducer.PRODUCER_NAME)){
            result = true;
        }else if(producerName.equals(LocalFileFetchProducer.PRODUCER_NAME)){
            result = true;
        }else if(producerName.equals(LocalResourceFetchProducer.PRODUCER_NAME)){
            result = true;
        }
        return result;
    }
}
