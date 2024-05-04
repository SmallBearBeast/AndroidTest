package com.example.administrator.androidtest.Widget.pdfview;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.example.liblog.SLog;

public class PdfBitmapCache {

    private static final String TAG = "PdfBitmapCache";
    private static final int MAX_SIZE = 50 * 1024 * 1024; // 50MB
    private boolean isInClearing = false;
    private OnBitmapRecyclerCallback recyclerCallback;

    private final LruCache<String, Bitmap> bitmapLruCache = new LruCache<String, Bitmap>(MAX_SIZE) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return getBitmapByteSize(value);
        }

        @Override
        protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
            if (evicted && !isInClearing && recyclerCallback != null) {
                recyclerCallback.onBitmapRecycled(oldValue);
            }
        }
    };

    public void put(String key, Bitmap value) {
        if (!value.isRecycled()) {
            bitmapLruCache.put(key, value);
        }
    }

    public Bitmap remove(String key) {
        return bitmapLruCache.remove(key);
    }

    public void clear() {
        try {
            SLog.d(TAG, "clear from PdfBitmapCache: cacheSize = " + bitmapLruCache.size());
            isInClearing = true;
            bitmapLruCache.evictAll();
            recyclerCallback = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isInClearing = false;
        }
    }

    public static int getBitmapByteSize(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return 0;
        }
        try {
            return bitmap.getAllocationByteCount();
        } catch (Exception e) {
            // Do nothing.
        }
        return bitmap.getHeight() * bitmap.getRowBytes();
    }

    public void setRecyclerCallback(OnBitmapRecyclerCallback callback) {
        recyclerCallback = callback;
    }

    public interface OnBitmapRecyclerCallback {
        void onBitmapRecycled(Bitmap bitmap);
    }

}
