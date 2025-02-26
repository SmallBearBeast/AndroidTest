package com.example.administrator.androidtest.widget.pdfview.pdfapi.cache

import android.graphics.Bitmap
import com.example.administrator.androidtest.widget.pdfview.pdfapi.isValid
import java.util.concurrent.ConcurrentHashMap

class BitmapMemCache : IBitmapCache {
    private val bitmapCache = ConcurrentHashMap<String, Bitmap>()

    override fun get(key: String): Bitmap? {
        return bitmapCache[key].takeIf { it.isValid() }
    }

    override fun put(key: String, value: Bitmap) {
        if (value.isValid()) {
            bitmapCache[key] = value
        }
    }

    override fun remove(key: String): Bitmap? {
        return bitmapCache.remove(key).takeIf { it.isValid() }
    }

    override fun clear() {
        bitmapCache.clear()
    }
}