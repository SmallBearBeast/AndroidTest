package com.example.administrator.androidtest.widget.stateful

interface IStatefulImg {
    fun setNormalImg(imgResId: Int)

    fun setNormalImgTint(tintColor: Int)

    fun setNormalImgSize(imgSize: Int)

    fun setNormalImgWidth(imgWidth: Int)

    fun setNormalImgHeight(imgHeight: Int)

    fun setPressedImg(imgResId: Int)

    fun setPressedImgTint(tintColor: Int)

    fun setPressedImgSize(imgSize: Int)

    fun setPressedImgWidth(imgWidth: Int)

    fun setPressedImgHeight(imgHeight: Int)

    fun setSelectedImg(imgResId: Int)

    fun setSelectedImgTint(tintColor: Int)

    fun setSelectedImgSize(imgSize: Int)

    fun setSelectedImgWidth(imgWidth: Int)

    fun setSelectedImgHeight(imgHeight: Int)
}