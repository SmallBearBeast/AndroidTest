package com.example.administrator.androidtest.widget.stateful.delegate

import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.widget.stateful.IStateful
import com.example.administrator.androidtest.widget.stateful.IStatefulSubImg
import com.example.administrator.androidtest.widget.stateful.IStatefulView

class StatefulSubImgDelegate(
    private val enableViewDelegate: Boolean = true,
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate()
) : IStatefulView by viewDelegate, IStatefulSubImg, IStateful {
    companion object {
        private const val INVALID_IMG_ID = -1
        private const val INVALID_IMG_COLOR_TINT = -1
        private const val INVALID_IMG_SIZE = -1F
        private const val DEFAULT_IMG_SIZE = 64F
    }

    // Normal State
    private var normalImg = INVALID_IMG_ID
    private var normalImgTint = INVALID_IMG_COLOR_TINT
    private var normalImgSize = DEFAULT_IMG_SIZE
    private var normalImgWidth = INVALID_IMG_SIZE
    private var normalImgHeight = INVALID_IMG_SIZE


    // Pressed State
    private var pressedImg = INVALID_IMG_ID
    private var pressedImgTint = INVALID_IMG_COLOR_TINT
    private var pressedImgSize = DEFAULT_IMG_SIZE
    private var pressedImgWidth = INVALID_IMG_SIZE
    private var pressedImgHeight = INVALID_IMG_SIZE

    // Selected State
    private var selectedImg = INVALID_IMG_ID
    private var selectedImgTint = INVALID_IMG_COLOR_TINT
    private var selectedImgSize = DEFAULT_IMG_SIZE
    private var selectedImgWidth = INVALID_IMG_SIZE
    private var selectedImgHeight = INVALID_IMG_SIZE

    private var attachedView: ImageView? = null

    override fun attachView(view: View?) {
        view ?: return
        if (enableViewDelegate) {
            viewDelegate.attachView(view)
        }
        attachedView = view as? ImageView
        // post 代码保证覆盖原有的size
//        attachedView?.post {
//            updateImg()
//        }
    }

    override fun initAttributeSet(attrs: AttributeSet?) {
        if (enableViewDelegate) {
            viewDelegate.initAttributeSet(attrs)
        }
        val context = attachedView?.context ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatefulSubImg)
        try {
            parseNormalAttrs(typedArray)
            parsePressedAttrs(typedArray)
            parseSelectedAttrs(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun parseNormalAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_sub_img)) {
            setNormalSubImg(typedArray.getResourceId(R.styleable.StatefulSubImg_sf_sub_img, -1))
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_sub_img_tint)) {
            setNormalSubImgTint(typedArray.getColor(R.styleable.StatefulSubImg_sf_sub_img_tint, Color.BLACK))
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_sub_img_size)) {
            setNormalSubImgSize(typedArray.getDimension(R.styleable.StatefulSubImg_sf_sub_img_size, DEFAULT_IMG_SIZE))
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_sub_img_width)) {
            setNormalSubImgWidth(typedArray.getDimension(R.styleable.StatefulSubImg_sf_sub_img_width, normalImgSize))
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_sub_img_height)) {
            setNormalSubImgHeight(typedArray.getDimension(R.styleable.StatefulSubImg_sf_sub_img_height, normalImgSize))
        }
    }

    private fun parsePressedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_pressed_sub_img)) {
            setPressedSubImg(typedArray.getResourceId(R.styleable.StatefulSubImg_sf_pressed_sub_img, -1))
        } else {
            setPressedSubImg(normalImg)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_pressed_sub_img_tint)) {
            setPressedSubImgTint(typedArray.getColor(R.styleable.StatefulSubImg_sf_pressed_sub_img_tint, Color.BLACK))
        } else {
            setPressedSubImgTint(normalImgTint)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_pressed_sub_img_size)) {
            setPressedSubImgSize(typedArray.getDimension(R.styleable.StatefulSubImg_sf_pressed_sub_img_size, DEFAULT_IMG_SIZE))
        } else {
            setPressedSubImgSize(normalImgSize)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_pressed_sub_img_width)) {
            setPressedSubImgWidth(typedArray.getDimension(R.styleable.StatefulSubImg_sf_pressed_sub_img_width, pressedImgSize))
        } else {
            setPressedSubImgWidth(normalImgWidth)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_pressed_sub_img_height)) {
            setPressedSubImgHeight(typedArray.getDimension(R.styleable.StatefulSubImg_sf_pressed_sub_img_height, pressedImgSize))
        } else {
            setPressedSubImgHeight(normalImgHeight)
        }
    }

    private fun parseSelectedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_selected_sub_img)) {
            setSelectedSubImg(typedArray.getResourceId(R.styleable.StatefulSubImg_sf_selected_sub_img, -1))
        } else {
            setSelectedSubImg(normalImg)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_selected_sub_img_tint)) {
            setSelectedSubImgTint(typedArray.getColor(R.styleable.StatefulSubImg_sf_selected_sub_img_tint, Color.BLACK))
        } else {
            setSelectedSubImgTint(normalImgTint)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_selected_sub_img_size)) {
            setSelectedSubImgSize(typedArray.getDimension(R.styleable.StatefulSubImg_sf_selected_sub_img_size, DEFAULT_IMG_SIZE))
        } else {
            setSelectedSubImgSize(normalImgSize)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_selected_sub_img_width)) {
            setSelectedSubImgWidth(typedArray.getDimension(R.styleable.StatefulSubImg_sf_selected_sub_img_width, selectedImgSize))
        } else {
            setSelectedSubImgWidth(normalImgWidth)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_selected_sub_img_height)) {
            setSelectedSubImgHeight(typedArray.getDimension(R.styleable.StatefulSubImg_sf_selected_sub_img_height, selectedImgSize))
        } else {
            setSelectedSubImgHeight(normalImgHeight)
        }
    }

    override fun setNormalSubImg(imgResId: Int) {
        if (normalImg != imgResId) {
            normalImg = imgResId
            updateImg()
        }
    }

    override fun setNormalSubImgTint(tintColor: Int) {
        if (normalImgTint != tintColor) {
            normalImgTint = tintColor
            updateImg()
        }
    }

    override fun setNormalSubImgSize(imgSize: Float) {
        if (normalImgSize != imgSize) {
            normalImgSize = imgSize
            updateImg()
        }
    }

    override fun setNormalSubImgWidth(imgWidth: Float) {
        if (normalImgWidth != imgWidth) {
            normalImgWidth = imgWidth
            updateImg()
        }
    }

    override fun setNormalSubImgHeight(imgHeight: Float) {
        if (normalImgHeight != imgHeight) {
            normalImgHeight = imgHeight
            updateImg()
        }
    }

    override fun setPressedSubImg(imgResId: Int) {
        if (pressedImg != imgResId) {
            pressedImg = imgResId
            updateImg()
        }
    }

    override fun setPressedSubImgTint(tintColor: Int) {
        if (pressedImgTint != tintColor) {
            pressedImgTint = tintColor
            updateImg()
        }
    }

    override fun setPressedSubImgSize(imgSize: Float) {
        if (pressedImgSize != imgSize) {
            pressedImgSize = imgSize
            updateImg()
        }
    }

    override fun setPressedSubImgWidth(imgWidth: Float) {
        if (pressedImgWidth != imgWidth) {
            pressedImgWidth = imgWidth
            updateImg()
        }
    }

    override fun setPressedSubImgHeight(imgHeight: Float) {
        if (pressedImgHeight != imgHeight) {
            pressedImgHeight = imgHeight
            updateImg()
        }
    }

    override fun setSelectedSubImg(imgResId: Int) {
        if (selectedImg != imgResId) {
            selectedImg = imgResId
            updateImg()
        }
    }

    override fun setSelectedSubImgTint(tintColor: Int) {
        if (selectedImgTint != tintColor) {
            selectedImgTint = tintColor
            updateImg()
        }
    }

    override fun setSelectedSubImgSize(imgSize: Float) {
        if (selectedImgSize != imgSize) {
            selectedImgSize = imgSize
            updateImg()
        }
    }

    override fun setSelectedSubImgWidth(imgWidth: Float) {
        if (selectedImgWidth != imgWidth) {
            selectedImgWidth = imgWidth
            updateImg()
        }
    }

    override fun setSelectedSubImgHeight(imgHeight: Float) {
        if (selectedImgHeight != imgHeight) {
            selectedImgHeight = imgHeight
            updateImg()
        }
    }

    override fun onPressedChanged(pressed: Boolean) {
        updateImg(pressed = pressed)
    }

    override fun onSelectedChanged(selected: Boolean) {
        updateImg(selected = selected)
    }

    override fun onLayoutParamsChanged() {
        updateImg()
    }

    private fun updateImg(
        pressed: Boolean = attachedView?.isPressed ?: false,
        selected: Boolean = attachedView?.isSelected ?: false
    ) {
        when {
            pressed -> {
                if (pressedImg != INVALID_IMG_ID) {
                    attachedView?.setImageResource(pressedImg)
                }
                if (pressedImgTint != INVALID_IMG_COLOR_TINT) {
                    attachedView?.imageTintList = ColorStateList.valueOf(pressedImgTint)
                }
                val lp = attachedView?.layoutParams?.apply {
                    width = if (pressedImgWidth != INVALID_IMG_SIZE) pressedImgWidth.toInt() else pressedImgSize.toInt()
                    height = if (pressedImgHeight != INVALID_IMG_SIZE) pressedImgHeight.toInt() else pressedImgSize.toInt()
                }
                if (lp != null) {
                    attachedView?.layoutParams = lp
                }
            }

            selected -> {
                if (selectedImg != INVALID_IMG_ID) {
                    attachedView?.setImageResource(selectedImg)
                }
                if (selectedImgTint != INVALID_IMG_COLOR_TINT) {
                    attachedView?.imageTintList = ColorStateList.valueOf(selectedImgTint)
                }
                val lp = attachedView?.layoutParams?.apply {
                    width = if (selectedImgWidth != INVALID_IMG_SIZE) selectedImgWidth.toInt() else selectedImgSize.toInt()
                    height = if (selectedImgHeight != INVALID_IMG_SIZE) selectedImgHeight.toInt() else selectedImgSize.toInt()
                }
                if (lp != null) {
                    attachedView?.layoutParams = lp
                }
            }

            else -> {
                if (normalImg != INVALID_IMG_ID) {
                    attachedView?.setImageResource(normalImg)
                }
                if (normalImgTint != INVALID_IMG_COLOR_TINT) {
                    attachedView?.imageTintList = ColorStateList.valueOf(normalImgTint)
                }
                val lp = attachedView?.layoutParams?.apply {
                    width = if (normalImgWidth != INVALID_IMG_SIZE) normalImgWidth.toInt() else normalImgSize.toInt()
                    height = if (normalImgHeight != INVALID_IMG_SIZE) normalImgHeight.toInt() else normalImgSize.toInt()
                }
                if (lp != null) {
                    attachedView?.layoutParams = lp
                }
            }
        }
    }
}