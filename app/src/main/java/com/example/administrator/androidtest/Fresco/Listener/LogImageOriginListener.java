package com.example.administrator.androidtest.Fresco.Listener;

import android.net.Uri;
import android.util.Log;
import com.example.libbase.Util.AppUtil;
import com.facebook.drawee.backends.pipeline.info.ImageOrigin;
import com.facebook.drawee.backends.pipeline.info.ImageOriginListener;

public class LogImageOriginListener implements ImageOriginListener {
    private static final String TAG = "LogImageOriginListener";
    private static final String ENTER = "\r\n";
    private static final String DIVIDER = ": ";
    private Uri mUri;

    public LogImageOriginListener(Uri uri){
        mUri = uri;
    }

    @Override
    public void onImageLoaded(String controllerId, int imageOrigin, boolean successful) {
        if(AppUtil.isAppDebug() || !successful){
            StringBuilder builder = new StringBuilder().append(ENTER);
            builder.append("uri").append(DIVIDER).append(mUri).append(ENTER);
            builder.append("imageOrigin").append(DIVIDER);
            if(imageOrigin == ImageOrigin.UNKNOWN){
                builder.append("UNKNOWN");
            }else if(imageOrigin == ImageOrigin.NETWORK){
                builder.append("NETWORK");
            }else if(imageOrigin == ImageOrigin.DISK){
                builder.append("DISK");
            }else if(imageOrigin == ImageOrigin.MEMORY_ENCODED){
                builder.append("MEMORY_ENCODED");
            }else if(imageOrigin == ImageOrigin.MEMORY_BITMAP){
                builder.append("MEMORY_BITMAP");
            }else if(imageOrigin == ImageOrigin.LOCAL){
                builder.append("LOCAL");
            }
            builder.append(ENTER);
            builder.append("successful").append(DIVIDER).append(successful).append(ENTER);

            Log.i(TAG, "onImageLoaded: " + builder.toString());
        }
    }
}
