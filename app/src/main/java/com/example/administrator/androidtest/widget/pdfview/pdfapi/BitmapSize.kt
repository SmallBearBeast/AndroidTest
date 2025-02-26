package com.example.administrator.androidtest.widget.pdfview.pdfapi

data class BitmapSize(
    var width: Int,
    var height: Int
) {
    fun isValid(): Boolean {
        return width > 0 && height > 0
    }
}
