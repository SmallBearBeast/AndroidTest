package com.example.administrator.androidtest.widget.stateful.delegate

import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.widget.stateful.IStateful
import com.example.administrator.androidtest.widget.stateful.IStatefulImg
import com.example.administrator.androidtest.widget.stateful.IStatefulView

class StatefulImgDelegate(
    private val enableViewDelegate: Boolean = true,
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate()
) : IStatefulView by viewDelegate, IStatefulImg, IStateful {
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
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatefulImg)
        try {
            parseNormalAttrs(typedArray)
            parsePressedAttrs(typedArray)
            parseSelectedAttrs(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun parseNormalAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_img)) {
            setNormalImg(typedArray.getResourceId(R.styleable.StatefulImg_sf_img, -1))
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_img_tint)) {
            setNormalImgTint(typedArray.getColor(R.styleable.StatefulImg_sf_img_tint, Color.BLACK))
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_img_size)) {
            setNormalImgSize(typedArray.getDimension(R.styleable.StatefulImg_sf_img_size, DEFAULT_IMG_SIZE))
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_img_width)) {
            setNormalImgWidth(typedArray.getDimension(R.styleable.StatefulImg_sf_img_width, normalImgSize))
        }
    }

    private fun parsePressedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_pressed_img)) {
            setPressedImg(typedArray.getResourceId(R.styleable.StatefulImg_sf_pressed_img, -1))
        } else {
            setPressedImg(normalImg)
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_pressed_img_tint)) {
            setPressedImgTint(typedArray.getColor(R.styleable.StatefulImg_sf_pressed_img_tint, Color.BLACK))
        } else {
            setPressedImgTint(normalImgTint)
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_pressed_img_size)) {
            setPressedImgSize(typedArray.getDimension(R.styleable.StatefulImg_sf_pressed_img_size, DEFAULT_IMG_SIZE))
        } else {
            setPressedImgSize(normalImgSize)
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_pressed_img_width)) {
            setPressedImgWidth(typedArray.getDimension(R.styleable.StatefulImg_sf_pressed_img_width, pressedImgSize))
        } else {
            setPressedImgWidth(normalImgWidth)
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_pressed_img_height)) {
            setPressedImgHeight(typedArray.getDimension(R.styleable.StatefulImg_sf_pressed_img_height, pressedImgSize))
        } else {
            setPressedImgHeight(normalImgHeight)
        }
    }

    private fun parseSelectedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_selected_img)) {
            setSelectedImg(typedArray.getResourceId(R.styleable.StatefulImg_sf_selected_img, -1))
        } else {
            setSelectedImg(normalImg)
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_selected_img_tint)) {
            setSelectedImgTint(typedArray.getColor(R.styleable.StatefulImg_sf_selected_img_tint, Color.BLACK))
        } else {
            setSelectedImgTint(normalImgTint)
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_selected_img_size)) {
            setSelectedImgSize(typedArray.getDimension(R.styleable.StatefulImg_sf_selected_img_size, DEFAULT_IMG_SIZE))
        } else {
            setSelectedImgSize(normalImgSize)
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_selected_img_width)) {
            setSelectedImgWidth(typedArray.getDimension(R.styleable.StatefulImg_sf_selected_img_width, selectedImgSize))
        } else {
            setSelectedImgWidth(normalImgWidth)
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_selected_img_height)) {
            setSelectedImgHeight(typedArray.getDimension(R.styleable.StatefulImg_sf_selected_img_height, selectedImgSize))
        } else {
            setSelectedImgHeight(normalImgHeight)
        }
    }

    override fun setNormalImg(imgResId: Int) {
        if (normalImg != imgResId) {
            normalImg = imgResId
            updateImg()
        }
    }

    override fun setNormalImgTint(tintColor: Int) {
        if (normalImgTint != tintColor) {
            normalImgTint = tintColor
            updateImg()
        }
    }

    override fun setNormalImgSize(imgSize: Float) {
        if (normalImgSize != imgSize) {
            normalImgSize = imgSize
            updateImg()
        }
    }

    override fun setNormalImgWidth(imgWidth: Float) {
        if (normalImgWidth != imgWidth) {
            normalImgWidth = imgWidth
            updateImg()
        }
    }

    override fun setNormalImgHeight(imgHeight: Float) {
        if (normalImgHeight != imgHeight) {
            normalImgHeight = imgHeight
            updateImg()
        }
    }

    override fun setPressedImg(imgResId: Int) {
        if (pressedImg != imgResId) {
            pressedImg = imgResId
            updateImg()
        }
    }

    override fun setPressedImgTint(tintColor: Int) {
        if (pressedImgTint != tintColor) {
            pressedImgTint = tintColor
            updateImg()
        }
    }

    override fun setPressedImgSize(imgSize: Float) {
        if (pressedImgSize != imgSize) {
            pressedImgSize = imgSize
            updateImg()
        }
    }

    override fun setPressedImgWidth(imgWidth: Float) {
        if (pressedImgWidth != imgWidth) {
            pressedImgWidth = imgWidth
            updateImg()
        }
    }

    override fun setPressedImgHeight(imgHeight: Float) {
        if (pressedImgHeight != imgHeight) {
            pressedImgHeight = imgHeight
            updateImg()
        }
    }

    override fun setSelectedImg(imgResId: Int) {
        if (selectedImg != imgResId) {
            selectedImg = imgResId
            updateImg()
        }
    }

    override fun setSelectedImgTint(tintColor: Int) {
        if (selectedImgTint != tintColor) {
            selectedImgTint = tintColor
            updateImg()
        }
    }

    override fun setSelectedImgSize(imgSize: Float) {
        if (selectedImgSize != imgSize) {
            selectedImgSize = imgSize
            updateImg()
        }
    }

    override fun setSelectedImgWidth(imgWidth: Float) {
        if (selectedImgWidth != imgWidth) {
            selectedImgWidth = imgWidth
            updateImg()
        }
    }

    override fun setSelectedImgHeight(imgHeight: Float) {
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