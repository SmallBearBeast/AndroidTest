package com.example.administrator.androidtest.widget.pdfview.pdfapi.cache

import android.graphics.Bitmap
import android.util.LruCache
import com.example.administrator.androidtest.widget.pdfview.pdfapi.isValid
import com.example.administrator.androidtest.widget.pdfview.pdfapi.size
import com.bear.liblog.SLog

class BitmapLruCache: IBitmapCache {
    private var isInClearing = false
    private var onBitmapRecycled: ((String, Bitmap) -> Unit)? = null

    private val lruCache = object : LruCache<String, Bitmap>(MAX_SIZE) {
        override fun sizeOf(key: String, value: Bitmap): Int {
            return value.size()
        }

        // newValue有可能为null
        override fun entryRemoved(evicted: Boolean, key: String, oldValue: Bitmap, newValue: Bitmap?) {
            if (evicted && !isInClearing) {
                onBitmapRecycled?.invoke(key, oldValue)
            }
        }
    }

    override fun get(key: String): Bitmap? {
        return lruCache.get(key).takeIf { it.isValid() }
    }

    override fun put(key: String, value: Bitmap) {
        if (value.isValid()) {
            lruCache.put(key, value)
        }
    }

    override fun remove(key: String): Bitmap? {
        return lruCache.remove(key).takeIf { it.isValid() }
    }

    override fun clear() {
        try {
            SLog.d(TAG, "clear: size = " + lruCache.size())
            isInClearing = true
            lruCache.evictAll()
            onBitmapRecycled = null
        } catch (e: Exception) {
            SLog.d(TAG, "clear: exception message: ${e.message}")
        } finally {
            isInClearing = false
        }
    }

    fun setBitmapRecycled(bitmapRecycled: ((String, Bitmap) -> Unit)?) {
        onBitmapRecycled = bitmapRecycled
    }

    companion object {
        private const val TAG = "BitmapLruCache"
        private const val MAX_SIZE = 50 * 1024 * 1024 // 50MB
    }
}
