package com.example.administrator.androidtest.widget.stateful

interface IStatefulSubImg {
    fun setNormalSubImg(imgResId: Int)

    fun setNormalSubImgTint(tintColor: Int)

    fun setNormalSubImgSize(imgSize: Int)

    fun setNormalSubImgWidth(imgWidth: Int)

    fun setNormalSubImgHeight(imgHeight: Int)

    fun setPressedSubImg(imgResId: Int)

    fun setPressedSubImgTint(tintColor: Int)

    fun setPressedSubImgSize(imgSize: Int)

    fun setPressedSubImgWidth(imgWidth: Int)

    fun setPressedSubImgHeight(imgHeight: Int)

    fun setSelectedSubImg(imgResId: Int)

    fun setSelectedSubImgTint(tintColor: Int)

    fun setSelectedSubImgSize(imgSize: Int)

    fun setSelectedSubImgWidth(imgWidth: Int)

    fun setSelectedSubImgHeight(imgHeight: Int)
}