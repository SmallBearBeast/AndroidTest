package com.example.administrator.androidtest.Fresco;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.util.AttributeSet;
import com.example.administrator.androidtest.Common.Util.File.FileUtil;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.backends.pipeline.info.ImageOriginListener;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.fresco.animation.backend.AnimationBackendDelegate;
import com.facebook.fresco.animation.drawable.AnimatedDrawable2;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;

public class FrescoView extends SimpleDraweeView {

    public FrescoView(Context context) {
        this(context, null);
    }

    public FrescoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setPath(String imagePath) {
        if (FileUtil.isFileExist(imagePath)) {
            Uri uri = Uri.fromFile(new File(imagePath));
            setImageURI(uri);
        }
    }

    public void setImageUri(Uri uri) {
        setImageUri(uri, null, null);
    }

    public void setImageUri(Uri uri, ImageOriginListener imageOriginListener) {
        setImageUri(uri, imageOriginListener, null, null);
    }

    public void setImageUri(Uri uri, ControllerListener controllerListener) {
        setImageUri(uri, null, controllerListener, null);
    }

    public void setImageUri(Uri uri, RequestListener requestListener) {
        setImageUri(uri, null, null, requestListener);
    }

    public void setImageUri(Uri uri, ImageOriginListener imageOriginListener, ControllerListener controllerListener) {
        setImageUri(uri, imageOriginListener, controllerListener, null);
    }

    public void setImageUri(Uri uri, ImageOriginListener imageOriginListener, ControllerListener controllerListener, RequestListener requestListener) {
        ImageRequestBuilder requestBuilder = FrescoUtil.defaultRequestBuilder(uri, -1, -1, requestListener);
        PipelineDraweeControllerBuilder controllerBuilder = FrescoUtil.defaultControllerBuilder(requestBuilder.build(), imageOriginListener, controllerListener);
        setController(controllerBuilder.build());
    }


    public void setImageUri(Uri uri, Uri lowUri, ImageOriginListener imageOriginListener, ControllerListener controllerListener, RequestListener requestListener) {
        ImageRequestBuilder builder = FrescoUtil.defaultRequestBuilder(uri, -1, -1, requestListener);
        PipelineDraweeControllerBuilder controllerBuilder = FrescoUtil.defaultControllerBuilder(builder.build(), imageOriginListener, controllerListener);
        if (lowUri != null) {
            ImageRequestBuilder lowBuilder = FrescoUtil.defaultRequestBuilder(lowUri, -1, -1, null);
            controllerBuilder.setLowResImageRequest(lowBuilder.build());
        }
        setController(controllerBuilder.build());
    }

    /*
        加载asset，content，url，uri直接使用setImageURI
     */

    @SuppressWarnings("unchecked")
    public static class WebpAndGifController extends BaseControllerListener {
        private int mLoopCount;

        public WebpAndGifController(int loopCount) {
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
}
