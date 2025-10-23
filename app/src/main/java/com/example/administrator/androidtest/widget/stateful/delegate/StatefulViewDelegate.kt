package com.example.administrator.androidtest.widget.stateful.delegate

import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.View
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.widget.stateful.IStateful
import com.example.administrator.androidtest.widget.stateful.IStatefulView

open class StatefulViewDelegate : IStatefulView, IStateful {
    // Normal State
    private var solidColor = IStatefulView.DEFAULT_COLOR
    private var cornerRadius = 0F
    private var strokeWidth = 0F
    private var strokeColor = IStatefulView.DEFAULT_COLOR
    private var strokeDashWidth = 0F
    private var strokeDashGap = 0F
    private var gradientColors: IntArray? = null
    private var gradientOrientation = GradientDrawable.Orientation.TOP_BOTTOM

    // Pressed State
    private var solidPressedColor = IStatefulView.DEFAULT_COLOR
    private var pressedCornerRadius = 0F
    private var pressedStrokeWidth = 0F
    private var pressedStrokeColor = IStatefulView.DEFAULT_COLOR
    private var pressedStrokeDashWidth = 0F
    private var pressedStrokeDashGap = 0F
    private var pressedGradientColors: IntArray? = null
    private var pressedGradientOrientation: GradientDrawable.Orientation? = null

    // Selected State
    private var solidSelectedColor = Color.TRANSPARENT
    private var selectedCornerRadius = 0F
    private var selectedStrokeWidth = 0F
    private var selectedStrokeColor = Color.TRANSPARENT
    private var selectedStrokeDashWidth = 0F
    private var selectedStrokeDashGap = 0F
    private var selectedGradientColors: IntArray? = null
    private var selectedGradientOrientation = GradientDrawable.Orientation.TOP_BOTTOM

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

    override fun onPressedChanged(pressed: Boolean) {
        // Do nothing
    }

    override fun onSelectedChanged(selected: Boolean) {
        // Do nothing
    }

    override fun onLayoutParamsChanged() {
        // Do nothing
    }

