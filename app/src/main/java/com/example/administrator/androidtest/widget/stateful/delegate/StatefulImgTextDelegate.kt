package com.example.administrator.androidtest.widget.stateful.delegate

import android.content.res.TypedArray
import android.os.Bundle
import android.os.Parcelable
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
import kotlinx.android.parcel.Parcelize

class StatefulImgTextDelegate(
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate(),
    private val textDelegate: StatefulTextDelegate = StatefulTextDelegate(enableViewDelegate = false),
    private val imgDelegate: StatefulImgDelegate = StatefulImgDelegate(enableViewDelegate = false)
) : IStatefulView by viewDelegate, IStatefulText by textDelegate, IStatefulImg by imgDelegate, IStatefulImgText, IStateful {
    private var imgTextState = ImgTextState()

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
        } else {
            setImgTextSpacing(imgTextState.imgTextSpacing)
        }
    }

    private fun parsePressedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulImgText_sf_pressed_img_text_spacing)) {
            setPressedImgTextSpacing(typedArray.getDimension(R.styleable.StatefulImgText_sf_pressed_img_text_spacing, 0F))
        } else {
            setPressedImgTextSpacing(imgTextState.imgTextSpacing)
        }
    }

    private fun parseSelectedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulImgText_sf_selected_img_text_spacing)) {
            setSelectedImgTextSpacing(typedArray.getDimension(R.styleable.StatefulImgText_sf_selected_img_text_spacing, 0F))
        } else {
            setSelectedImgTextSpacing(imgTextState.imgTextSpacing)
        }
    }

    override fun setImgTextSpacing(spacing: Float) {
        if (imgTextState.imgTextSpacing != spacing) {
            imgTextState.imgTextSpacing = spacing
            updateImgText()
        }
    }

    override fun setPressedImgTextSpacing(spacing: Float) {
        if (imgTextState.pressedImgTextSpacing != spacing) {
            imgTextState.pressedImgTextSpacing = spacing
            updateImgText()
        }
    }

    override fun setSelectedImgTextSpacing(spacing: Float) {
        if (imgTextState.selectedImgTextSpacing != spacing) {
            imgTextState.selectedImgTextSpacing = spacing
            updateImgText()
        }
    }

    override fun onPressedChanged(pressed: Boolean) {
        viewDelegate.onPressedChanged(pressed)
        textDelegate.onPressedChanged(pressed)
        imgDelegate.onPressedChanged(pressed)
        updateImgText(pressed = pressed)
    }

    override fun onSelectedChanged(selected: Boolean) {
        viewDelegate.onSelectedChanged(selected)
        textDelegate.onSelectedChanged(selected)
        imgDelegate.onSelectedChanged(selected)
        updateImgText(selected = selected)
    }

    override fun onLayoutParamsChanged() {
        viewDelegate.onLayoutParamsChanged()
        textDelegate.onLayoutParamsChanged()
        imgDelegate.onLayoutParamsChanged()
        updateImgText()
    }

    override fun onSaveInstanceState(savedBundle: Bundle) {
        val bundle = Bundle()
        viewDelegate.onSaveInstanceState(bundle)
        textDelegate.onSaveInstanceState(bundle)
        imgDelegate.onSaveInstanceState(bundle)
        savedBundle.putBundle("child_state", bundle)
        savedBundle.putParcelable("img_text_state", imgTextState)
    }

    override fun onRestoreInstanceState(restoredBundle: Bundle) {
        val bundle = restoredBundle.getBundle("child_state") ?: Bundle()
        viewDelegate.onRestoreInstanceState(bundle)
        textDelegate.onRestoreInstanceState(bundle)
        imgDelegate.onRestoreInstanceState(bundle)
        imgTextState = restoredBundle.getParcelable("img_text_state") ?: ImgTextState()
        updateImgText()
    }

    private fun updateImgText(
        pressed: Boolean = attachedView?.isPressed ?: false,
        selected: Boolean = attachedView?.isSelected ?: false
    ) {
        when {
            selected -> {
                val lp = (textView?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                    if (attachedView?.orientation == LinearLayout.HORIZONTAL) {
                        marginStart = imgTextState.selectedImgTextSpacing.toInt()
                    } else {
                        topMargin = imgTextState.selectedImgTextSpacing.toInt()
                    }
                }
                if (lp != null) {
                    textView?.layoutParams = lp
                }
            }

            pressed -> {
                val lp = (textView?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                    if (attachedView?.orientation == LinearLayout.HORIZONTAL) {
                        marginStart = imgTextState.pressedImgTextSpacing.toInt()
                    } else {
                        topMargin = imgTextState.pressedImgTextSpacing.toInt()
                    }
                }
                if (lp != null) {
                    textView?.layoutParams = lp
                }
            }

            else -> {
                val lp = (textView?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                    if (attachedView?.orientation == LinearLayout.HORIZONTAL) {
                        marginStart = imgTextState.imgTextSpacing.toInt()
                    } else {
                        topMargin = imgTextState.imgTextSpacing.toInt()
                    }
                }
                if (lp != null) {
                    textView?.layoutParams = lp
                }
            }
        }
    }
}

@Parcelize
private data class ImgTextState(
    var imgTextSpacing: Float = 0F,
    var pressedImgTextSpacing: Float = 0F,
    var selectedImgTextSpacing: Float = 0F
) : Parcelable