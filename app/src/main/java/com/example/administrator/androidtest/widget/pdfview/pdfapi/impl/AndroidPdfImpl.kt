package com.example.administrator.androidtest.widget.pdfview.pdfapi.impl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import com.bear.liblog.SLog
import com.example.administrator.androidtest.widget.pdfview.pdfapi.BitmapSize
import com.example.administrator.androidtest.widget.pdfview.pdfapi.isValid

class AndroidPdfImpl(context: Context) : BasePdfImpl(context) {

    private var pdfRenderer: PdfRenderer? = null

    init {
        // PdfRenderer只支持ARGB_8888格式，不支持RGB_565
        setBitmapConfig(Bitmap.Config.ARGB_8888)
    }

    override fun loadInner(parcelFileDescriptor: ParcelFileDescriptor) {
        // 初始化 PdfRenderer 并设置页面计数
        pdfRenderer = PdfRenderer(parcelFileDescriptor).also {
            setPageCount(it.pageCount)
            it.openPage(0).apply {
                documentSize.width = width
                documentSize.height = height
                close()
            }
        }
        setBitmapSize()
    }

    override fun getDiskOrNewBitmap(pageIndex: Int, size: BitmapSize): Bitmap? {
        var bitmap = getDiskBitmap(pageIndex, size)
        if (bitmap.isValid()) {
            return bitmap
        }
        if (pdfRenderer != null && pageIndex < getPageCount()) {
            bitmap = getEmptyBitmap(size.width, size.height)
            try {
                pdfRenderer?.openPage(pageIndex).use {
                    it?.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                }
                putDiskBitmap(pageIndex, size, bitmap)
                return bitmap
            } catch (e: Exception) {
                SLog.e(TAG, "getDiskOrNewBitmap: exception message: ${e.message}")
                return null
            }
        }
        return null
    }

    override fun close() {
        super.close()
        pdfRenderer?.close()
        pdfRenderer = null
    }

    override fun getMetaInfo() {

    }

    override fun cacheFolderName() = "androidPdf-disk-cache"

    companion object {
        private const val TAG = "AndroidPdfImpl"
    }
}