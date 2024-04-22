package com.example.administrator.androidtest.Widget.pdfview;

import android.graphics.Bitmap;
import android.util.LruCache;

public class PdfBitmapCache extends LruCache<String, Bitmap> {
    private static final int maxSize = 100 * 1024 * 1024;
    public PdfBitmapCache() {
        super(maxSize);
    }
}
