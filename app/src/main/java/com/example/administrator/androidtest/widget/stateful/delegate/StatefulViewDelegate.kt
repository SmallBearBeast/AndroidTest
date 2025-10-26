package com.example.administrator.androidtest.widget.stateful.delegate

import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.widget.stateful.IStateful
import com.example.administrator.androidtest.widget.stateful.IStatefulView
import kotlinx.android.parcel.Parcelize

open class StatefulViewDelegate : IStatefulView, IStateful {
    private var viewState = ViewState()

    private val normalDrawable by lazy { GradientDrawable() }
    private val pressedDrawable by lazy { GradientDrawable() }
    private val selectedDrawable by lazy { GradientDrawable() }

    private var attachedView: View? = null

    override fun attachView(view: View?) {
        view ?: return
        attachedView = view
        updateBackground()
    }

    override fun initAttributeSet(attrs: AttributeSet?) {
        val context = attachedView?.context ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatefulView)
        try {
            parseNormalAttrs(typedArray)
            parsePressedAttrs(typedArray)
            parseSelectedAttrs(typedArray)
            if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed)) {
                attachedView?.isPressed = typedArray.getBoolean(R.styleable.StatefulView_sf_pressed, false)
            }
            if (typedArray.hasValue(R.styleable.StatefulView_sf_selected)) {
                attachedView?.isSelected = typedArray.getBoolean(R.styleable.StatefulView_sf_selected, false)
            }
        } finally {
            typedArray.recycle()
        }
    }

    private fun parseNormalAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulView_sf_solid)) {
            setSolid(typedArray.getColor(R.styleable.StatefulView_sf_solid, ViewState.DEFAULT_COLOR))
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_corner_radius)) {
            setCornerRadius(typedArray.getDimension(R.styleable.StatefulView_sf_corner_radius, 0F))
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_stroke_width)
            || typedArray.hasValue(R.styleable.StatefulView_sf_stroke_color)
        ) {
            setStroke(
                typedArray.getDimension(R.styleable.StatefulView_sf_stroke_width, 0F),
                typedArray.getColor(R.styleable.StatefulView_sf_stroke_color, ViewState.DEFAULT_COLOR)
            )
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_stroke_dash_width)
            || typedArray.hasValue(R.styleable.StatefulView_sf_stroke_dash_gap)
        ) {
            setStrokeDash(
                typedArray.getDimension(R.styleable.StatefulView_sf_stroke_dash_width, 0F),
                typedArray.getDimension(R.styleable.StatefulView_sf_stroke_dash_gap, 0F)
            )
        }
        val colors = arrayListOf<Int>()
        if (typedArray.hasValue(R.styleable.StatefulView_sf_gra_start_color)) {
            colors.add(typedArray.getColor(R.styleable.StatefulView_sf_gra_start_color, ViewState.DEFAULT_COLOR))
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_gra_center_color)) {
            colors.add(typedArray.getColor(R.styleable.StatefulView_sf_gra_center_color, ViewState.DEFAULT_COLOR))
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_gra_end_color)) {
            colors.add(typedArray.getColor(R.styleable.StatefulView_sf_gra_end_color, ViewState.DEFAULT_COLOR))
        }
        if (colors.isNotEmpty() || typedArray.hasValue(R.styleable.StatefulView_sf_gra_orientation)) {
            val orientationIndex = typedArray.getInt(R.styleable.StatefulView_sf_gra_orientation, -1)
            val orientation = getGradientOrientation(orientationIndex)
            setGradient(colors, orientation)
        }
    }

    private fun parsePressedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed_solid)) {
            setPressedSolid(typedArray.getColor(R.styleable.StatefulView_sf_pressed_solid, ViewState.DEFAULT_COLOR))
        } else {
            setPressedSolid(viewState.solidColor)
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed_corner_radius)) {
            setPressedCornerRadius(typedArray.getDimension(R.styleable.StatefulView_sf_pressed_corner_radius, 0F))
        } else {
            setPressedCornerRadius(viewState.cornerRadius)
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed_stroke_width)
            || typedArray.hasValue(R.styleable.StatefulView_sf_pressed_stroke_color)
        ) {
            setPressedStroke(
                typedArray.getDimension(R.styleable.StatefulView_sf_pressed_stroke_width, 0F),
                typedArray.getColor(R.styleable.StatefulView_sf_pressed_stroke_color, ViewState.DEFAULT_COLOR)
            )
        } else {
            setPressedStroke(viewState.strokeWidth, viewState.strokeColor)
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed_stroke_dash_width)
            || typedArray.hasValue(R.styleable.StatefulView_sf_pressed_stroke_dash_gap)
        ) {
            setPressedStrokeDash(
                typedArray.getDimension(R.styleable.StatefulView_sf_pressed_stroke_dash_width, 0F),
                typedArray.getDimension(R.styleable.StatefulView_sf_pressed_stroke_dash_gap, 0F)
            )
        } else {
            setPressedStrokeDash(viewState.strokeDashWidth, viewState.strokeDashGap)
        }
        val colors = arrayListOf<Int>()
        if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed_gra_start_color)) {
            colors.add(typedArray.getColor(R.styleable.StatefulView_sf_pressed_gra_start_color, ViewState.DEFAULT_COLOR))
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed_gra_center_color)) {
            colors.add(typedArray.getColor(R.styleable.StatefulView_sf_pressed_gra_center_color, ViewState.DEFAULT_COLOR))
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed_gra_end_color)) {
            colors.add(typedArray.getColor(R.styleable.StatefulView_sf_pressed_gra_end_color, ViewState.DEFAULT_COLOR))
        }
        if (colors.isNotEmpty() || typedArray.hasValue(R.styleable.StatefulView_sf_pressed_gra_orientation)) {
            val orientationIndex = typedArray.getInt(R.styleable.StatefulView_sf_pressed_gra_orientation, -1)
            val orientation = getGradientOrientation(orientationIndex)
            setPressedGradient(colors, orientation)
        } else {
            setPressedGradient(viewState.gradientColors, viewState.gradientOrientation)
        }
    }

    private fun parseSelectedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulView_sf_selected_solid)) {
            setSelectedSolid(typedArray.getColor(R.styleable.StatefulView_sf_selected_solid, ViewState.DEFAULT_COLOR))
        } else {
            setSelectedSolid(viewState.solidColor)
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_selected_corner_radius)) {
            setSelectedCornerRadius(typedArray.getDimension(R.styleable.StatefulView_sf_selected_corner_radius, 0F))
        } else {
            setSelectedCornerRadius(viewState.cornerRadius)
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_selected_stroke_width)
            || typedArray.hasValue(R.styleable.StatefulView_sf_selected_stroke_color)
        ) {
            setSelectedStroke(
                typedArray.getDimension(R.styleable.StatefulView_sf_selected_stroke_width, 0F),
                typedArray.getColor(R.styleable.StatefulView_sf_selected_stroke_color, ViewState.DEFAULT_COLOR)
            )
        } else {
            setSelectedStroke(viewState.strokeWidth, viewState.strokeColor)
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_selected_stroke_dash_width)
            || typedArray.hasValue(R.styleable.StatefulView_sf_selected_stroke_dash_gap)
        ) {
            setSelectedStrokeDash(
                typedArray.getDimension(R.styleable.StatefulView_sf_selected_stroke_dash_width, 0F),
                typedArray.getDimension(R.styleable.StatefulView_sf_selected_stroke_dash_gap, 0F)
            )
        } else {
            setSelectedStrokeDash(viewState.strokeDashWidth, viewState.strokeDashGap)
        }
        val colors = arrayListOf<Int>()
        if (typedArray.hasValue(R.styleable.StatefulView_sf_selected_gra_start_color)) {
            colors.add(typedArray.getColor(R.styleable.StatefulView_sf_selected_gra_start_color, ViewState.DEFAULT_COLOR))
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_selected_gra_center_color)) {
            colors.add(typedArray.getColor(R.styleable.StatefulView_sf_selected_gra_center_color, ViewState.DEFAULT_COLOR))
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_selected_gra_end_color)) {
            colors.add(typedArray.getColor(R.styleable.StatefulView_sf_selected_gra_end_color, ViewState.DEFAULT_COLOR))
        }
        if (colors.isNotEmpty() || typedArray.hasValue(R.styleable.StatefulView_sf_selected_gra_orientation)) {
            val orientationIndex = typedArray.getInt(R.styleable.StatefulView_sf_selected_gra_orientation, -1)
            val orientation = getGradientOrientation(orientationIndex)
            setSelectedGradient(colors, orientation)
        } else {
            setSelectedGradient(viewState.gradientColors, viewState.gradientOrientation)
        }
    }

    override fun setSolid(color: Int) {
        if (viewState.solidColor != color) {
            viewState.solidColor = color
            updateBackground()
        }
    }

    override fun setCornerRadius(radius: Float) {
        if (viewState.cornerRadius != radius) {
            viewState.cornerRadius = radius
            updateBackground()
        }
    }

    override fun setStroke(width: Float, color: Int) {
        if (viewState.strokeWidth != width || viewState.strokeColor != color) {
            if (viewState.strokeWidth != width) {
                viewState.strokeWidth = width
            }
            if (viewState.strokeColor != color) {
                viewState.strokeColor = color
            }
            updateBackground()
        }
    }

    override fun setStrokeDash(dashWidth: Float, dashGap: Float) {
        if (viewState.strokeDashWidth != dashWidth || viewState.strokeDashGap != dashGap) {
            if (viewState.strokeDashWidth != dashWidth) {
                viewState.strokeDashWidth = dashWidth
            }
            if (viewState.strokeDashGap != dashGap) {
                viewState.strokeDashGap = dashGap
            }
            updateBackground()
        }
    }

    override fun setGradient(colors: List<Int>?, orientation: GradientDrawable.Orientation) {
        if (viewState.gradientColors != colors || viewState.gradientOrientation != orientation) {
            if (viewState.gradientColors != colors) {
                viewState.gradientColors = colors
            }
            if (viewState.gradientOrientation != orientation) {
                viewState.gradientOrientation = orientation
            }
            updateBackground()
        }
    }

    override fun setPressedSolid(color: Int) {
        if (viewState.pressedSolidColor != color) {
            viewState.pressedSolidColor = color
            updateBackground()
        }
    }

    override fun setPressedCornerRadius(radius: Float) {
        if (viewState.pressedCornerRadius != radius) {
            viewState.pressedCornerRadius = radius
            updateBackground()
        }
    }

    override fun setPressedStroke(width: Float, color: Int) {
        if (viewState.pressedStrokeWidth != width || viewState.pressedStrokeColor != color) {
            if (viewState.pressedStrokeWidth != width) {
                viewState.pressedStrokeWidth = width
            }
            if (viewState.pressedStrokeColor != color) {
                viewState.pressedStrokeColor = color
            }
            updateBackground()
        }
    }

    override fun setPressedStrokeDash(dashWidth: Float, dashGap: Float) {
        if (viewState.pressedStrokeDashWidth != dashWidth || viewState.pressedStrokeDashGap != dashGap) {
            if (viewState.pressedStrokeDashWidth != dashWidth) {
                viewState.pressedStrokeDashWidth = dashWidth
            }
            if (viewState.pressedStrokeDashGap != dashGap) {
                viewState.pressedStrokeDashGap = dashGap
            }
            updateBackground()
        }
    }

    override fun setPressedGradient(colors: List<Int>?, orientation: GradientDrawable.Orientation) {
        if (viewState.pressedGradientColors != colors || viewState.pressedGradientOrientation != orientation) {
            if (viewState.pressedGradientColors != colors) {
                viewState.pressedGradientColors = colors
            }
            if (viewState.pressedGradientOrientation != orientation) {
                viewState.pressedGradientOrientation = orientation
            }
            updateBackground()
        }
    }

    override fun setSelectedSolid(color: Int) {
        if (viewState.selectedSolidColor != color) {
            viewState.selectedSolidColor = color
            updateBackground()
        }
    }

    override fun setSelectedCornerRadius(radius: Float) {
        if (viewState.selectedCornerRadius != radius) {
            viewState.selectedCornerRadius = radius
            updateBackground()
        }
    }

    override fun setSelectedStroke(width: Float, color: Int) {
        if (viewState.selectedStrokeWidth != width || viewState.selectedStrokeColor != color) {
            if (viewState.selectedStrokeWidth != width) {
                viewState.selectedStrokeWidth = width
            }
            if (viewState.selectedStrokeColor != color) {
                viewState.selectedStrokeColor = color
            }
            updateBackground()
        }
    }

    override fun setSelectedStrokeDash(dashWidth: Float, dashGap: Float) {
        if (viewState.selectedStrokeDashWidth != dashWidth || viewState.selectedStrokeDashGap != dashGap) {
            if (viewState.selectedStrokeDashWidth != dashWidth) {
                viewState.selectedStrokeDashWidth = dashWidth
            }
            if (viewState.selectedStrokeDashGap != dashGap) {
                viewState.selectedStrokeDashGap = dashGap
            }
            updateBackground()
        }
    }

    override fun setSelectedGradient(colors: List<Int>?, orientation: GradientDrawable.Orientation) {
        if (viewState.selectedGradientColors != colors || viewState.selectedGradientOrientation != orientation) {
            if (viewState.selectedGradientColors != colors) {
                viewState.selectedGradientColors = colors
            }
            if (viewState.selectedGradientOrientation != orientation) {
                viewState.selectedGradientOrientation = orientation
            }
            updateBackground()
        }
    }

    override fun onPressedChanged(pressed: Boolean) {
        // Do nothing
    }

    override fun onSelectedChanged(selected: Boolean) {
        viewState.isSelected = selected
    }

    override fun onLayoutParamsChanged() {
        // Do nothing
    }

    override fun onSaveInstanceState(savedBundle: Bundle) {
        savedBundle.putParcelable("view_state", viewState)
    }

    override fun onRestoreInstanceState(restoredBundle: Bundle) {
        viewState = restoredBundle.getParcelable("view_state") ?: ViewState()
        attachedView?.isSelected = viewState.isSelected
        updateBackground()
    }

    // TODO: 优化，避免重复创建StateListDrawable
    private fun updateBackground() {
//        if (attachedView?.background is StateListDrawable) {
//            attachedView?.background?.invalidateSelf()
//            return
//        }
        normalDrawable.apply {
            setColor(viewState.solidColor)
            cornerRadius = viewState.cornerRadius
            setStroke(
                viewState.strokeWidth.toInt(),
                viewState.strokeColor,
                viewState.strokeDashWidth,
                viewState.strokeDashGap
            )
            if (viewState.gradientColors != null) {
                colors = viewState.gradientColors?.toIntArray()
                orientation = viewState.gradientOrientation
            }
        }
        pressedDrawable.apply {
            setColor(viewState.pressedSolidColor)
            cornerRadius = viewState.pressedCornerRadius
            setStroke(
                viewState.pressedStrokeWidth.toInt(),
                viewState.pressedStrokeColor,
                viewState.pressedStrokeDashWidth,
                viewState.pressedStrokeDashGap
            )
            if (viewState.pressedGradientColors != null) {
                colors = viewState.pressedGradientColors?.toIntArray()
                orientation = viewState.gradientOrientation
            }
        }
        selectedDrawable.apply {
            setColor(viewState.selectedSolidColor)
            cornerRadius = viewState.selectedCornerRadius
            setStroke(
                viewState.selectedStrokeWidth.toInt(),
                viewState.selectedStrokeColor,
                viewState.selectedStrokeDashWidth,
                viewState.selectedStrokeDashGap
            )
            if (viewState.selectedGradientColors != null) {
                colors = viewState.selectedGradientColors?.toIntArray()
                orientation = viewState.selectedGradientOrientation
            }
        }
        val stateListDrawable = StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_selected), selectedDrawable)
            addState(intArrayOf(android.R.attr.state_pressed), pressedDrawable)
            addState(intArrayOf(), normalDrawable)
        }
        attachedView?.background = stateListDrawable
    }

    private fun getGradientOrientation(orientationIndex: Int): GradientDrawable.Orientation {
        return when (orientationIndex) {
            0 -> return GradientDrawable.Orientation.TOP_BOTTOM
            1 -> return GradientDrawable.Orientation.TR_BL
            2 -> return GradientDrawable.Orientation.RIGHT_LEFT
            3 -> return GradientDrawable.Orientation.BR_TL
            4 -> return GradientDrawable.Orientation.BOTTOM_TOP
            5 -> return GradientDrawable.Orientation.BL_TR
            6 -> return GradientDrawable.Orientation.LEFT_RIGHT
            7 -> return GradientDrawable.Orientation.TL_BR
            else -> GradientDrawable.Orientation.TOP_BOTTOM
        }
    }
}

