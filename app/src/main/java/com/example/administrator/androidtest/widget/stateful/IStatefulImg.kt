package com.example.administrator.androidtest.widget.stateful

interface IStatefulImg {
    fun setNormalImg(imgResId: Int)

    fun setNormalImgTint(tintColor: Int)

    fun setNormalImgSize(imgSize: Float)

    fun setNormalImgWidth(imgWidth: Float)

    fun setNormalImgHeight(imgHeight: Float)

    fun setPressedImg(imgResId: Int)

    fun setPressedImgTint(tintColor: Int)

    fun setPressedImgSize(imgSize: Float)

    fun setPressedImgWidth(imgWidth: Float)

    fun setPressedImgHeight(imgHeight: Float)

    fun setSelectedImg(imgResId: Int)

    fun setSelectedImgTint(tintColor: Int)

    fun setSelectedImgSize(imgSize: Float)

    fun setSelectedImgWidth(imgWidth: Float)

    fun setSelectedImgHeight(imgHeight: Float)
}