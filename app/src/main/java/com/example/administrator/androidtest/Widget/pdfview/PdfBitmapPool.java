package com.example.administrator.androidtest.Widget.pdfview;

import android.graphics.Bitmap;
import android.util.Log;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;

public class PdfBitmapPool {

    private static final String TAG = "PdfBitmapPool";
    private final BitmapPool bitmapPool;

    public PdfBitmapPool() {
        long bitmapPoolSize = 10 * 1024 * 1024;
        bitmapPool = new LruBitmapPool(bitmapPoolSize);
    }

    public Bitmap get(int width, int height) {
        Log.d(TAG, "get() called with: width = [" + width + "], height = [" + height + "]");
        return bitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
    }

    public void put(Bitmap bitmap) {
        Log.d(TAG, "put() called with: bitmap = [" + bitmap + "]");
        bitmapPool.put(bitmap);
    }

    public void clear() {
        try {
            bitmapPool.clearMemory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
