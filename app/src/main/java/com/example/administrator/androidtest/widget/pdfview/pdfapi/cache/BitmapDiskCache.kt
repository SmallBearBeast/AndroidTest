package com.example.administrator.androidtest.widget.pdfview.pdfapi.cache

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.bear.liblog.SLog
import com.jakewharton.disklrucache.DiskLruCache
import java.io.File

class BitmapDiskCache(file: File) : IBitmapCache {

    private val diskLruCache = DiskLruCache.open(file, 1, 1, MAX_SIZE)

    override fun get(key: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            diskLruCache.get(key)?.apply {
                bitmap = BitmapFactory.decodeStream(getInputStream(0))
            }
        } catch (e: Exception) {
            SLog.e(TAG, "get: key = $key, exception message: ${e.message}")
        }
        return bitmap
    }

    override fun put(key: String, value: Bitmap) {
        var editor: DiskLruCache.Editor? = null
        try {
            editor = diskLruCache.edit(key)
            value.compress(Bitmap.CompressFormat.JPEG, 100, editor.newOutputStream(0));
            editor.commit()
            diskLruCache.flush()
        } catch (e: Exception) {
            SLog.e(TAG, "put: key = $key, exception message: ${e.message}")
            editor?.abort()
        }
    }

    override fun remove(key: String): Bitmap? {
        val bitmap = get(key)
        try {
            diskLruCache.remove(key)
        } catch (e: Exception) {
            SLog.e(TAG, "remove: key = $key, exception message: ${e.message}")
        }
        return bitmap
    }

    override fun clear() {
        try {
            diskLruCache.delete()
        } catch (e: Exception) {
            SLog.e(TAG, "clear: exception message: ${e.message}")
        }
    }

    companion object {
        private const val TAG = "BitmapDiskCache"
        private const val MAX_SIZE = 1024 * 1024 * 100L // 100MB
    }
}