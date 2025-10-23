package com.example.administrator.androidtest.widget.stateful

interface IStatefulText {
    fun setNormalText(text: CharSequence?)

    fun setNormalTextSize(textSize: Float)

    fun setNormalTextColor(color: Int)

    fun setNormalTextStyle(style: Int)

    fun setPressedText(text: CharSequence?)

    fun setPressedTextSize(textSize: Float)

    fun setPressedTextColor(color: Int)

    fun setPressedTextStyle(style: Int)

    fun setSelectedText(text: CharSequence?)

    fun setSelectedTextSize(textSize: Float)

    fun setSelectedTextColor(color: Int)

    fun setSelectedTextStyle(style: Int)
}