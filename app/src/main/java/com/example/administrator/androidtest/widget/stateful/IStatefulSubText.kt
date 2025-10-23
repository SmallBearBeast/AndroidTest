package com.example.administrator.androidtest.widget.stateful

interface IStatefulSubText {
    fun setNormalSubText(text: CharSequence?)

    fun setNormalSubTextSize(textSize: Float)

    fun setNormalSubTextColor(color: Int)

    fun setNormalSubTextStyle(style: Int)

    fun setPressedSubText(text: CharSequence?)

    fun setPressedSubTextSize(textSize: Float)

    fun setPressedSubTextColor(color: Int)

    fun setPressedSubTextStyle(style: Int)

    fun setSelectedSubText(text: CharSequence?)

    fun setSelectedSubTextSize(textSize: Float)

    fun setSelectedSubTextColor(color: Int)

    fun setSelectedSubTextStyle(style: Int)
}