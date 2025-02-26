package com.example.administrator.androidtest.widget.pdfview.pdfapi.impl

import android.content.Context
import android.graphics.Bitmap
import android.os.ParcelFileDescriptor
import com.example.administrator.androidtest.widget.pdfview.pdfapi.BitmapSize
import com.example.administrator.androidtest.widget.pdfview.pdfapi.isValid
import com.example.liblog.SLog
import com.shockwave.pdfium.PdfDocument
import com.shockwave.pdfium.PdfiumCore

class PdfiumImpl(context: Context) : BasePdfImpl(context) {

    private var pdfDocument: PdfDocument? = null
    private val pdfiumCore: PdfiumCore = PdfiumCore(context)

    override fun loadInner(parcelFileDescriptor: ParcelFileDescriptor) {
        // 初始化 Pdfium 文档并设置页面计数
        pdfDocument = pdfiumCore.newDocument(parcelFileDescriptor)
        setPageCount(pdfiumCore.getPageCount(pdfDocument))
        pdfiumCore.openPage(pdfDocument, 0)
        documentSize.width = pdfiumCore.getPageWidthPoint(pdfDocument, 0)
        documentSize.height = pdfiumCore.getPageHeightPoint(pdfDocument, 0)
        setBitmapSize()
    }

    override fun getDiskOrNewBitmap(pageIndex: Int, size: BitmapSize): Bitmap? {
        var bitmap = getDiskBitmap(pageIndex, size)
        if (bitmap.isValid()) {
            return bitmap
        }
        if (pdfDocument != null && pageIndex < getPageCount()) {
            bitmap = getEmptyBitmap(size.width, size.height)
            try {
                pdfiumCore.openPage(pdfDocument, pageIndex)
                pdfiumCore.renderPageBitmap(
                    pdfDocument, bitmap, pageIndex, 0, 0,
                    size.width, size.height
                )
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
        pdfDocument?.let {
            pdfiumCore.closeDocument(it)
        }
    }

    override fun getMetaInfo() {

    }

    override fun cacheFolderName() = "pdfium-disk-cache"

    companion object {
        private const val TAG = "PdfiumImpl"
    }
}