package com.example.administrator.androidtest.widget.pdfview.pdfapi.cache

import android.graphics.Bitmap

interface IBitmapCache {
    fun get(key: String): Bitmap?
    fun put(key: String, value: Bitmap)
    fun remove(key: String): Bitmap?
    fun clear()
}