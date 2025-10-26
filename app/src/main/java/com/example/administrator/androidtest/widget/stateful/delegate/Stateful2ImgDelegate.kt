package com.example.administrator.androidtest.widget.stateful.delegate

import android.content.res.TypedArray
import android.os.Bundle
import android.os.Parcelable
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
import kotlinx.android.parcel.Parcelize

class Stateful2ImgDelegate(
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate(),
    private val imgDelegate: StatefulImgDelegate = StatefulImgDelegate(enableViewDelegate = false),
    private val subImgDelegate: StatefulSubImgDelegate = StatefulSubImgDelegate(enableViewDelegate = false)
) : IStatefulView by viewDelegate, IStatefulImg by imgDelegate, IStatefulSubImg by subImgDelegate, IStateful2Img, IStateful {
    private var img2State = Img2State()

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
            setPressedImgSpacing(img2State.imgSpacing)
        }
    }

    private fun parseSelectedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.Stateful2Img_sf_selected_img_spacing)) {
            setSelectedImgSpacing(typedArray.getDimension(R.styleable.Stateful2Img_sf_selected_img_spacing, 0F))
        } else {
            setSelectedImgSpacing(img2State.imgSpacing)
        }
    }


    override fun setImgSpacing(spacing: Float) {
        if (img2State.imgSpacing != spacing) {
            img2State.imgSpacing = spacing
            update2Img()
        }
    }

    override fun setPressedImgSpacing(spacing: Float) {
        if (img2State.pressedImgSpacing != spacing) {
            img2State.pressedImgSpacing = spacing
            update2Img()
        }
    }

    override fun setSelectedImgSpacing(spacing: Float) {
        if (img2State.selectedImgSpacing != spacing) {
            img2State.selectedImgSpacing = spacing
            update2Img()
        }
    }

    override fun onPressedChanged(pressed: Boolean) {
        viewDelegate.onPressedChanged(pressed)
        imgDelegate.onPressedChanged(pressed)
        subImgDelegate.onPressedChanged(pressed)
        update2Img(pressed = pressed)
    }

    override fun onSelectedChanged(selected: Boolean) {
        viewDelegate.onSelectedChanged(selected)
        imgDelegate.onSelectedChanged(selected)
        subImgDelegate.onSelectedChanged(selected)
        update2Img(selected = selected)
    }

    override fun onLayoutParamsChanged() {
        viewDelegate.onLayoutParamsChanged()
        imgDelegate.onLayoutParamsChanged()
        subImgDelegate.onLayoutParamsChanged()
        update2Img()
    }

    override fun onSaveInstanceState(savedBundle: Bundle) {
        val bundle = Bundle()
        viewDelegate.onSaveInstanceState(bundle)
        imgDelegate.onSaveInstanceState(bundle)
        subImgDelegate.onSaveInstanceState(bundle)
        savedBundle.putBundle("child_state", bundle)
        savedBundle.putParcelable("img2_state", img2State)
    }

    override fun onRestoreInstanceState(restoredBundle: Bundle) {
        val bundle = restoredBundle.getBundle("child_state") ?: Bundle()
        viewDelegate.onRestoreInstanceState(bundle)
        imgDelegate.onRestoreInstanceState(bundle)
        subImgDelegate.onRestoreInstanceState(bundle)
        img2State = restoredBundle.getParcelable("img2_state") ?: Img2State()
        update2Img()
    }

    private fun update2Img(
        pressed: Boolean = attachedView?.isPressed ?: false,
        selected: Boolean = attachedView?.isSelected ?: false
    ) {
        when {
            selected -> {
                val lp = (subImgView?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                    if (attachedView?.orientation == LinearLayout.HORIZONTAL) {
                        marginStart = img2State.selectedImgSpacing.toInt()
                    } else {
                        topMargin = img2State.selectedImgSpacing.toInt()
                    }
                }
                if (lp != null) {
                    subImgView?.layoutParams = lp
                }
            }

            pressed -> {
                val lp = (subImgView?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                    if (attachedView?.orientation == LinearLayout.HORIZONTAL) {
                        marginStart = img2State.pressedImgSpacing.toInt()
                    } else {
                        topMargin = img2State.pressedImgSpacing.toInt()
                    }
                }
                if (lp != null) {
                    subImgView?.layoutParams = lp
                }
            }

            else -> {
                val lp = (subImgView?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                    if (attachedView?.orientation == LinearLayout.HORIZONTAL) {
                        marginStart = img2State.imgSpacing.toInt()
                    } else {
                        topMargin = img2State.imgSpacing.toInt()
                    }
                }
                if (lp != null) {
                    subImgView?.layoutParams = lp
                }
            }
        }
    }

}

@Parcelize
private data class Img2State(
    var imgSpacing: Float = 0F,
    var pressedImgSpacing: Float = 0F,
    var selectedImgSpacing: Float = 0F
) : Parcelable