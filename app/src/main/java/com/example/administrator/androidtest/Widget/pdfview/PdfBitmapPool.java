package com.example.administrator.androidtest.Widget.pdfview;

import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.example.liblog.SLog;

public class PdfBitmapPool {

    private static final String TAG = "PdfBitmapPool";
    private final BitmapPool bitmapPool;

    public PdfBitmapPool() {
        long bitmapPoolSize = 20 * 1024 * 1024; // 20MB
        bitmapPool = new LruBitmapPool(bitmapPoolSize);
    }

    public Bitmap get(int width, int height) {
        return bitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
    }

    public void put(Bitmap bitmap) {
        bitmapPool.put(bitmap);
    }

    public void clear() {
        try {
            SLog.d(TAG, "clear from PdfBitmapPool: currentSize = " + ((LruBitmapPool)bitmapPool).getCurrentSize());
            bitmapPool.clearMemory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
