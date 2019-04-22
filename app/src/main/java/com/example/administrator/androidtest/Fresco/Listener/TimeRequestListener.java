package com.example.administrator.androidtest.Fresco.Listener;

import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.producers.*;
import com.facebook.imagepipeline.request.ImageRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeRequestListener implements RequestListener {
    private static final String TAG = "TimeRequestListener";
    private static final String REQUEST_TIME = "request_time";
    private static final String ENTER = "\r\n";
    private static final String DIVIDER = ": ";

    private Uri mUri;

    private List<Pair<String, Long[]>> mTimeList = new ArrayList<>();
    private Map<String, Long> mStartMap = new HashMap<>();
    private Map<String, Long> mEndMap = new HashMap<>();
    private StringBuilder mLogBuilder = new StringBuilder();

    @Override
    public void onRequestStart(ImageRequest request, Object callerContext, String requestId, boolean isPrefetch) {
        if(mUri != null) {
            mUri = request.getSourceUri();
        }
        mTimeList.add(new Pair<>(REQUEST_TIME, new Long[1]));
        mStartMap.put(REQUEST_TIME, SystemClock.elapsedRealtime());
    }

    @Override
    public void onRequestSuccess(ImageRequest request, String requestId, boolean isPrefetch) {
        mLogBuilder.append(" ").append(ENTER);
        mLogBuilder.append("uri").append(DIVIDER).append(mUri).append(ENTER);
        mEndMap.put(REQUEST_TIME, SystemClock.elapsedRealtime());
        for (Pair<String, Long[]> pair : mTimeList) {
            long startTime = mStartMap.get(pair.first);
            long endTime = mEndMap.get(pair.first);
            pair.second[0] = endTime - startTime;
            mLogBuilder.append(pair.first).append(DIVIDER).append(pair.second[0]).append(ENTER);
        }
        Log.i(TAG, "onRequestSuccess: " + mLogBuilder);
    }

    @Override
    public void onRequestFailure(ImageRequest request, String requestId, Throwable throwable, boolean isPrefetch) {

    }

    @Override
    public void onRequestCancellation(String requestId) {

    }

    @Override
    public void onProducerStart(String requestId, String producerName) {
        if(isLogProducerName(producerName)){
            mTimeList.add(new Pair<>(producerName, new Long[1]));
            mStartMap.put(producerName, SystemClock.elapsedRealtime());
        }
    }

    @Override
    public void onProducerEvent(String requestId, String producerName, String eventName) {

    }

    @Override
    public void onProducerFinishWithSuccess(String requestId, String producerName, Map<String, String> extraMap) {
        if(isLogProducerName(producerName)){
            mEndMap.put(producerName, SystemClock.elapsedRealtime());
        }
    }

    @Override
    public void onProducerFinishWithFailure(String requestId, String producerName, Throwable t, Map<String, String> extraMap) {

    }

    @Override
    public void onProducerFinishWithCancellation(String requestId, String producerName, Map<String, String> extraMap) {

    }

    @Override
    public void onUltimateProducerReached(String requestId, String producerName, boolean successful) {

    }

    @Override
    public boolean requiresExtraMap(String requestId) {
        return true;
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

    public long getProducerTime(String timeType){
        Long time = null;
        for (int i = 0, len = mTimeList.size(); i < len; i++) {
            Pair<String, Long[]> pair = mTimeList.get(i);
            if(timeType.equals(pair.first)){
                time = pair.second[0];
                break;
            }
        }
        return time == null ? 0 : time;
    }
}
