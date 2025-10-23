package com.example.administrator.androidtest.widget.stateful

import android.util.AttributeSet
import android.view.View

// TODO: 状态保存
interface IStateful {
    fun attachView(view: View?)

    fun initAttributeSet(attrs: AttributeSet?)

    fun onPressedChanged(pressed: Boolean)

    fun onSelectedChanged(selected: Boolean)

    fun onLayoutParamsChanged()
}