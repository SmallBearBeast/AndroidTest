package com.example.libfresco.Listener;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.util.Log;
import com.example.libcommon.Util.AppUtil;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;

public class LogControllListener extends BaseControllerListener {
    private static final String TAG = "LogControllListener";
    private static final String ENTER = "\r\n";
    private static final String DIVIDER = ": ";
    private Uri mUri;

    public LogControllListener(ControllerListener listener, Uri uri){
        mUri = uri;
    }

    @Override
    public void onSubmit(String id, Object callerContext) {
        super.onSubmit(id, callerContext);
    }

    @Override
    public void onFinalImageSet(String id,  Object imageInfo,  Animatable animatable) {
        if(AppUtil.isAppDebug()){
            StringBuilder builder = new StringBuilder().append(ENTER);
            builder.append("uri").append(DIVIDER).append(mUri).append(ENTER);
            if (imageInfo instanceof CloseableStaticBitmap) {
                CloseableStaticBitmap closeableStaticBitmap = (CloseableStaticBitmap) imageInfo;
                builder.append("width").append(DIVIDER).append(closeableStaticBitmap.getWidth()).append(ENTER);
                builder.append("height").append(DIVIDER).append(closeableStaticBitmap.getHeight()).append(ENTER);
                builder.append("size").append(DIVIDER).append(closeableStaticBitmap.getSizeInBytes() / 1024f).append(ENTER);
                builder.append("rotationAngle").append(DIVIDER).append(closeableStaticBitmap.getRotationAngle()).append(ENTER);
                builder.append("orientation").append(DIVIDER).append(closeableStaticBitmap.getExifOrientation()).append(ENTER);
            }
            Log.i(TAG, "onFinalImageSet: " + builder.toString());
        }
    }

    @Override
    public void onFailure(String id, Throwable throwable) {
        if(AppUtil.isAppDebug()){
            throwable.printStackTrace();
        }
    }
}
