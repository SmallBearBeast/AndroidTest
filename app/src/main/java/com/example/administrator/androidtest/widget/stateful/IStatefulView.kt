package com.example.administrator.androidtest.widget.stateful

import android.graphics.drawable.GradientDrawable

interface IStatefulView {
    fun setSolid(color: Int)

    fun setCornerRadius(radius: Float)

    fun setLeftTopCornerRadius(radius: Float)

    fun setLeftBottomCornerRadius(radius: Float)

    fun setRightTopCornerRadius(radius: Float)

    fun setRightBottomCornerRadius(radius: Float)

    fun setStroke(width: Float, color: Int)

    fun setStrokeDash(dashWidth: Float, dashGap: Float)

    fun setGradient(colors: List<Int>?, orientation: GradientDrawable.Orientation)

    fun setPressedSolid(color: Int)

    fun setPressedCornerRadius(radius: Float)

    fun setPressedLeftTopCornerRadius(radius: Float)

    fun setPressedLeftBottomCornerRadius(radius: Float)

    fun setPressedRightTopCornerRadius(radius: Float)

    fun setPressedRightBottomCornerRadius(radius: Float)

    fun setPressedStroke(width: Float, color: Int)

    fun setPressedStrokeDash(dashWidth: Float, dashGap: Float)

    fun setPressedGradient(colors: List<Int>?, orientation: GradientDrawable.Orientation)

    fun setSelectedSolid(color: Int)

    fun setSelectedCornerRadius(radius: Float)

    fun setSelectedLeftTopCornerRadius(radius: Float)

    fun setSelectedLeftBottomCornerRadius(radius: Float)

    fun setSelectedRightTopCornerRadius(radius: Float)

    fun setSelectedRightBottomCornerRadius(radius: Float)

    fun setSelectedStroke(width: Float, color: Int)

    fun setSelectedStrokeDash(dashWidth: Float, dashGap: Float)

    fun setSelectedGradient(colors: List<Int>?, orientation: GradientDrawable.Orientation)
}
