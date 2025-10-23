package com.example.administrator.androidtest.widget.pdfview.pdfapi

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

interface PdfApi {

    fun load(uri: Uri, tag: String)
    fun load(file: File, tag: String)

    /**
     * 加载/渲染
     */
    fun getBitmap(pageIndex: Int, size: BitmapSize = getBitmapSize()): Bitmap?
    fun getMemoryBitmap(pageIndex: Int, size: BitmapSize = getBitmapSize()): Bitmap?
    fun getDiskBitmap(pageIndex: Int, size: BitmapSize = getBitmapSize()): Bitmap?
    fun getDiskOrNewBitmap(pageIndex: Int, size: BitmapSize = getBitmapSize()): Bitmap?

    fun putUsedBitmap(pageIndex: Int, size: BitmapSize = getBitmapSize(), bitmap: Bitmap?)
    fun putCachedBitmap(pageIndex: Int, size: BitmapSize = getBitmapSize(), bitmap: Bitmap?)
    fun putDiskBitmap(pageIndex: Int, size: BitmapSize = getBitmapSize(), bitmap: Bitmap?)

    fun removeUsedBitmap(pageIndex: Int, size: BitmapSize = getBitmapSize()): Bitmap?
    fun removeCachedBitmap(pageIndex: Int, size: BitmapSize = getBitmapSize()): Bitmap?

//    fun into(imageView: ImageView, pageIndex: Int, size: BitmapSize = getBitmapSize())
//    fun cacheBitmap(pageIndex: Int, size: BitmapSize = getBitmapSize())

    /**
     * 预加载指定页
     */
    fun preloadBitmap(size: BitmapSize = getBitmapSize(), vararg pages: Int)
    fun close()

    fun setMaxWidth(width: Int)
    fun setMaxHeight(height: Int)
    fun setBitmapConfig(config: Bitmap.Config)

    fun getPageCount(): Int
    fun getMetaInfo()
    fun getBitmapSize(): BitmapSize
}