package com.example.administrator.androidtest.widget.stateful.delegate

import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.widget.stateful.IStateful
import com.example.administrator.androidtest.widget.stateful.IStatefulSubText
import com.example.administrator.androidtest.widget.stateful.IStatefulView

class StatefulSubTextDelegate(
    private val enableViewDelegate: Boolean = true,
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate()
) : IStatefulView by viewDelegate, IStatefulSubText, IStateful {
    companion object Companion {
        private const val DEFAULT_TEXT_SIZE = 16F
        private const val DEFAULT_TEXT_COLOR = Color.BLACK
        private const val DEFAULT_TEXT_STYLE = Typeface.NORMAL
    }

    // Normal State
    private var normalText = ""
    private var normalTextColor = DEFAULT_TEXT_COLOR
    private var normalTextSize = DEFAULT_TEXT_SIZE
    private var normalTextStyle = DEFAULT_TEXT_STYLE

    // Pressed State
    private var pressedText = ""
    private var pressedTextColor = DEFAULT_TEXT_COLOR
    private var pressedTextSize = DEFAULT_TEXT_SIZE
    private var pressedTextStyle = DEFAULT_TEXT_STYLE

    // Selected State
    private var selectedText = ""
    private var selectedTextColor = DEFAULT_TEXT_COLOR
    private var selectedTextSize = DEFAULT_TEXT_SIZE
    private var selectedTextStyle = DEFAULT_TEXT_STYLE

    private var attachedView: TextView? = null

    override fun attachView(view: View?) {
        view ?: return
        if (enableViewDelegate) {
            viewDelegate.attachView(view)
        }
        attachedView = view as? TextView
    }

    override fun initAttributeSet(attrs: AttributeSet?) {
        if (enableViewDelegate) {
            viewDelegate.initAttributeSet(attrs)
        }
        val context = attachedView?.context ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatefulSubText)
        try {
            parseNormalAttrs(typedArray)
            parsePressedAttrs(typedArray)
            parseSelectedAttrs(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun parseNormalAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_sub_text)) {
            setNormalSubText(typedArray.getString(R.styleable.StatefulSubText_sf_sub_text) ?: "")
        }
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_sub_text_color)) {
            setNormalSubTextColor(typedArray.getColor(R.styleable.StatefulSubText_sf_sub_text_color, Color.BLACK))
        }
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_sub_text_size)) {
            setNormalSubTextSize(typedArray.getDimension(R.styleable.StatefulSubText_sf_sub_text_size, 16F))
        }
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_sub_text_style)) {
            setNormalSubTextStyle(typedArray.getInt(R.styleable.StatefulSubText_sf_sub_text_style, Typeface.NORMAL))
        }
    }

    private fun parsePressedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_pressed_sub_text)) {
            setPressedSubText(typedArray.getString(R.styleable.StatefulSubText_sf_pressed_sub_text) ?: "")
        } else {
            setPressedSubText(normalText)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_pressed_sub_text_color)) {
            setPressedSubTextColor(typedArray.getColor(R.styleable.StatefulSubText_sf_pressed_sub_text_color, Color.BLACK))
        } else {
            setPressedSubTextColor(normalTextColor)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_pressed_sub_text_size)) {
            setPressedSubTextSize(typedArray.getDimension(R.styleable.StatefulSubText_sf_pressed_sub_text_size, 16F))
        } else {
            setPressedSubTextSize(normalTextSize)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_pressed_sub_text_style)) {
            setPressedSubTextStyle(typedArray.getInt(R.styleable.StatefulSubText_sf_pressed_sub_text_style, Typeface.NORMAL))
        } else {
            setPressedSubTextStyle(normalTextStyle)
        }
    }

    private fun parseSelectedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_selected_sub_text)) {
            setSelectedSubText(typedArray.getString(R.styleable.StatefulSubText_sf_selected_sub_text) ?: "")
        } else {
            setSelectedSubText(normalText)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_selected_sub_text_color)) {
            setSelectedSubTextColor(typedArray.getColor(R.styleable.StatefulSubText_sf_selected_sub_text_color, Color.BLACK))
        } else {
            setSelectedSubTextColor(normalTextColor)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_selected_sub_text_size)) {
            setSelectedSubTextSize(typedArray.getDimension(R.styleable.StatefulSubText_sf_selected_sub_text_size, 16F))
        } else {
            setSelectedSubTextSize(normalTextSize)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_selected_sub_text_style)) {
            setSelectedSubTextStyle(typedArray.getInt(R.styleable.StatefulSubText_sf_selected_sub_text_style, Typeface.NORMAL))
        } else {
            setSelectedSubTextStyle(normalTextStyle)
        }
    }

    override fun setNormalSubText(text: CharSequence?) {
        if (normalText != text.toString()) {
            normalText = text.toString()
            updateText()
        }
    }

    override fun setNormalSubTextSize(textSize: Float) {
        if (normalTextSize != textSize) {
            normalTextSize = textSize
            updateText()
        }
    }

    override fun setNormalSubTextColor(color: Int) {
        if (normalTextColor != color) {
            normalTextColor = color
            updateText()
        }
    }

    override fun setNormalSubTextStyle(style: Int) {
        if (normalTextStyle != style) {
            normalTextStyle = style
            updateText()
        }
    }

    override fun setPressedSubText(text: CharSequence?) {
        if (pressedText != text.toString()) {
            pressedText = text.toString()
            updateText()
        }
    }

    override fun setPressedSubTextSize(textSize: Float) {
        if (pressedTextSize != textSize) {
            pressedTextSize = textSize
            updateText()
        }
    }

    override fun setPressedSubTextColor(color: Int) {
        if (pressedTextColor != color) {
            pressedTextColor = color
            updateText()
        }
    }

    override fun setPressedSubTextStyle(style: Int) {
        if (pressedTextStyle != style) {
            pressedTextStyle = style
            updateText()
        }
    }

    override fun setSelectedSubText(text: CharSequence?) {
        if (selectedText != text.toString()) {
            selectedText = text.toString()
            updateText()
        }
    }

    override fun setSelectedSubTextSize(textSize: Float) {
        if (selectedTextSize != textSize) {
            selectedTextSize = textSize
            updateText()
        }
    }

    override fun setSelectedSubTextColor(color: Int) {
        if (selectedTextColor != color) {
            selectedTextColor = color
            updateText()
        }
    }

    override fun setSelectedSubTextStyle(style: Int) {
        if (selectedTextStyle != style) {
            selectedTextStyle = style
            updateText()
        }
    }

    override fun onPressedChanged(pressed: Boolean) {
        updateText(pressed = pressed)
    }

    override fun onSelectedChanged(selected: Boolean) {
        updateText(selected = selected)
    }

    override fun onLayoutParamsChanged() {
        // Do nothing
    }

    private fun updateText(
        pressed: Boolean = attachedView?.isPressed ?: false,
        selected: Boolean = attachedView?.isSelected ?: false
    ) {
        when {
            pressed -> {
                attachedView?.text = pressedText
                attachedView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, pressedTextSize)
                attachedView?.setTextColor(pressedTextColor)
                attachedView?.setTypeface(null, pressedTextStyle)
            }

            selected -> {
                attachedView?.text = selectedText
                attachedView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, selectedTextSize)
                attachedView?.setTextColor(selectedTextColor)
                attachedView?.setTypeface(null, selectedTextStyle)
            }

            else -> {
                attachedView?.text = normalText
                attachedView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, normalTextSize)
                attachedView?.setTextColor(normalTextColor)
                attachedView?.setTypeface(null, normalTextStyle)
            }
        }
    }
}