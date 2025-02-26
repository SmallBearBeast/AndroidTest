package com.example.administrator.androidtest.widget.pdfview.pdfapi

import android.content.Context
import com.example.administrator.androidtest.widget.pdfview.pdfapi.impl.AndroidPdfImpl
import com.example.administrator.androidtest.widget.pdfview.pdfapi.impl.PdfiumImpl

object PdfApiFactory {
    /**
     * 创建PdfApi实例
     * @param context Context
     * @param type 指定使用的PDF实现类型
     * @return PdfApi 实例
     */
    @JvmStatic
    @JvmOverloads
    fun create(context: Context, type: PdfApiType = PdfApiType.DEFAULT): PdfApi {
        return when (type) {
            PdfApiType.PDFIUM -> PdfiumImpl(context)
            PdfApiType.ANDROID -> AndroidPdfImpl(context)
            PdfApiType.DEFAULT -> AndroidPdfImpl(context)
        }
    }
}

enum class PdfApiType {
    PDFIUM,    // Pdfium 实现
    ANDROID,   // Android 原生实现
    DEFAULT    // 默认实现(Pdfium)
}