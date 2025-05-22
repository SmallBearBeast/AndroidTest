package com.example.administrator.androidtest.widget.pdfview.pdfapi.pool

import android.graphics.Bitmap
import com.bear.liblog.SLog
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool

class PdfBitmapPool {

    private var bitmapConfig = Bitmap.Config.RGB_565
    private val bitmapPool = LruBitmapPool(POOL_SIZE) as BitmapPool

    fun get(width: Int, height: Int): Bitmap {
        return bitmapPool[width, height, bitmapConfig]
    }

    fun put(bitmap: Bitmap?) {
        bitmapPool.put(bitmap)
    }

    fun clear() {
        try {
            bitmapPool.clearMemory()
        } catch (e: Exception) {
            SLog.d(TAG, "clear: exception message: ${e.message}")
        }
    }

    fun setBitmapConfig(config: Bitmap.Config) {
        bitmapConfig = config
    }

    companion object {
        private const val TAG = "PdfBitmapPool"
        private const val POOL_SIZE = 20 * 1024 * 1024L // 20MB
    }
}
