package com.example.administrator.androidtest.widget.stateful.delegate

import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.widget.stateful.IStatefulText
import com.example.administrator.androidtest.widget.stateful.IStatefulView
import kotlinx.android.parcel.Parcelize

class StatefulTextDelegate(
    enableViewDelegate: Boolean = true,
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate(enableViewDelegate)
) : BaseViewDelegate<TextView>(), IStatefulView by viewDelegate, IStatefulText {
    private var textState = TextState()

    override fun attachView(view: View?) {
        view ?: return
        super.attachView(view)
        viewDelegate.attachView(view)
    }

    override fun initAttributeSet(attrs: AttributeSet?) {
        super.initAttributeSet(attrs)
        viewDelegate.initAttributeSet(attrs)
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
            setNormalTextColor(typedArray.getColor(R.styleable.StatefulText_sf_text_color, TextState.DEFAULT_TEXT_COLOR))
        }
        if (typedArray.hasValue(R.styleable.StatefulText_sf_text_size)) {
            setNormalTextSize(typedArray.getDimension(R.styleable.StatefulText_sf_text_size, TextState.DEFAULT_TEXT_SIZE))
        }
        if (typedArray.hasValue(R.styleable.StatefulText_sf_text_style)) {
            setNormalTextStyle(typedArray.getInt(R.styleable.StatefulText_sf_text_style, TextState.DEFAULT_TEXT_STYLE))
        }
    }

    private fun parsePressedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulText_sf_pressed_text)) {
            setPressedText(typedArray.getString(R.styleable.StatefulText_sf_pressed_text) ?: "")
        } else {
            setPressedText(textState.normalText)
        }
        if (typedArray.hasValue(R.styleable.StatefulText_sf_pressed_text_color)) {
            setPressedTextColor(typedArray.getColor(R.styleable.StatefulText_sf_pressed_text_color, TextState.DEFAULT_TEXT_COLOR))
        } else {
            setPressedTextColor(textState.normalTextColor)
        }
        if (typedArray.hasValue(R.styleable.StatefulText_sf_pressed_text_size)) {
            setPressedTextSize(typedArray.getDimension(R.styleable.StatefulText_sf_pressed_text_size, TextState.DEFAULT_TEXT_SIZE))
        } else {
            setPressedTextSize(textState.normalTextSize)
        }
        if (typedArray.hasValue(R.styleable.StatefulText_sf_pressed_text_style)) {
            setPressedTextStyle(typedArray.getInt(R.styleable.StatefulText_sf_pressed_text_style, TextState.DEFAULT_TEXT_STYLE))
        } else {
            setPressedTextStyle(textState.normalTextStyle)
        }
    }

    private fun parseSelectedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulText_sf_selected_text)) {
            setSelectedText(typedArray.getString(R.styleable.StatefulText_sf_selected_text) ?: "")
        } else {
            setSelectedText(textState.normalText)
        }
        if (typedArray.hasValue(R.styleable.StatefulText_sf_selected_text_color)) {
            setSelectedTextColor(typedArray.getColor(R.styleable.StatefulText_sf_selected_text_color, TextState.DEFAULT_TEXT_COLOR))
        } else {
            setSelectedTextColor(textState.normalTextColor)
        }
        if (typedArray.hasValue(R.styleable.StatefulText_sf_selected_text_size)) {
            setSelectedTextSize(typedArray.getDimension(R.styleable.StatefulText_sf_selected_text_size, TextState.DEFAULT_TEXT_SIZE))
        } else {
            setSelectedTextSize(textState.normalTextSize)
        }
        if (typedArray.hasValue(R.styleable.StatefulText_sf_selected_text_style)) {
            setSelectedTextStyle(typedArray.getInt(R.styleable.StatefulText_sf_selected_text_style, TextState.DEFAULT_TEXT_STYLE))
        } else {
            setSelectedTextStyle(textState.normalTextStyle)
        }
    }

    override fun setNormalText(text: CharSequence?) {
        if (textState.normalText != text.toString()) {
            textState.normalText = text.toString()
            updateText()
        }
    }

    override fun setNormalTextSize(textSize: Float) {
        if (textState.normalTextSize != textSize) {
            textState.normalTextSize = textSize
            updateText()
        }
    }

    override fun setNormalTextColor(color: Int) {
        if (textState.normalTextColor != color) {
            textState.normalTextColor = color
            updateText()
        }
    }

    override fun setNormalTextStyle(style: Int) {
        if (textState.normalTextStyle != style) {
            textState.normalTextStyle = style
            updateText()
        }
    }

    override fun setPressedText(text: CharSequence?) {
        if (textState.pressedText != text.toString()) {
            textState.pressedText = text.toString()
            updateText()
        }
    }

    override fun setPressedTextSize(textSize: Float) {
        if (textState.pressedTextSize != textSize) {
            textState.pressedTextSize = textSize
            updateText()
        }
    }

    override fun setPressedTextColor(color: Int) {
        if (textState.pressedTextColor != color) {
            textState.pressedTextColor = color
            updateText()
        }
    }

    override fun setPressedTextStyle(style: Int) {
        if (textState.pressedTextStyle != style) {
            textState.pressedTextStyle = style
            updateText()
        }
    }

    override fun setSelectedText(text: CharSequence?) {
        if (textState.selectedText != text.toString()) {
            textState.selectedText = text.toString()
            updateText()
        }
    }

    override fun setSelectedTextSize(textSize: Float) {
        if (textState.selectedTextSize != textSize) {
            textState.selectedTextSize = textSize
            updateText()
        }
    }

    override fun setSelectedTextColor(color: Int) {
        if (textState.selectedTextColor != color) {
            textState.selectedTextColor = color
            updateText()
        }
    }

    override fun setSelectedTextStyle(style: Int) {
        if (textState.selectedTextStyle != style) {
            textState.selectedTextStyle = style
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

    override fun onSaveInstanceState(savedBundle: Bundle) {
        val bundle = Bundle()
        viewDelegate.onSaveInstanceState(bundle)
        savedBundle.putBundle("child_state", bundle)
        savedBundle.putParcelable("text_state", textState)
    }

    override fun onRestoreInstanceState(restoredBundle: Bundle) {
        val bundle = restoredBundle.getBundle("child_state") ?: Bundle()
        viewDelegate.onRestoreInstanceState(bundle)
        textState = restoredBundle.getParcelable("text_state") ?: TextState()
        updateText()
    }

    private fun updateText(
        pressed: Boolean = attachedView?.isPressed ?: false,
        selected: Boolean = attachedView?.isSelected ?: false
    ) {
        when {
            selected -> {
                if (isSelectedEnable) {
                    attachedView?.text = textState.selectedText
                    attachedView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, textState.selectedTextSize)
                    attachedView?.setTextColor(textState.selectedTextColor)
                    attachedView?.setTypeface(null, textState.selectedTextStyle)
                }
            }

            pressed -> {
                if (isPressedEnable) {
                    attachedView?.text = textState.pressedText
                    attachedView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, textState.pressedTextSize)
                    attachedView?.setTextColor(textState.pressedTextColor)
                    attachedView?.setTypeface(null, textState.pressedTextStyle)
                }
            }

            else -> {
                attachedView?.text = textState.normalText
                attachedView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, textState.normalTextSize)
                attachedView?.setTextColor(textState.normalTextColor)
                attachedView?.setTypeface(null, textState.normalTextStyle)
            }
        }
    }
}

@Parcelize
private data class TextState(
    // Normal State
    var normalText: String = "",
    var normalTextColor: Int = DEFAULT_TEXT_COLOR,
    var normalTextSize: Float = DEFAULT_TEXT_SIZE,
    var normalTextStyle: Int = DEFAULT_TEXT_STYLE,

    // Pressed State
    var pressedText: String = "",
    var pressedTextColor: Int = DEFAULT_TEXT_COLOR,
    var pressedTextSize: Float = DEFAULT_TEXT_SIZE,
    var pressedTextStyle: Int = DEFAULT_TEXT_STYLE,

    // Selected State
    var selectedText: String = "",
    var selectedTextColor: Int = DEFAULT_TEXT_COLOR,
    var selectedTextSize: Float = DEFAULT_TEXT_SIZE,
    var selectedTextStyle: Int = DEFAULT_TEXT_STYLE,
) : Parcelable {
    companion object {
        const val DEFAULT_TEXT_SIZE = 48F
        const val DEFAULT_TEXT_COLOR = Color.BLACK
        const val DEFAULT_TEXT_STYLE = Typeface.NORMAL
    }
}
