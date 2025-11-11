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
import com.example.administrator.androidtest.widget.stateful.IStatefulImg
import com.example.administrator.androidtest.widget.stateful.IStatefulImgText
import com.example.administrator.androidtest.widget.stateful.IStatefulText
import com.example.administrator.androidtest.widget.stateful.IStatefulView
import kotlinx.android.parcel.Parcelize

class StatefulImgTextDelegate(
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate(),
    private val textDelegate: StatefulTextDelegate = StatefulTextDelegate(enableViewDelegate = false),
    private val imgDelegate: StatefulImgDelegate = StatefulImgDelegate(enableViewDelegate = false)
) : BaseViewDelegate<LinearLayout>(), IStatefulView by viewDelegate, IStatefulText by textDelegate, IStatefulImg by imgDelegate, IStatefulImgText {
    private var imgTextState = ImgTextState()

    private var imgView: ImageView? = null
    private var textView: TextView? = null

    override fun attachView(view: View?) {
        view ?: return
        super.attachView(view)
        if (view is LinearLayout && view.childCount <= 2) {
            imgView = view.findViewById(R.id.iv_img)
            textView = view.findViewById(R.id.tv_text)
            viewDelegate.attachView(view)
            textDelegate.attachView(textView)
            imgDelegate.attachView(imgView)
        }
    }

    override fun initAttributeSet(attrs: AttributeSet?) {
        super.initAttributeSet(attrs)
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
        if (typedArray.hasValue(R.styleable.StatefulImgText_sf_reverse)) {
            setReverse(typedArray.getBoolean(R.styleable.StatefulImgText_sf_reverse, false))
        }
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

    override fun setReverse(reverse: Boolean) {
        if (imgTextState.reverse != reverse) {
            imgTextState.reverse = reverse
            updateImgText()
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
        if ((attachedView?.childCount ?: 0) > 0) {
            val curReverse = attachedView?.getChildAt(0) is TextView
            if (imgTextState.reverse != curReverse) {
                attachedView?.removeView(imgView)
                attachedView?.removeView(textView)
                if (imgTextState.reverse) {
                    attachedView?.addView(textView)
                    attachedView?.addView(imgView)
                } else {
                    attachedView?.addView(imgView)
                    attachedView?.addView(textView)
                }
            }
        }
        when {
            selected -> {
                if (isSelectedEnable) {
                    val lp = (textView?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                        if (attachedView?.orientation == LinearLayout.HORIZONTAL) {
                            if (imgTextState.reverse) {
                                marginEnd = imgTextState.selectedImgTextSpacing.toInt()
                            } else {
                                marginStart = imgTextState.selectedImgTextSpacing.toInt()
                            }
                        } else {
                            if (imgTextState.reverse) {
                                bottomMargin = imgTextState.selectedImgTextSpacing.toInt()
                            } else {
                                topMargin = imgTextState.selectedImgTextSpacing.toInt()
                            }
                        }
                    }
                    if (lp != null) {
                        textView?.layoutParams = lp
                    }
                }
            }

            pressed -> {
                if (isPressedEnable) {
                    val lp = (textView?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                        if (attachedView?.orientation == LinearLayout.HORIZONTAL) {
                            if (imgTextState.reverse) {
                                marginEnd = imgTextState.pressedImgTextSpacing.toInt()
                            } else {
                                marginStart = imgTextState.pressedImgTextSpacing.toInt()
                            }
                        } else {
                            if (imgTextState.reverse) {
                                bottomMargin = imgTextState.pressedImgTextSpacing.toInt()
                            } else {
                                topMargin = imgTextState.pressedImgTextSpacing.toInt()
                            }
                        }
                    }
                    if (lp != null) {
                        textView?.layoutParams = lp
                    }
                }
            }

            else -> {
                val lp = (textView?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                    if (attachedView?.orientation == LinearLayout.HORIZONTAL) {
                        if (imgTextState.reverse) {
                            marginEnd = imgTextState.imgTextSpacing.toInt()
                        } else {
                            marginStart = imgTextState.imgTextSpacing.toInt()
                        }
                    } else {
                        if (imgTextState.reverse) {
                            bottomMargin = imgTextState.imgTextSpacing.toInt()
                        } else {
                            topMargin = imgTextState.imgTextSpacing.toInt()
                        }
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
    // 默认图片在上面或者左边，设置为true时，图片在右边或者下面
    var reverse: Boolean = false,
    var imgTextSpacing: Float = 0F,
    var pressedImgTextSpacing: Float = 0F,
    var selectedImgTextSpacing: Float = 0F
) : Parcelable