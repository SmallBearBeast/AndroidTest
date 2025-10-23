package com.example.administrator.androidtest.widget.stateful.delegate

import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.widget.stateful.IStateful
import com.example.administrator.androidtest.widget.stateful.IStatefulImg
import com.example.administrator.androidtest.widget.stateful.IStatefulImgText
import com.example.administrator.androidtest.widget.stateful.IStatefulText
import com.example.administrator.androidtest.widget.stateful.IStatefulView

class StatefulImgTextDelegate(
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate(),
    private val textDelegate: StatefulTextDelegate = StatefulTextDelegate(enableViewDelegate = false),
    private val imgDelegate: StatefulImgDelegate = StatefulImgDelegate(enableViewDelegate = false)
) : IStatefulView by viewDelegate, IStatefulText by textDelegate, IStatefulImg by imgDelegate, IStatefulImgText, IStateful {
    private var imgTextSpacing = 0F
    private var pressedImgTextSpacing = 0F
    private var selectedImgTextSpacing = 0F

    private var attachedView: LinearLayout? = null
    private var imgView: ImageView? = null
    private var textView: TextView? = null

    override fun attachView(view: View?) {
        view ?: return
        if (view is LinearLayout && view.childCount <= 2) {
            imgView = view.findViewById(R.id.iv_img)
            textView = view.findViewById(R.id.tv_text)
            viewDelegate.attachView(view)
            textDelegate.attachView(textView)
            imgDelegate.attachView(imgView)
            attachedView = view
        }
    }

    override fun initAttributeSet(attrs: AttributeSet?) {
        viewDelegate.initAttributeSet(attrs)
        textDelegate.initAttributeSet(attrs)
        imgDelegate.initAttributeSet(attrs)
        val context = attachedView?.context ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatefulImgText)
        try {
            parseNormalAttrs(typedArray)
            parsePressedAttrs(typedArray)
            parseSelectedAttrs(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun parseNormalAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulImgText_sf_img_text_spacing)) {
            setImgTextSpacing(typedArray.getDimension(R.styleable.StatefulImgText_sf_img_text_spacing, 0F))
        }
    }

    private fun parsePressedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulImgText_sf_pressed_img_text_spacing)) {
            setPressedImgTextSpacing(typedArray.getDimension(R.styleable.StatefulImgText_sf_pressed_img_text_spacing, 0F))
        } else {
            setPressedImgTextSpacing(imgTextSpacing)
        }
    }

    private fun parseSelectedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulImgText_sf_selected_img_text_spacing)) {
            setSelectedImgTextSpacing(typedArray.getDimension(R.styleable.StatefulImgText_sf_selected_img_text_spacing, 0F))
        } else {
            setSelectedImgTextSpacing(imgTextSpacing)
        }
    }

    override fun setImgTextSpacing(spacing: Float) {
        if (imgTextSpacing != spacing) {
            imgTextSpacing = spacing
            updateImgText()
        }
    }

    override fun setPressedImgTextSpacing(spacing: Float) {
        if (pressedImgTextSpacing != spacing) {
            pressedImgTextSpacing = spacing
            updateImgText()
        }
    }

    override fun setSelectedImgTextSpacing(spacing: Float) {
        if (selectedImgTextSpacing != spacing) {
            selectedImgTextSpacing = spacing
            updateImgText()
        }
    }

    override fun onPressedChanged(pressed: Boolean) {
        viewDelegate.onPressedChanged(pressed)
        textDelegate.onPressedChanged(pressed)
        imgDelegate.onPressedChanged(pressed)
        updateImgText()
    }

    override fun onSelectedChanged(selected: Boolean) {
        viewDelegate.onSelectedChanged(selected)
        textDelegate.onSelectedChanged(selected)
        imgDelegate.onSelectedChanged(selected)
        updateImgText()
    }

    override fun onLayoutParamsChanged() {
        viewDelegate.onLayoutParamsChanged()
        textDelegate.onLayoutParamsChanged()
        imgDelegate.onLayoutParamsChanged()
        updateImgText()
    }

    private fun updateImgText(
        pressed: Boolean = attachedView?.isPressed ?: false,
        selected: Boolean = attachedView?.isSelected ?: false
    ) {
        when {
            pressed -> {
                val lp = (textView?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                    if (attachedView?.orientation == LinearLayout.HORIZONTAL) {
                        marginStart = pressedImgTextSpacing.toInt()
                    } else {
                        topMargin = pressedImgTextSpacing.toInt()
                    }
                }
                if (lp != null) {
                    textView?.layoutParams = lp
                }
            }

            selected -> {
                val lp = (textView?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                    if (attachedView?.orientation == LinearLayout.HORIZONTAL) {
                        marginStart = selectedImgTextSpacing.toInt()
                    } else {
                        topMargin = selectedImgTextSpacing.toInt()
                    }
                }
                if (lp != null) {
                    textView?.layoutParams = lp
                }
            }

            else -> {
                val lp = (textView?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                    if (attachedView?.orientation == LinearLayout.HORIZONTAL) {
                        marginStart = imgTextSpacing.toInt()
                    } else {
                        topMargin = imgTextSpacing.toInt()
                    }
                }
                if (lp != null) {
                    textView?.layoutParams = lp
                }
            }
        }
    }
}