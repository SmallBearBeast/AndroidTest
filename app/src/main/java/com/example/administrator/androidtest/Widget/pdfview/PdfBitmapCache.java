package com.example.administrator.androidtest.Widget.pdfview;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

public class PdfBitmapCache {

    private static final String TAG = "PdfBitmapCache";
    private static final int MAX_SIZE = 20 * 1024 * 1024;
    private OnBitmapRecyclerCallback recyclerCallback;

    private final LruCache<String, Bitmap> bitmapLruCache = new LruCache<String, Bitmap>(MAX_SIZE) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return getBitmapByteSize(value);
        }

        @Override
        protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
            if (evicted && recyclerCallback != null) {
                Log.d(TAG, "entryRemoved: evicted = " + evicted + ", key = " + key);
                recyclerCallback.onBitmapRecycled(oldValue);
            }
        }
    };

    public void put(String key, Bitmap value) {
        Log.d(TAG, "put() called with: key = [" + key + "], isRecycled = [" + value.isRecycled());
        if (!value.isRecycled()) {
            bitmapLruCache.put(key, value);
        }
    }

    public Bitmap remove(String key) {
        Log.d(TAG, "remove() called with: key = [" + key + "]");
        return bitmapLruCache.remove(key);
    }

    public void clear() {
        Log.d(TAG, "clear() called");
        try {
            bitmapLruCache.evictAll();
        } catch (Exception e) {
            e.printStackTrace();
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

    public void setRecyclerCallback(OnBitmapRecyclerCallback recyclerCallback) {
        this.recyclerCallback = recyclerCallback;
    }

    public interface OnBitmapRecyclerCallback {
        void onBitmapRecycled(Bitmap bitmap);
    }

}
