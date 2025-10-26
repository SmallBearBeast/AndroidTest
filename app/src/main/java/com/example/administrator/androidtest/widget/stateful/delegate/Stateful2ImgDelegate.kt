package com.example.administrator.androidtest.widget.stateful.delegate

import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.widget.stateful.IStateful
import com.example.administrator.androidtest.widget.stateful.IStateful2Img
import com.example.administrator.androidtest.widget.stateful.IStatefulImg
import com.example.administrator.androidtest.widget.stateful.IStatefulSubImg
import com.example.administrator.androidtest.widget.stateful.IStatefulView

class Stateful2ImgDelegate(
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate(),
    private val imgDelegate: StatefulImgDelegate = StatefulImgDelegate(enableViewDelegate = false),
    private val subImgDelegate: StatefulSubImgDelegate = StatefulSubImgDelegate(enableViewDelegate = false)
) : IStatefulView by viewDelegate, IStatefulImg by imgDelegate, IStatefulSubImg by subImgDelegate, IStateful2Img, IStateful {
    private var imgSpacing = 0F
    private var pressedImgSpacing = 0F
    private var selectedImgSpacing = 0F

    private var attachedView: LinearLayout? = null
    private var mainImgView: ImageView? = null
    private var subImgView: ImageView? = null

    override fun attachView(view: View?) {
        view ?: return
        if (view is LinearLayout && view.childCount <= 2) {
            mainImgView = view.findViewById(R.id.iv_main_img)
            subImgView = view.findViewById(R.id.iv_sub_img)
            viewDelegate.attachView(view)
            imgDelegate.attachView(mainImgView)
            subImgDelegate.attachView(subImgView)
            attachedView = view
        }
    }

    override fun initAttributeSet(attrs: AttributeSet?) {
        viewDelegate.initAttributeSet(attrs)
        imgDelegate.initAttributeSet(attrs)
        subImgDelegate.initAttributeSet(attrs)
        val context = attachedView?.context ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Stateful2Img)
        try {
            parseNormalAttrs(typedArray)
            parsePressedAttrs(typedArray)
            parseSelectedAttrs(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun parseNormalAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.Stateful2Img_sf_img_spacing)) {
            setImgSpacing(typedArray.getDimension(R.styleable.Stateful2Img_sf_img_spacing, 0F))
        }
    }

    private fun parsePressedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.Stateful2Img_sf_pressed_img_spacing)) {
            setPressedImgSpacing(typedArray.getDimension(R.styleable.Stateful2Img_sf_pressed_img_spacing, 0F))
        } else {
            setPressedImgSpacing(imgSpacing)
        }
    }

    private fun parseSelectedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.Stateful2Img_sf_selected_img_spacing)) {
            setSelectedImgSpacing(typedArray.getDimension(R.styleable.Stateful2Img_sf_selected_img_spacing, 0F))
        } else {
            setSelectedImgSpacing(imgSpacing)
        }
    }


    override fun setImgSpacing(spacing: Float) {
        if (imgSpacing != spacing) {
            imgSpacing = spacing
            update2Img()
        }
    }

    override fun setPressedImgSpacing(spacing: Float) {
        if (pressedImgSpacing != spacing) {
            pressedImgSpacing = spacing
            update2Img()
        }
    }

    override fun setSelectedImgSpacing(spacing: Float) {
        if (selectedImgSpacing != spacing) {
            selectedImgSpacing = spacing
            update2Img()
        }
    }

    override fun onPressedChanged(pressed: Boolean) {
        viewDelegate.onPressedChanged(pressed)
        imgDelegate.onPressedChanged(pressed)
        subImgDelegate.onPressedChanged(pressed)
        update2Img()
    }

    override fun onSelectedChanged(selected: Boolean) {
        viewDelegate.onSelectedChanged(selected)
        imgDelegate.onSelectedChanged(selected)
        subImgDelegate.onSelectedChanged(selected)
        update2Img()
    }

    override fun onLayoutParamsChanged() {
        viewDelegate.onLayoutParamsChanged()
        imgDelegate.onLayoutParamsChanged()
        subImgDelegate.onLayoutParamsChanged()
        update2Img()
    }

    private fun update2Img(
        pressed: Boolean = attachedView?.isPressed ?: false,
        selected: Boolean = attachedView?.isSelected ?: false
    ) {
        when {
            pressed -> {
                val lp = (subImgView?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                    if (attachedView?.orientation == LinearLayout.HORIZONTAL) {
                        marginStart = pressedImgSpacing.toInt()
                    } else {
                        topMargin = pressedImgSpacing.toInt()
                    }
                }
                if (lp != null) {
                    subImgView?.layoutParams = lp
                }
            }

            selected -> {
                val lp = (subImgView?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                    if (attachedView?.orientation == LinearLayout.HORIZONTAL) {
                        marginStart = selectedImgSpacing.toInt()
                    } else {
                        topMargin = selectedImgSpacing.toInt()
                    }
                }
                if (lp != null) {
                    subImgView?.layoutParams = lp
                }
            }

            else -> {
                val lp = (subImgView?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                    if (attachedView?.orientation == LinearLayout.HORIZONTAL) {
                        marginStart = imgSpacing.toInt()
                    } else {
                        topMargin = imgSpacing.toInt()
                    }
                }
                if (lp != null) {
                    subImgView?.layoutParams = lp
                }
            }
        }
    }

}