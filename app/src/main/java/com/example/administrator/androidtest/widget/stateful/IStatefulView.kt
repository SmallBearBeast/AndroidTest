package com.example.administrator.androidtest.widget.stateful

import android.graphics.drawable.GradientDrawable

interface IStatefulView {
    fun setSolid(color: Int)

    fun setCornerRadius(radius: Float)

    fun setStroke(width: Float, color: Int)

    fun setStrokeDash(dashWidth: Float, dashGap: Float)

    fun setGradient(colors: List<Int>?, orientation: GradientDrawable.Orientation)

    fun setPressedSolid(color: Int)

    fun setPressedCornerRadius(radius: Float)

    fun setPressedStroke(width: Float, color: Int)

    fun setPressedStrokeDash(dashWidth: Float, dashGap: Float)

    fun setPressedGradient(colors: List<Int>?, orientation: GradientDrawable.Orientation)

    fun setSelectedSolid(color: Int)

    fun setSelectedCornerRadius(radius: Float)

    fun setSelectedStroke(width: Float, color: Int)

    fun setSelectedStrokeDash(dashWidth: Float, dashGap: Float)

    fun setSelectedGradient(colors: List<Int>?, orientation: GradientDrawable.Orientation)
}
