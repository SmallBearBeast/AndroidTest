package com.example.administrator.androidtest.Widget.pdfview;

import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;

public class PdfBitmapPool {

    private final BitmapPool bitmapPool;

    public PdfBitmapPool() {
        long bitmapPoolSize = 100 * 1024 * 1024;
        bitmapPool = new LruBitmapPool(bitmapPoolSize);
    }

    public Bitmap get(int width, int height) {
        return bitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
    }

    public void put(Bitmap bitmap) {
        bitmapPool.put(bitmap);
    }

    public void clear() {
        bitmapPool.clearMemory();
    }
}