    private fun parseNormalAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulView_sf_solid)) {
            setSolid(typedArray.getColor(R.styleable.StatefulView_sf_solid, IStatefulView.DEFAULT_COLOR))
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_corner_radius)) {
            setCornerRadius(typedArray.getDimension(R.styleable.StatefulView_sf_corner_radius, 0F))
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_stroke_width)
            || typedArray.hasValue(R.styleable.StatefulView_sf_stroke_color)
        ) {
            setStroke(
                typedArray.getDimension(R.styleable.StatefulView_sf_stroke_width, 0F),
                typedArray.getColor(R.styleable.StatefulView_sf_stroke_color, IStatefulView.DEFAULT_COLOR)
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
            colors.add(typedArray.getColor(R.styleable.StatefulView_sf_gra_start_color, IStatefulView.DEFAULT_COLOR))
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_gra_center_color)) {
            colors.add(typedArray.getColor(R.styleable.StatefulView_sf_gra_center_color, IStatefulView.DEFAULT_COLOR))
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_gra_end_color)) {
            colors.add(typedArray.getColor(R.styleable.StatefulView_sf_gra_end_color, IStatefulView.DEFAULT_COLOR))
        }
        if (colors.isNotEmpty() || typedArray.hasValue(R.styleable.StatefulView_sf_gra_orientation)) {
            val orientationIndex = typedArray.getInt(R.styleable.StatefulView_sf_gra_orientation, -1)
            val orientation = getGradientOrientation(orientationIndex)
            setGradient(colors.toIntArray(), orientation)
        }
    }

    private fun parsePressedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed_solid)) {
            setPressedSolid(typedArray.getColor(R.styleable.StatefulView_sf_pressed_solid, IStatefulView.DEFAULT_COLOR))
        } else {
            setPressedSolid(solidColor)
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed_corner_radius)) {
            setPressedCornerRadius(typedArray.getDimension(R.styleable.StatefulView_sf_pressed_corner_radius, 0F))
        } else {
            setPressedCornerRadius(cornerRadius)
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed_stroke_width)
            || typedArray.hasValue(R.styleable.StatefulView_sf_pressed_stroke_color)
        ) {
            setPressedStroke(
                typedArray.getDimension(R.styleable.StatefulView_sf_pressed_stroke_width, 0F),
                typedArray.getColor(R.styleable.StatefulView_sf_pressed_stroke_color, IStatefulView.DEFAULT_COLOR)
            )
        } else {
            setPressedStroke(strokeWidth, strokeColor)
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed_stroke_dash_width)
            || typedArray.hasValue(R.styleable.StatefulView_sf_pressed_stroke_dash_gap)
        ) {
            setPressedStrokeDash(
                typedArray.getDimension(R.styleable.StatefulView_sf_pressed_stroke_dash_width, 0F),
                typedArray.getDimension(R.styleable.StatefulView_sf_pressed_stroke_dash_gap, 0F)
            )
        } else {
            setPressedStrokeDash(strokeDashWidth, strokeDashGap)
        }
        val colors = arrayListOf<Int>()
        if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed_gra_start_color)) {
            colors.add(typedArray.getColor(R.styleable.StatefulView_sf_pressed_gra_start_color, IStatefulView.DEFAULT_COLOR))
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed_gra_center_color)) {
            colors.add(typedArray.getColor(R.styleable.StatefulView_sf_pressed_gra_center_color, IStatefulView.DEFAULT_COLOR))
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed_gra_end_color)) {
            colors.add(typedArray.getColor(R.styleable.StatefulView_sf_pressed_gra_end_color, IStatefulView.DEFAULT_COLOR))
        }
        if (colors.isNotEmpty() || typedArray.hasValue(R.styleable.StatefulView_sf_pressed_gra_orientation)) {
            val orientationIndex = typedArray.getInt(R.styleable.StatefulView_sf_pressed_gra_orientation, -1)
            val orientation = getGradientOrientation(orientationIndex)
            setPressedGradient(colors.toIntArray(), orientation)
        } else {
            setPressedGradient(gradientColors, gradientOrientation)
        }
    }

    private fun parseSelectedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulView_sf_selected_solid)) {
            setSelectedSolid(typedArray.getColor(R.styleable.StatefulView_sf_selected_solid, IStatefulView.DEFAULT_COLOR))
        } else {
            setSelectedSolid(solidColor)
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_selected_corner_radius)) {
            setSelectedCornerRadius(typedArray.getDimension(R.styleable.StatefulView_sf_selected_corner_radius, 0F))
        } else {
            setSelectedCornerRadius(cornerRadius)
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_selected_stroke_width)
            || typedArray.hasValue(R.styleable.StatefulView_sf_selected_stroke_color)
        ) {
            setSelectedStroke(
                typedArray.getDimension(R.styleable.StatefulView_sf_selected_stroke_width, 0F),
                typedArray.getColor(R.styleable.StatefulView_sf_selected_stroke_color, IStatefulView.DEFAULT_COLOR)
            )
        } else {
            setSelectedStroke(strokeWidth, strokeColor)
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_selected_stroke_dash_width)
            || typedArray.hasValue(R.styleable.StatefulView_sf_selected_stroke_dash_gap)
        ) {
            setSelectedStrokeDash(
                typedArray.getDimension(R.styleable.StatefulView_sf_selected_stroke_dash_width, 0F),
                typedArray.getDimension(R.styleable.StatefulView_sf_selected_stroke_dash_gap, 0F)
            )
        } else {
            setSelectedStrokeDash(strokeDashWidth, strokeDashGap)
        }
        val colors = arrayListOf<Int>()
        if (typedArray.hasValue(R.styleable.StatefulView_sf_selected_gra_start_color)) {
            colors.add(typedArray.getColor(R.styleable.StatefulView_sf_selected_gra_start_color, IStatefulView.DEFAULT_COLOR))
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_selected_gra_center_color)) {
            colors.add(typedArray.getColor(R.styleable.StatefulView_sf_selected_gra_center_color, IStatefulView.DEFAULT_COLOR))
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_selected_gra_end_color)) {
            colors.add(typedArray.getColor(R.styleable.StatefulView_sf_selected_gra_end_color, IStatefulView.DEFAULT_COLOR))
        }
        if (colors.isNotEmpty() || typedArray.hasValue(R.styleable.StatefulView_sf_selected_gra_orientation)) {
            val orientationIndex = typedArray.getInt(R.styleable.StatefulView_sf_selected_gra_orientation, -1)
            val orientation = getGradientOrientation(orientationIndex)
            setSelectedGradient(colors.toIntArray(), orientation)
        } else {
            setSelectedGradient(gradientColors, gradientOrientation)
        }
    }

    override fun setSolid(color: Int) {
        if (solidColor != color) {
            solidColor = color
            normalDrawable.setColor(color)
            updateBackground()
        }
    }

    override fun setCornerRadius(radius: Float) {
        if (cornerRadius != radius) {
            cornerRadius = radius
            normalDrawable.cornerRadius = radius
            updateBackground()
        }
    }

    override fun setStroke(width: Float, color: Int) {
        if (strokeWidth != width || strokeColor != color) {
            if (strokeWidth != width) {
                strokeWidth = width
            }
            if (strokeColor != color) {
                strokeColor = color
            }
            normalDrawable.setStroke(strokeWidth.toInt(), strokeColor)
            updateBackground()
        }
    }

    override fun setStrokeDash(dashWidth: Float, dashGap: Float) {
        if (strokeDashWidth != dashWidth || strokeDashGap != dashGap) {
            if (strokeDashWidth != dashWidth) {
                strokeDashWidth = dashWidth
            }
            if (strokeDashGap != dashGap) {
                strokeDashGap = dashGap
            }
            normalDrawable.setStroke(strokeWidth.toInt(), strokeColor, strokeDashWidth, strokeDashGap)
            updateBackground()
        }
    }

    override fun setGradient(colors: IntArray?, orientation: GradientDrawable.Orientation) {
        if (!gradientColors.contentEquals(colors) || gradientOrientation != orientation) {
            if (!gradientColors.contentEquals(colors)) {
                gradientColors = colors
            }
            if (gradientOrientation != orientation) {
                gradientOrientation = orientation
            }
            normalDrawable.colors = gradientColors
            normalDrawable.orientation = gradientOrientation
            updateBackground()
        }
    }

    override fun setPressedSolid(color: Int) {
        if (solidPressedColor != color) {
            solidPressedColor = color
            pressedDrawable.setColor(color)
            updateBackground()
        }
    }

    override fun setPressedCornerRadius(radius: Float) {
        if (pressedCornerRadius != radius) {
            pressedCornerRadius = radius
            pressedDrawable.cornerRadius = radius
            updateBackground()
        }
    }

    override fun setPressedStroke(width: Float, color: Int) {
        if (pressedStrokeWidth != width || pressedStrokeColor != color) {
            if (pressedStrokeWidth != width) {
                pressedStrokeWidth = width
            }
            if (pressedStrokeColor != color) {
                pressedStrokeColor = color
            }
            pressedDrawable.setStroke(pressedStrokeWidth.toInt(), pressedStrokeColor)
            updateBackground()
        }
    }

    override fun setPressedStrokeDash(dashWidth: Float, dashGap: Float) {
        if (pressedStrokeDashWidth != dashWidth || pressedStrokeDashGap != dashGap) {
            if (pressedStrokeDashWidth != dashWidth) {
                pressedStrokeDashWidth = dashWidth
            }
            if (pressedStrokeDashGap != dashGap) {
                pressedStrokeDashGap = dashGap
            }
            pressedDrawable.setStroke(pressedStrokeWidth.toInt(), pressedStrokeColor, pressedStrokeDashWidth, pressedStrokeDashGap)
            updateBackground()
        }
    }

    override fun setPressedGradient(colors: IntArray?, orientation: GradientDrawable.Orientation) {
        if (!pressedGradientColors.contentEquals(colors) || pressedGradientOrientation != orientation) {
            if (!pressedGradientColors.contentEquals(colors)) {
                pressedGradientColors = colors
            }
            if (pressedGradientOrientation != orientation) {
                pressedGradientOrientation = orientation
            }
            pressedDrawable.colors = pressedGradientColors
            pressedDrawable.orientation = pressedGradientOrientation
            updateBackground()
        }
    }

    override fun setSelectedSolid(color: Int) {
        if (solidSelectedColor != color) {
            solidSelectedColor = color
            selectedDrawable.setColor(color)
            updateBackground()
        }
    }

    override fun setSelectedCornerRadius(radius: Float) {
        if (selectedCornerRadius != radius) {
            selectedCornerRadius = radius
            selectedDrawable.cornerRadius = radius
            updateBackground()
        }
    }

    override fun setSelectedStroke(width: Float, color: Int) {
        if (selectedStrokeWidth != width || selectedStrokeColor != color) {
            if (selectedStrokeWidth != width) {
                selectedStrokeWidth = width
            }
            if (selectedStrokeColor != color) {
                selectedStrokeColor = color
            }
            selectedDrawable.setStroke(selectedStrokeWidth.toInt(), selectedStrokeColor)
            updateBackground()
        }
    }

    override fun setSelectedStrokeDash(dashWidth: Float, dashGap: Float) {
        if (selectedStrokeDashWidth != dashWidth || selectedStrokeDashGap != dashGap) {
            if (selectedStrokeDashWidth != dashWidth) {
                selectedStrokeDashWidth = dashWidth
            }
            if (selectedStrokeDashGap != dashGap) {
                selectedStrokeDashGap = dashGap
            }
            selectedDrawable.setStroke(selectedStrokeWidth.toInt(), selectedStrokeColor, selectedStrokeDashWidth, selectedStrokeDashGap)
            updateBackground()
        }
    }

    override fun setSelectedGradient(colors: IntArray?, orientation: GradientDrawable.Orientation) {
        if (!selectedGradientColors.contentEquals(colors) || selectedGradientOrientation != orientation) {
            if (!selectedGradientColors.contentEquals(colors)) {
                selectedGradientColors = colors
            }
            if (selectedGradientOrientation != orientation) {
                selectedGradientOrientation = orientation
            }
            selectedDrawable.colors = selectedGradientColors
            selectedDrawable.orientation = selectedGradientOrientation
            updateBackground()
        }
    }

    // TODO: 优化，避免重复创建StateListDrawable
    private fun updateBackground() {
//        if (attachedView?.background is StateListDrawable) {
//            attachedView?.background?.invalidateSelf()
//            return
//        }
        val stateListDrawable = StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_pressed, android.R.attr.state_enabled), pressedDrawable)
            addState(intArrayOf(android.R.attr.state_selected), selectedDrawable)
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
