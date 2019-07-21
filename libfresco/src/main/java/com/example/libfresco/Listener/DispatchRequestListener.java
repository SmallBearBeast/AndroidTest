package com.example.libfresco.Listener;

import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.request.ImageRequest;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DispatchRequestListener implements RequestListener {
    private Set<RequestListener> mListenerSet = new HashSet<>();

    public void addListener(RequestListener listener){
        mListenerSet.add(listener);
    }

    @Override
    public void onRequestStart(ImageRequest request, Object callerContext, String requestId, boolean isPrefetch) {
        for (RequestListener listener : mListenerSet) {
            listener.onRequestStart(request, callerContext, requestId, isPrefetch);
        }
    }

    @Override
    public void onRequestSuccess(ImageRequest request, String requestId, boolean isPrefetch) {
        for (RequestListener listener : mListenerSet) {
            listener.onRequestSuccess(request, requestId, isPrefetch);
        }
    }

    @Override
    public void onRequestFailure(ImageRequest request, String requestId, Throwable throwable, boolean isPrefetch) {
        for (RequestListener listener : mListenerSet) {
            listener.onRequestFailure(request, requestId, throwable, isPrefetch);
        }
    }

    @Override
    public void onRequestCancellation(String requestId) {
        for (RequestListener listener : mListenerSet) {
            listener.onRequestCancellation(requestId);
        }
    }

    @Override
    public void onProducerStart(String requestId, String producerName) {
        for (RequestListener listener : mListenerSet) {
            listener.onProducerStart(requestId, producerName);
        }
    }

    @Override
    public void onProducerEvent(String requestId, String producerName, String eventName) {
        for (RequestListener listener : mListenerSet) {
            listener.onProducerEvent(requestId, producerName, eventName);
        }
    }

    @Override
    public void onProducerFinishWithSuccess(String requestId, String producerName,  Map<String, String> extraMap) {
        for (RequestListener listener : mListenerSet) {
            listener.onProducerFinishWithSuccess(requestId, producerName, extraMap);
        }
    }

    @Override
    public void onProducerFinishWithFailure(String requestId, String producerName, Throwable t,  Map<String, String> extraMap) {
        for (RequestListener listener : mListenerSet) {
            listener.onProducerFinishWithFailure(requestId, producerName, t, extraMap);
        }
    }

    @Override
    public void onProducerFinishWithCancellation(String requestId, String producerName,  Map<String, String> extraMap) {
        for (RequestListener listener : mListenerSet) {
            listener.onProducerFinishWithCancellation(requestId, producerName, extraMap);
        }
    }

    @Override
    public void onUltimateProducerReached(String requestId, String producerName, boolean successful) {
        for (RequestListener listener : mListenerSet) {
            listener.onUltimateProducerReached(requestId, producerName, successful);
        }
    }

    @Override
    public boolean requiresExtraMap(String requestId) {
        for (RequestListener listener : mListenerSet) {
            if(listener.requiresExtraMap(requestId)){
                return true;
            }
        }
        return false;
    }
}
