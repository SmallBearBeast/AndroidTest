package com.example.administrator.androidtest.widget.pdfview.pdfapi.impl

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.ParcelFileDescriptor
import com.example.administrator.androidtest.widget.pdfview.pdfapi.BitmapSize
import com.example.administrator.androidtest.widget.pdfview.pdfapi.PdfApi
import com.example.administrator.androidtest.widget.pdfview.pdfapi.cache.BitmapDiskCache
import com.example.administrator.androidtest.widget.pdfview.pdfapi.cache.BitmapLruCache
import com.example.administrator.androidtest.widget.pdfview.pdfapi.cache.BitmapMemCache
import com.example.administrator.androidtest.widget.pdfview.pdfapi.cache.IBitmapCache
import com.example.administrator.androidtest.widget.pdfview.pdfapi.getScreenHeight
import com.example.administrator.androidtest.widget.pdfview.pdfapi.getScreenWidth
import com.example.administrator.androidtest.widget.pdfview.pdfapi.isValid
import com.example.administrator.androidtest.widget.pdfview.pdfapi.pool.PdfBitmapPool
import java.io.File

abstract class BasePdfImpl(private val context: Context) : PdfApi {

    private var maxWidth = -1
    private var maxHeight = -1
    private var pageCount = 0
    protected var documentSize = BitmapSize(-1, -1)
    private var bitmapSize = BitmapSize(-1, -1)
    private var cacheTag: String = ""
    private val bitmapPool = PdfBitmapPool()
    private var fd: ParcelFileDescriptor? = null

    private val usedBitmapMemCache = BitmapMemCache() as IBitmapCache
    private val bitmapLruCache = BitmapLruCache() as IBitmapCache
    private val bitmapDiskCache = BitmapDiskCache(getDiskCacheDir(context)) as IBitmapCache

    init {
        maxWidth = context.getScreenWidth()
        maxHeight = context.getScreenHeight()
        documentSize = BitmapSize(maxWidth, maxHeight)
        bitmapSize = BitmapSize(maxWidth, maxHeight)
        (bitmapLruCache as BitmapLruCache).setBitmapRecycled { key, bitmap ->
            usedBitmapMemCache.let {
                if (it.get(key) != bitmap) {
                    bitmapPool.put(bitmap)
                }
            }
        }
    }

    override fun load(uri: Uri, tag: String) {
        cacheTag = tag
        fd = context.contentResolver.openFileDescriptor(uri, "r")?.also {
            loadInner(it)
        }
    }

    override fun load(file: File, tag: String) {
        cacheTag = tag
        fd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)?.also {
            loadInner(it)
        }
    }

    abstract fun loadInner(parcelFileDescriptor: ParcelFileDescriptor)

    override fun getBitmap(pageIndex: Int, size: BitmapSize): Bitmap? {
        val bitmap = getMemoryBitmap(pageIndex, size)
        if (bitmap.isValid()) {
            return bitmap
        }
        return getDiskOrNewBitmap(pageIndex, size)
    }

    override fun getMemoryBitmap(pageIndex: Int, size: BitmapSize): Bitmap? {
        val key = getCacheKey(pageIndex, size)
        val bitmap = usedBitmapMemCache.get(key)
        if (bitmap.isValid()) {
            return bitmap
        }
        // bitmapLruCache需要用remove
        return bitmapLruCache.remove(key).takeIf { it.isValid() }?.also {
            usedBitmapMemCache.put(key, it)
        }
    }

    override fun getDiskBitmap(pageIndex: Int, size: BitmapSize): Bitmap? {
        if (cacheTag.isEmpty()) {
            return null
        }
        val key = getCacheKey(pageIndex, size)
        return bitmapDiskCache.get(key).takeIf { it.isValid() }
    }

    override fun putUsedBitmap(pageIndex: Int, size: BitmapSize, bitmap: Bitmap?) {
        bitmap.takeIf { it.isValid() }?.let {
            val key = getCacheKey(pageIndex, size)
            usedBitmapMemCache.put(key, it)
        }
    }

    override fun putCachedBitmap(pageIndex: Int, size: BitmapSize, bitmap: Bitmap?) {
        bitmap.takeIf { it.isValid() }?.let {
            val key = getCacheKey(pageIndex, size)
            bitmapLruCache.put(key, it)
        }
    }

    override fun putDiskBitmap(pageIndex: Int, size: BitmapSize, bitmap: Bitmap?) {
        // 没有传入cacheTag，不启动磁盘缓存
        if (cacheTag.isEmpty()) {
            return
        }
        bitmap.takeIf { it.isValid() }?.let {
            val key = getCacheKey(pageIndex, size)
            bitmapDiskCache.put(key, it)
        }
    }

    override fun removeUsedBitmap(pageIndex: Int, size: BitmapSize): Bitmap? {
        val key = getCacheKey(pageIndex, size)
        return usedBitmapMemCache.remove(key)
    }

    override fun removeCachedBitmap(pageIndex: Int, size: BitmapSize): Bitmap? {
        val key = getCacheKey(pageIndex, size)
        return bitmapLruCache.remove(key)
    }

    override fun preloadBitmap(size: BitmapSize, vararg pages: Int) {
        if (pages.isEmpty()) {
            return
        }
        for (page in pages) {
            getBitmap(page, size)
        }
    }

    override fun setMaxWidth(width: Int) {
        maxWidth = if (width > 0) {
            width
        } else {
            context.getScreenWidth()
        }
        maxHeight = -1
        setBitmapSize()
    }

    override fun setMaxHeight(height: Int) {
        maxHeight = if (height > 0) {
            height
        } else {
            context.getScreenHeight()
        }
        maxWidth = -1
        setBitmapSize()
    }

    protected fun setBitmapSize() {
        if (documentSize.isValid()) {
            if (maxWidth != -1) {
                bitmapSize.width = maxWidth
                bitmapSize.height = documentSize.height * maxWidth / documentSize.width
            } else if (maxHeight != -1) {
                bitmapSize.height = maxHeight
                bitmapSize.width = documentSize.width * maxHeight / documentSize.height
            } else {
                bitmapSize.height = context.getScreenHeight()
                bitmapSize.width = documentSize.width * bitmapSize.height / documentSize.height
            }
        }
    }

    override fun close() {
        usedBitmapMemCache.clear()
        bitmapLruCache.clear()
        bitmapPool.clear()
    }

    override fun setBitmapConfig(config: Bitmap.Config) {
        bitmapPool.setBitmapConfig(config)
    }

    fun setPageCount(count: Int) {
        pageCount = count
    }

    override fun getPageCount(): Int {
        return pageCount
    }

    override fun getBitmapSize(): BitmapSize {
        return bitmapSize
    }

    private fun getDiskCacheDir(context: Context): File {
        return File(context.cacheDir.path + File.separator + cacheFolderName())
    }

    private fun getCacheKey(pageIndex: Int, size: BitmapSize): String {
        return "$cacheTag-$pageIndex-${size.width}-${size.height}"
    }

    protected fun getEmptyBitmap(width: Int, height: Int): Bitmap {
        return bitmapPool.get(width, height)
    }

    protected open fun cacheFolderName() = "pdf-disk-cache"
}