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
import com.example.administrator.androidtest.widget.stateful.IStatefulView
import kotlinx.android.parcel.Parcelize
import kotlin.math.max

open class StatefulViewDelegate(
    private val enable: Boolean = true,
) : BaseViewDelegate<View>(), IStatefulView {
    private var viewState = ViewState()

    private val normalDrawable by lazy { GradientDrawable() }
    private val pressedDrawable by lazy { GradientDrawable() }
    private val selectedDrawable by lazy { GradientDrawable() }

    override fun attachView(view: View?) {
        if (!enable) {
            return
        }
        view ?: return
        super.attachView(view)
        updateBackground()
    }

    override fun initAttributeSet(attrs: AttributeSet?) {
        if (!enable) {
            return
        }
        super.initAttributeSet(attrs)
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
        if (typedArray.hasValue(R.styleable.StatefulView_sf_left_top_corner_radius)) {
            setLeftTopCornerRadius(typedArray.getDimension(R.styleable.StatefulView_sf_left_top_corner_radius, 0F))
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_left_bottom_corner_radius)) {
            setLeftBottomCornerRadius(typedArray.getDimension(R.styleable.StatefulView_sf_left_bottom_corner_radius, 0F))
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_right_top_corner_radius)) {
            setRightTopCornerRadius(typedArray.getDimension(R.styleable.StatefulView_sf_right_top_corner_radius, 0F))
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_right_bottom_corner_radius)) {
            setRightBottomCornerRadius(typedArray.getDimension(R.styleable.StatefulView_sf_right_bottom_corner_radius, 0F))
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
        if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed_left_top_corner_radius)) {
            setPressedLeftTopCornerRadius(typedArray.getDimension(R.styleable.StatefulView_sf_pressed_left_top_corner_radius, 0F))
        } else {
            setPressedLeftTopCornerRadius(viewState.leftTopCornerRadius)
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed_left_bottom_corner_radius)) {
            setPressedLeftBottomCornerRadius(typedArray.getDimension(R.styleable.StatefulView_sf_pressed_left_bottom_corner_radius, 0F))
        } else {
            setPressedLeftBottomCornerRadius(viewState.leftBottomCornerRadius)
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed_right_top_corner_radius)) {
            setPressedRightTopCornerRadius(typedArray.getDimension(R.styleable.StatefulView_sf_pressed_right_top_corner_radius, 0F))
        } else {
            setPressedRightTopCornerRadius(viewState.rightTopCornerRadius)
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_pressed_right_bottom_corner_radius)) {
            setPressedRightBottomCornerRadius(typedArray.getDimension(R.styleable.StatefulView_sf_pressed_right_bottom_corner_radius, 0F))
        } else {
            setPressedRightBottomCornerRadius(viewState.rightBottomCornerRadius)
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
        if (typedArray.hasValue(R.styleable.StatefulView_sf_selected_left_top_corner_radius)) {
            setSelectedLeftTopCornerRadius(typedArray.getDimension(R.styleable.StatefulView_sf_selected_left_top_corner_radius, 0F))
        } else {
            setSelectedLeftTopCornerRadius(viewState.leftTopCornerRadius)
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_selected_left_bottom_corner_radius)) {
            setSelectedLeftBottomCornerRadius(typedArray.getDimension(R.styleable.StatefulView_sf_selected_left_bottom_corner_radius, 0F))
        } else {
            setSelectedLeftBottomCornerRadius(viewState.leftBottomCornerRadius)
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_selected_right_top_corner_radius)) {
            setSelectedRightTopCornerRadius(typedArray.getDimension(R.styleable.StatefulView_sf_selected_right_top_corner_radius, 0F))
        } else {
            setSelectedRightTopCornerRadius(viewState.rightTopCornerRadius)
        }
        if (typedArray.hasValue(R.styleable.StatefulView_sf_selected_right_bottom_corner_radius)) {
            setSelectedRightBottomCornerRadius(typedArray.getDimension(R.styleable.StatefulView_sf_selected_right_bottom_corner_radius, 0F))
        } else {
            setSelectedRightBottomCornerRadius(viewState.rightBottomCornerRadius)
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

    override fun setLeftTopCornerRadius(radius: Float) {
        if (viewState.leftTopCornerRadius != radius) {
            viewState.leftTopCornerRadius = radius
            updateBackground()
        }
    }

    override fun setLeftBottomCornerRadius(radius: Float) {
        if (viewState.leftBottomCornerRadius != radius) {
            viewState.leftBottomCornerRadius = radius
            updateBackground()
        }
    }

    override fun setRightTopCornerRadius(radius: Float) {
        if (viewState.rightTopCornerRadius != radius) {
            viewState.rightTopCornerRadius = radius
            updateBackground()
        }
    }

    override fun setRightBottomCornerRadius(radius: Float) {
        if (viewState.rightBottomCornerRadius != radius) {
            viewState.rightBottomCornerRadius = radius
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

    override fun setPressedLeftTopCornerRadius(radius: Float) {
        if (viewState.pressedLeftTopCornerRadius != radius) {
            viewState.pressedLeftTopCornerRadius = radius
            updateBackground()
        }
    }

    override fun setPressedLeftBottomCornerRadius(radius: Float) {
        if (viewState.pressedLeftBottomCornerRadius != radius) {
            viewState.pressedLeftBottomCornerRadius = radius
            updateBackground()
        }
    }

    override fun setPressedRightTopCornerRadius(radius: Float) {
        if (viewState.pressedRightTopCornerRadius != radius) {
            viewState.pressedRightTopCornerRadius = radius
            updateBackground()
        }
    }

    override fun setPressedRightBottomCornerRadius(radius: Float) {
        if (viewState.pressedRightBottomCornerRadius != radius) {
            viewState.pressedRightBottomCornerRadius = radius
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

    override fun setSelectedLeftTopCornerRadius(radius: Float) {
        if (viewState.selectedLeftTopCornerRadius != radius) {
            viewState.selectedLeftTopCornerRadius = radius
            updateBackground()
        }
    }

    override fun setSelectedLeftBottomCornerRadius(radius: Float) {
        if (viewState.selectedLeftBottomCornerRadius != radius) {
            viewState.selectedLeftBottomCornerRadius = radius
            updateBackground()
        }
    }

    override fun setSelectedRightTopCornerRadius(radius: Float) {
        if (viewState.selectedRightTopCornerRadius != radius) {
            viewState.selectedRightTopCornerRadius = radius
            updateBackground()
        }
    }

    override fun setSelectedRightBottomCornerRadius(radius: Float) {
        if (viewState.selectedRightBottomCornerRadius != radius) {
            viewState.selectedRightBottomCornerRadius = radius
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
        if (!enable) {
            return
        }
        savedBundle.putParcelable("view_state", viewState)
    }

    override fun onRestoreInstanceState(restoredBundle: Bundle) {
        if (!enable) {
            return
        }
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
        if (!enable) {
            return
        }
        normalDrawable.apply {
            setColor(viewState.solidColor)
            val leftTopCornerRadius = if (viewState.leftTopCornerRadius >= 0) {
                viewState.leftTopCornerRadius
            } else {
                max(viewState.cornerRadius, 0F)
            }
            val leftBottomCornerRadius = if (viewState.leftBottomCornerRadius >= 0) {
                viewState.leftBottomCornerRadius
            } else {
                max(viewState.cornerRadius, 0F)
            }
            val rightTopCornerRadius = if (viewState.rightTopCornerRadius >= 0) {
                viewState.rightTopCornerRadius
            } else {
                max(viewState.cornerRadius, 0F)
            }
            val rightBottomCornerRadius = if (viewState.rightBottomCornerRadius >= 0) {
                viewState.rightBottomCornerRadius
            } else {
                max(viewState.cornerRadius, 0F)
            }
            cornerRadii = floatArrayOf(
                leftTopCornerRadius, leftTopCornerRadius,
                rightTopCornerRadius, rightTopCornerRadius,
                rightBottomCornerRadius, rightBottomCornerRadius,
                leftBottomCornerRadius, leftBottomCornerRadius,
            )
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
        if (isPressedEnable) {
            pressedDrawable.apply {
                setColor(viewState.pressedSolidColor)
                val pressedLeftTopCornerRadius = if (viewState.pressedLeftTopCornerRadius >= 0) {
                    viewState.pressedLeftTopCornerRadius
                } else {
                    max(viewState.pressedCornerRadius, 0F)
                }
                val pressedLeftBottomCornerRadius = if (viewState.pressedLeftBottomCornerRadius >= 0) {
                    viewState.pressedLeftBottomCornerRadius
                } else {
                    max(viewState.pressedCornerRadius, 0F)
                }
                val pressedRightTopCornerRadius = if (viewState.pressedRightTopCornerRadius >= 0) {
                    viewState.pressedRightTopCornerRadius
                } else {
                    max(viewState.pressedCornerRadius, 0F)
                }
                val pressedRightBottomCornerRadius = if (viewState.pressedRightBottomCornerRadius >= 0) {
                    viewState.pressedRightBottomCornerRadius
                } else {
                    max(viewState.pressedCornerRadius, 0F)
                }
                cornerRadii = floatArrayOf(
                    pressedLeftTopCornerRadius, pressedLeftTopCornerRadius,
                    pressedRightTopCornerRadius, pressedRightTopCornerRadius,
                    pressedRightBottomCornerRadius, pressedRightBottomCornerRadius,
                    pressedLeftBottomCornerRadius, pressedLeftBottomCornerRadius,
                )
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
        }
        if (isSelectedEnable) {
            selectedDrawable.apply {
                setColor(viewState.selectedSolidColor)
                val selectedLeftTopCornerRadius = if (viewState.selectedLeftTopCornerRadius >= 0) {
                    viewState.selectedLeftTopCornerRadius
                } else {
                    max(viewState.selectedCornerRadius, 0F)
                }
                val selectedLeftBottomCornerRadius = if (viewState.selectedLeftBottomCornerRadius >= 0) {
                    viewState.selectedLeftBottomCornerRadius
                } else {
                    max(viewState.selectedCornerRadius, 0F)
                }
                val selectedRightTopCornerRadius = if (viewState.selectedRightTopCornerRadius >= 0) {
                    viewState.selectedRightTopCornerRadius
                } else {
                    max(viewState.selectedCornerRadius, 0F)
                }
                val selectedRightBottomCornerRadius = if (viewState.selectedRightBottomCornerRadius >= 0) {
                    viewState.selectedRightBottomCornerRadius
                } else {
                    max(viewState.selectedCornerRadius, 0F)
                }
                cornerRadii = floatArrayOf(
                    selectedLeftTopCornerRadius, selectedLeftTopCornerRadius,
                    selectedRightTopCornerRadius, selectedRightTopCornerRadius,
                    selectedRightBottomCornerRadius, selectedRightBottomCornerRadius,
                    selectedLeftBottomCornerRadius, selectedLeftBottomCornerRadius,
                )
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
        }
        val stateListDrawable = StateListDrawable().apply {
            if (isSelectedEnable) {
                addState(intArrayOf(android.R.attr.state_selected), selectedDrawable)
            }
            if (isPressedEnable) {
                addState(intArrayOf(android.R.attr.state_pressed), pressedDrawable)
            }
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
    var leftTopCornerRadius: Float = INVALID_CORNER_RADIUS,
    var leftBottomCornerRadius: Float = INVALID_CORNER_RADIUS,
    var rightTopCornerRadius: Float = INVALID_CORNER_RADIUS,
    var rightBottomCornerRadius: Float = INVALID_CORNER_RADIUS,
    var strokeWidth: Float = 0F,
    var strokeColor: Int = DEFAULT_COLOR,
    var strokeDashWidth: Float = 0F,
    var strokeDashGap: Float = 0F,
    var gradientColors: List<Int>? = null,
    var gradientOrientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM,

    // Pressed State
    var pressedSolidColor: Int = DEFAULT_COLOR,
    var pressedCornerRadius: Float = 0F,
    var pressedLeftTopCornerRadius: Float = INVALID_CORNER_RADIUS,
    var pressedLeftBottomCornerRadius: Float = INVALID_CORNER_RADIUS,
    var pressedRightTopCornerRadius: Float = INVALID_CORNER_RADIUS,
    var pressedRightBottomCornerRadius: Float = INVALID_CORNER_RADIUS,
    var pressedStrokeWidth: Float = 0F,
    var pressedStrokeColor: Int = DEFAULT_COLOR,
    var pressedStrokeDashWidth: Float = 0F,
    var pressedStrokeDashGap: Float = 0F,
    var pressedGradientColors: List<Int>? = null,
    var pressedGradientOrientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM,

    // Selected State
    var selectedSolidColor: Int = DEFAULT_COLOR,
    var selectedCornerRadius: Float = 0F,
    var selectedLeftTopCornerRadius: Float = INVALID_CORNER_RADIUS,
    var selectedLeftBottomCornerRadius: Float = INVALID_CORNER_RADIUS,
    var selectedRightTopCornerRadius: Float = INVALID_CORNER_RADIUS,
    var selectedRightBottomCornerRadius: Float = INVALID_CORNER_RADIUS,
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
        const val INVALID_CORNER_RADIUS = -1F
    }
}
