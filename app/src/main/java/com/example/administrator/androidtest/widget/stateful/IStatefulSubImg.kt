package com.example.administrator.androidtest.widget.stateful

interface IStatefulSubImg {
    fun setNormalSubImg(imgResId: Int)

    fun setNormalSubImgTint(tintColor: Int)

    fun setNormalSubImgSize(imgSize: Float)

    fun setNormalSubImgWidth(imgWidth: Float)

    fun setNormalSubImgHeight(imgHeight: Float)

    fun setPressedSubImg(imgResId: Int)

    fun setPressedSubImgTint(tintColor: Int)

    fun setPressedSubImgSize(imgSize: Float)

    fun setPressedSubImgWidth(imgWidth: Float)

    fun setPressedSubImgHeight(imgHeight: Float)

    fun setSelectedSubImg(imgResId: Int)

    fun setSelectedSubImgTint(tintColor: Int)

    fun setSelectedSubImgSize(imgSize: Float)

    fun setSelectedSubImgWidth(imgWidth: Float)

    fun setSelectedSubImgHeight(imgHeight: Float)
}