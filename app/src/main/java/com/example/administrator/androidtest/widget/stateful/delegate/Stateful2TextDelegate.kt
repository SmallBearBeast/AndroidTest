package com.example.administrator.androidtest.widget.stateful.delegate

import android.content.res.TypedArray
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.widget.stateful.IStateful
import com.example.administrator.androidtest.widget.stateful.IStateful2Text
import com.example.administrator.androidtest.widget.stateful.IStatefulSubText
import com.example.administrator.androidtest.widget.stateful.IStatefulText
import com.example.administrator.androidtest.widget.stateful.IStatefulView
import kotlinx.android.parcel.Parcelize

class Stateful2TextDelegate(
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate(),
    private val textDelegate: StatefulTextDelegate = StatefulTextDelegate(enableViewDelegate = false),
    private val subTextDelegate: StatefulSubTextDelegate = StatefulSubTextDelegate(enableViewDelegate = false)
) : IStatefulView by viewDelegate, IStatefulText by textDelegate, IStatefulSubText by subTextDelegate, IStateful2Text, IStateful {
    private var text2State = Text2State()

    private var attachedView: LinearLayout? = null
    private var mainTextView: TextView? = null
    private var subTextView: TextView? = null

    override fun attachView(view: View?) {
        view ?: return
        if (view is LinearLayout && view.childCount <= 2) {
            mainTextView = view.findViewById(R.id.tv_main_text)
            subTextView = view.findViewById(R.id.tv_sub_text)
            viewDelegate.attachView(view)
            textDelegate.attachView(mainTextView)
            subTextDelegate.attachView(subTextView)
            attachedView = view
        }
    }

    override fun initAttributeSet(attrs: AttributeSet?) {
        viewDelegate.initAttributeSet(attrs)
        textDelegate.initAttributeSet(attrs)
        subTextDelegate.initAttributeSet(attrs)
        val context = attachedView?.context ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Stateful2Text)
        try {
            parseNormalAttrs(typedArray)
            parsePressedAttrs(typedArray)
            parseSelectedAttrs(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun parseNormalAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.Stateful2Text_sf_text_spacing)) {
            setTextSpacing(typedArray.getDimension(R.styleable.Stateful2Text_sf_text_spacing, 0F))
        } else {
            setTextSpacing(text2State.textSpacing)
        }
    }

    private fun parsePressedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.Stateful2Text_sf_pressed_text_spacing)) {
            setPressedTextSpacing(typedArray.getDimension(R.styleable.Stateful2Text_sf_pressed_text_spacing, 0F))
        } else {
            setPressedTextSpacing(text2State.textSpacing)
        }
    }

    private fun parseSelectedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.Stateful2Text_sf_selected_text_spacing)) {
            setSelectedTextSpacing(typedArray.getDimension(R.styleable.Stateful2Text_sf_selected_text_spacing, 0F))
        } else {
            setSelectedTextSpacing(text2State.textSpacing)
        }
    }

    override fun setTextSpacing(spacing: Float) {
        if (text2State.textSpacing != spacing) {
            text2State.textSpacing = spacing
            update2Text()
        }
    }

    override fun setPressedTextSpacing(spacing: Float) {
        if (text2State.pressedTextSpacing != spacing) {
            text2State.pressedTextSpacing = spacing
            update2Text()
        }
    }

    override fun setSelectedTextSpacing(spacing: Float) {
        if (text2State.selectedTextSpacing != spacing) {
            text2State.selectedTextSpacing = spacing
            update2Text()
        }
    }

    override fun onPressedChanged(pressed: Boolean) {
        viewDelegate.onPressedChanged(pressed)
        textDelegate.onPressedChanged(pressed)
        subTextDelegate.onPressedChanged(pressed)
        update2Text(pressed = pressed)
    }

    override fun onSelectedChanged(selected: Boolean) {
        viewDelegate.onSelectedChanged(selected)
        textDelegate.onSelectedChanged(selected)
        subTextDelegate.onSelectedChanged(selected)
        update2Text(selected = selected)
    }

    override fun onLayoutParamsChanged() {
        viewDelegate.onLayoutParamsChanged()
        textDelegate.onLayoutParamsChanged()
        subTextDelegate.onLayoutParamsChanged()
        update2Text()
    }

    override fun onSaveInstanceState(savedBundle: Bundle) {
        val bundle = Bundle()
        viewDelegate.onSaveInstanceState(bundle)
        textDelegate.onSaveInstanceState(bundle)
        subTextDelegate.onSaveInstanceState(bundle)
        savedBundle.putBundle("child_state", bundle)
        savedBundle.putParcelable("text2_state", text2State)
    }

    override fun onRestoreInstanceState(restoredBundle: Bundle) {
        val bundle = restoredBundle.getBundle("child_state") ?: Bundle()
        viewDelegate.onRestoreInstanceState(bundle)
        textDelegate.onRestoreInstanceState(bundle)
        subTextDelegate.onRestoreInstanceState(bundle)
        text2State = restoredBundle.getParcelable("text2_state") ?: Text2State()
        update2Text()
    }

    private fun update2Text(
        pressed: Boolean = attachedView?.isPressed ?: false,
        selected: Boolean = attachedView?.isSelected ?: false
    ) {
        when {
            selected -> {
                val lp = (subTextView?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                    if (attachedView?.orientation == LinearLayout.HORIZONTAL) {
                        marginStart = text2State.selectedTextSpacing.toInt()
                    } else {
                        topMargin = text2State.selectedTextSpacing.toInt()
                    }
                }
                if (lp != null) {
                    subTextView?.layoutParams = lp
                }
            }

            pressed -> {
                val lp = (subTextView?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                    if (attachedView?.orientation == LinearLayout.HORIZONTAL) {
                        marginStart = text2State.pressedTextSpacing.toInt()
                    } else {
                        topMargin = text2State.pressedTextSpacing.toInt()
                    }
                }
                if (lp != null) {
                    subTextView?.layoutParams = lp
                }
            }

            else -> {
                val lp = (subTextView?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                    if (attachedView?.orientation == LinearLayout.HORIZONTAL) {
                        marginStart = text2State.textSpacing.toInt()
                    } else {
                        topMargin = text2State.textSpacing.toInt()
                    }
                }
                if (lp != null) {
                    subTextView?.layoutParams = lp
                }
            }
        }
    }
}

@Parcelize
private data class Text2State(
    var textSpacing: Float = 0F,
    var pressedTextSpacing: Float = 0F,
    var selectedTextSpacing: Float = 0F
) : Parcelable