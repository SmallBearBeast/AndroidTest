package com.example.libfresco.Listener;

import com.facebook.drawee.backends.pipeline.info.ImageOriginListener;

import java.util.HashSet;
import java.util.Set;

public class DispatchImageOriginListener implements ImageOriginListener {
    private Set<ImageOriginListener> mListenerSet = new HashSet<>();

    public void addListener(ImageOriginListener listener){
        mListenerSet.add(listener);
    }

    @Override
    public void onImageLoaded(String controllerId, int imageOrigin, boolean successful) {
        for (ImageOriginListener listener : mListenerSet) {
            listener.onImageLoaded(controllerId, imageOrigin, successful);
        }
    }
}