@Parcelize
private data class ViewState(
    // Normal State
    var solidColor: Int = DEFAULT_COLOR,
    var cornerRadius: Float = 0F,
    var strokeWidth: Float = 0F,
    var strokeColor: Int = DEFAULT_COLOR,
    var strokeDashWidth: Float = 0F,
    var strokeDashGap: Float = 0F,
    var gradientColors: List<Int>? = null,
    var gradientOrientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM,

    // Pressed State
    var pressedSolidColor: Int = DEFAULT_COLOR,
    var pressedCornerRadius: Float = 0F,
    var pressedStrokeWidth: Float = 0F,
    var pressedStrokeColor: Int = DEFAULT_COLOR,
    var pressedStrokeDashWidth: Float = 0F,
    var pressedStrokeDashGap: Float = 0F,
    var pressedGradientColors: List<Int>? = null,
    var pressedGradientOrientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM,

    // Selected State
    var selectedSolidColor: Int = DEFAULT_COLOR,
    var selectedCornerRadius: Float = 0F,
    var selectedStrokeWidth: Float = 0F,
    var selectedStrokeColor: Int = DEFAULT_COLOR,
    var selectedStrokeDashWidth: Float = 0F,
    var selectedStrokeDashGap: Float = 0F,
    var selectedGradientColors: List<Int>? = null,
    var selectedGradientOrientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM,

    var isSelected: Boolean = false,
) : Parcelable {
    companion object {
        const val DEFAULT_COLOR = Color.TRANSPARENT
    }
}
