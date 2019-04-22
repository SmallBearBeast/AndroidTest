package com.example.administrator.androidtest.Fresco.Listener;

import android.graphics.drawable.Animatable;
import com.facebook.drawee.backends.pipeline.info.ImageOriginListener;
import com.facebook.drawee.controller.ControllerListener;

import java.util.HashSet;
import java.util.Set;

public class DispatchControllListener implements ControllerListener {
    private Set<ControllerListener> mListenerSet = new HashSet<>();

    public void addListener(ControllerListener listener){
        mListenerSet.add(listener);
    }

    @Override
    public void onSubmit(String id, Object callerContext) {
        for (ControllerListener listener : mListenerSet) {
            listener.onSubmit(id, callerContext);
        }
    }

    @Override
    public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
        for (ControllerListener listener : mListenerSet) {
            listener.onFinalImageSet(id, imageInfo, animatable);
        }
    }

    @Override
    public void onIntermediateImageSet(String id, Object imageInfo) {
        for (ControllerListener listener : mListenerSet) {
            listener.onIntermediateImageSet(id, imageInfo);
        }
    }

    @Override
    public void onIntermediateImageFailed(String id, Throwable throwable) {
        for (ControllerListener listener : mListenerSet) {
            listener.onIntermediateImageFailed(id, throwable);
        }
    }

    @Override
    public void onFailure(String id, Throwable throwable) {
        for (ControllerListener listener : mListenerSet) {
            listener.onFailure(id, throwable);
        }
    }

    @Override
    public void onRelease(String id) {
        for (ControllerListener listener : mListenerSet) {
            listener.onRelease(id);
        }
    }
}
