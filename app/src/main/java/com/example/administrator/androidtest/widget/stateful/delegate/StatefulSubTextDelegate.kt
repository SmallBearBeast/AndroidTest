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
import com.example.administrator.androidtest.widget.stateful.IStateful
import com.example.administrator.androidtest.widget.stateful.IStatefulSubText
import com.example.administrator.androidtest.widget.stateful.IStatefulView
import kotlinx.android.parcel.Parcelize

class StatefulSubTextDelegate(
    private val enableViewDelegate: Boolean = true,
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate()
) : IStatefulView by viewDelegate, IStatefulSubText, IStateful {
    private var subTextState = SubTextState()
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
            setNormalSubTextColor(typedArray.getColor(R.styleable.StatefulSubText_sf_sub_text_color, SubTextState.DEFAULT_TEXT_COLOR))
        }
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_sub_text_size)) {
            setNormalSubTextSize(typedArray.getDimension(R.styleable.StatefulSubText_sf_sub_text_size, SubTextState.DEFAULT_TEXT_SIZE))
        }
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_sub_text_style)) {
            setNormalSubTextStyle(typedArray.getInt(R.styleable.StatefulSubText_sf_sub_text_style, SubTextState.DEFAULT_TEXT_STYLE))
        }
    }

    private fun parsePressedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_pressed_sub_text)) {
            setPressedSubText(typedArray.getString(R.styleable.StatefulSubText_sf_pressed_sub_text) ?: "")
        } else {
            setPressedSubText(subTextState.normalText)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_pressed_sub_text_color)) {
            setPressedSubTextColor(typedArray.getColor(R.styleable.StatefulSubText_sf_pressed_sub_text_color, SubTextState.DEFAULT_TEXT_COLOR))
        } else {
            setPressedSubTextColor(subTextState.normalTextColor)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_pressed_sub_text_size)) {
            setPressedSubTextSize(typedArray.getDimension(R.styleable.StatefulSubText_sf_pressed_sub_text_size, SubTextState.DEFAULT_TEXT_SIZE))
        } else {
            setPressedSubTextSize(subTextState.normalTextSize)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_pressed_sub_text_style)) {
            setPressedSubTextStyle(typedArray.getInt(R.styleable.StatefulSubText_sf_pressed_sub_text_style, SubTextState.DEFAULT_TEXT_STYLE))
        } else {
            setPressedSubTextStyle(subTextState.normalTextStyle)
        }
    }

    private fun parseSelectedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_selected_sub_text)) {
            setSelectedSubText(typedArray.getString(R.styleable.StatefulSubText_sf_selected_sub_text) ?: "")
        } else {
            setSelectedSubText(subTextState.normalText)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_selected_sub_text_color)) {
            setSelectedSubTextColor(typedArray.getColor(R.styleable.StatefulSubText_sf_selected_sub_text_color, SubTextState.DEFAULT_TEXT_COLOR))
        } else {
            setSelectedSubTextColor(subTextState.normalTextColor)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_selected_sub_text_size)) {
            setSelectedSubTextSize(typedArray.getDimension(R.styleable.StatefulSubText_sf_selected_sub_text_size, SubTextState.DEFAULT_TEXT_SIZE))
        } else {
            setSelectedSubTextSize(subTextState.normalTextSize)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubText_sf_selected_sub_text_style)) {
            setSelectedSubTextStyle(typedArray.getInt(R.styleable.StatefulSubText_sf_selected_sub_text_style, SubTextState.DEFAULT_TEXT_STYLE))
        } else {
            setSelectedSubTextStyle(subTextState.normalTextStyle)
        }
    }

    override fun setNormalSubText(text: CharSequence?) {
        if (subTextState.normalText != text.toString()) {
            subTextState.normalText = text.toString()
            updateText()
        }
    }

    override fun setNormalSubTextSize(textSize: Float) {
        if (subTextState.normalTextSize != textSize) {
            subTextState.normalTextSize = textSize
            updateText()
        }
    }

    override fun setNormalSubTextColor(color: Int) {
        if (subTextState.normalTextColor != color) {
            subTextState.normalTextColor = color
            updateText()
        }
    }

    override fun setNormalSubTextStyle(style: Int) {
        if (subTextState.normalTextStyle != style) {
            subTextState.normalTextStyle = style
            updateText()
        }
    }

    override fun setPressedSubText(text: CharSequence?) {
        if (subTextState.pressedText != text.toString()) {
            subTextState.pressedText = text.toString()
            updateText()
        }
    }

    override fun setPressedSubTextSize(textSize: Float) {
        if (subTextState.pressedTextSize != textSize) {
            subTextState.pressedTextSize = textSize
            updateText()
        }
    }

    override fun setPressedSubTextColor(color: Int) {
        if (subTextState.pressedTextColor != color) {
            subTextState.pressedTextColor = color
            updateText()
        }
    }

    override fun setPressedSubTextStyle(style: Int) {
        if (subTextState.pressedTextStyle != style) {
            subTextState.pressedTextStyle = style
            updateText()
        }
    }

    override fun setSelectedSubText(text: CharSequence?) {
        if (subTextState.selectedText != text.toString()) {
            subTextState.selectedText = text.toString()
            updateText()
        }
    }

    override fun setSelectedSubTextSize(textSize: Float) {
        if (subTextState.selectedTextSize != textSize) {
            subTextState.selectedTextSize = textSize
            updateText()
        }
    }

    override fun setSelectedSubTextColor(color: Int) {
        if (subTextState.selectedTextColor != color) {
            subTextState.selectedTextColor = color
            updateText()
        }
    }

    override fun setSelectedSubTextStyle(style: Int) {
        if (subTextState.selectedTextStyle != style) {
            subTextState.selectedTextStyle = style
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
        if (enableViewDelegate) {
            viewDelegate.onSaveInstanceState(bundle)
        }
        savedBundle.putBundle("child_state", bundle)
        savedBundle.putParcelable("sub_text_state", subTextState)
    }

    override fun onRestoreInstanceState(restoredBundle: Bundle) {
        val bundle = restoredBundle.getBundle("child_state") ?: Bundle()
        if (enableViewDelegate) {
            viewDelegate.onRestoreInstanceState(bundle)
        }
        subTextState = restoredBundle.getParcelable("sub_text_state") ?: SubTextState()
        updateText()
    }

    private fun updateText(
        pressed: Boolean = attachedView?.isPressed ?: false,
        selected: Boolean = attachedView?.isSelected ?: false
    ) {
        when {
            selected -> {
                attachedView?.text = subTextState.selectedText
                attachedView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, subTextState.selectedTextSize)
                attachedView?.setTextColor(subTextState.selectedTextColor)
                attachedView?.setTypeface(null, subTextState.selectedTextStyle)
            }

            pressed -> {
                attachedView?.text = subTextState.pressedText
                attachedView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, subTextState.pressedTextSize)
                attachedView?.setTextColor(subTextState.pressedTextColor)
                attachedView?.setTypeface(null, subTextState.pressedTextStyle)
            }

            else -> {
                attachedView?.text = subTextState.normalText
                attachedView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, subTextState.normalTextSize)
                attachedView?.setTextColor(subTextState.normalTextColor)
                attachedView?.setTypeface(null, subTextState.normalTextStyle)
            }
        }
    }
}

@Parcelize
private data class SubTextState(
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
    var selectedTextStyle: Int = DEFAULT_TEXT_STYLE
) : Parcelable {
    companion object Companion {
        const val DEFAULT_TEXT_SIZE = 48F
        const val DEFAULT_TEXT_COLOR = Color.BLACK
        const val DEFAULT_TEXT_STYLE = Typeface.NORMAL
    }
}

