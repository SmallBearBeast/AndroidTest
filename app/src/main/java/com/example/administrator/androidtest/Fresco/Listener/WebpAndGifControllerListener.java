package com.example.administrator.androidtest.Fresco.Listener;

import android.graphics.drawable.Animatable;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.fresco.animation.backend.AnimationBackendDelegate;
import com.facebook.fresco.animation.drawable.AnimatedDrawable2;

@SuppressWarnings("unchecked")
public class WebpAndGifControllerListener extends BaseControllerListener {
    private int mLoopCount;

    public WebpAndGifControllerListener(int loopCount) {
        mLoopCount = loopCount;
    }

    @Override
    public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
        if (animatable instanceof AnimatedDrawable2) {
            AnimatedDrawable2 drawable = (AnimatedDrawable2) animatable;
            drawable.setAnimationBackend(new AnimationBackendDelegate(drawable.getAnimationBackend()) {
                @Override
                public int getLoopCount() {
                    return mLoopCount;
                }
            });
        }
    }
}