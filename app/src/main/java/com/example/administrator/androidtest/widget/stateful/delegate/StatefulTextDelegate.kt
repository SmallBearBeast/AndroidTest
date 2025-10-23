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
import com.example.administrator.androidtest.widget.stateful.IStatefulText
import com.example.administrator.androidtest.widget.stateful.IStatefulView

class StatefulTextDelegate(
    private val enableViewDelegate: Boolean = true,
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate()
) : IStatefulView by viewDelegate, IStatefulText, IStateful {
    companion object {
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
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatefulText)
        try {
            parseNormalAttrs(typedArray)
            parsePressedAttrs(typedArray)
            parseSelectedAttrs(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun parseNormalAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulText_sf_text)) {
            setNormalText(typedArray.getString(R.styleable.StatefulText_sf_text) ?: "")
        }
        if (typedArray.hasValue(R.styleable.StatefulText_sf_text_color)) {
            setNormalTextColor(typedArray.getColor(R.styleable.StatefulText_sf_text_color, Color.BLACK))
        }
        if (typedArray.hasValue(R.styleable.StatefulText_sf_text_size)) {
            setNormalTextSize(typedArray.getDimension(R.styleable.StatefulText_sf_text_size, 16F))
        }
        if (typedArray.hasValue(R.styleable.StatefulText_sf_text_style)) {
            setNormalTextStyle(typedArray.getInt(R.styleable.StatefulText_sf_text_style, Typeface.NORMAL))
        }
    }

    private fun parsePressedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulText_sf_pressed_text)) {
            setPressedText(typedArray.getString(R.styleable.StatefulText_sf_pressed_text) ?: "")
        } else {
            setPressedText(normalText)
        }
        if (typedArray.hasValue(R.styleable.StatefulText_sf_pressed_text_color)) {
            setPressedTextColor(typedArray.getColor(R.styleable.StatefulText_sf_pressed_text_color, Color.BLACK))
        } else {
            setPressedTextColor(normalTextColor)
        }
        if (typedArray.hasValue(R.styleable.StatefulText_sf_pressed_text_size)) {
            setPressedTextSize(typedArray.getDimension(R.styleable.StatefulText_sf_pressed_text_size, 16F))
        } else {
            setPressedTextSize(normalTextSize)
        }
        if (typedArray.hasValue(R.styleable.StatefulText_sf_pressed_text_style)) {
            setPressedTextStyle(typedArray.getInt(R.styleable.StatefulText_sf_pressed_text_style, Typeface.NORMAL))
        } else {
            setPressedTextStyle(normalTextStyle)
        }
    }

    private fun parseSelectedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulText_sf_selected_text)) {
            setSelectedText(typedArray.getString(R.styleable.StatefulText_sf_selected_text) ?: "")
        } else {
            setSelectedText(normalText)
        }
        if (typedArray.hasValue(R.styleable.StatefulText_sf_selected_text_color)) {
            setSelectedTextColor(typedArray.getColor(R.styleable.StatefulText_sf_selected_text_color, Color.BLACK))
        } else {
            setSelectedTextColor(normalTextColor)
        }
        if (typedArray.hasValue(R.styleable.StatefulText_sf_selected_text_size)) {
            setSelectedTextSize(typedArray.getDimension(R.styleable.StatefulText_sf_selected_text_size, 16F))
        } else {
            setSelectedTextSize(normalTextSize)
        }
        if (typedArray.hasValue(R.styleable.StatefulText_sf_selected_text_style)) {
            setSelectedTextStyle(typedArray.getInt(R.styleable.StatefulText_sf_selected_text_style, Typeface.NORMAL))
        } else {
            setSelectedTextStyle(normalTextStyle)
        }
    }

    override fun setNormalText(text: CharSequence?) {
        if (normalText != text.toString()) {
            normalText = text.toString()
            updateText()
        }
    }

    override fun setNormalTextSize(textSize: Float) {
        if (normalTextSize != textSize) {
            normalTextSize = textSize
            updateText()
        }
    }

    override fun setNormalTextColor(color: Int) {
        if (normalTextColor != color) {
            normalTextColor = color
            updateText()
        }
    }

    override fun setNormalTextStyle(style: Int) {
        if (normalTextStyle != style) {
            normalTextStyle = style
            updateText()
        }
    }

    override fun setPressedText(text: CharSequence?) {
        if (pressedText != text.toString()) {
            pressedText = text.toString()
            updateText()
        }
    }

    override fun setPressedTextSize(textSize: Float) {
        if (pressedTextSize != textSize) {
            pressedTextSize = textSize
            updateText()
        }
    }

    override fun setPressedTextColor(color: Int) {
        if (pressedTextColor != color) {
            pressedTextColor = color
            updateText()
        }
    }

    override fun setPressedTextStyle(style: Int) {
        if (pressedTextStyle != style) {
            pressedTextStyle = style
            updateText()
        }
    }

    override fun setSelectedText(text: CharSequence?) {
        if (selectedText != text.toString()) {
            selectedText = text.toString()
            updateText()
        }
    }

    override fun setSelectedTextSize(textSize: Float) {
        if (selectedTextSize != textSize) {
            selectedTextSize = textSize
            updateText()
        }
    }

    override fun setSelectedTextColor(color: Int) {
        if (selectedTextColor != color) {
            selectedTextColor = color
            updateText()
        }
    }

    override fun setSelectedTextStyle(style: Int) {
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