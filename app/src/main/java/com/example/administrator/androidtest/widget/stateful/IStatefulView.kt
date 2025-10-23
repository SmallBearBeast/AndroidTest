package com.example.administrator.androidtest.widget.stateful

import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View

interface IStatefulView {

    companion object {
        internal const val DEFAULT_COLOR = android.graphics.Color.TRANSPARENT
    }

    fun setSolid(color: Int = DEFAULT_COLOR)

    fun setCornerRadius(radius: Float = 0F)

    fun setStroke(width: Float = 0F, color: Int = DEFAULT_COLOR)

    fun setStrokeDash(dashWidth: Float = 0F, dashGap: Float = 0F)

    fun setGradient(colors: IntArray? = null, orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM)

    fun setPressedSolid(color: Int = DEFAULT_COLOR)

    fun setPressedCornerRadius(radius: Float = 0F)

    fun setPressedStroke(width: Float = 0F, color: Int = DEFAULT_COLOR)

    fun setPressedStrokeDash(dashWidth: Float = 0F, dashGap: Float = 0F)

    fun setPressedGradient(colors: IntArray? = null, orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM)

    fun setSelectedSolid(color: Int = DEFAULT_COLOR)

    fun setSelectedCornerRadius(radius: Float = 0F)

    fun setSelectedStroke(width: Float = 0F, color: Int = DEFAULT_COLOR)

    fun setSelectedStrokeDash(dashWidth: Float = 0F, dashGap: Float = 0F)

    fun setSelectedGradient(colors: IntArray? = null, orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM)
}
