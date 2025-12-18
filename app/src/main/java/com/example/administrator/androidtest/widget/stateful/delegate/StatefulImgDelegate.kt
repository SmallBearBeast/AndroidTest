package com.example.administrator.androidtest.widget.stateful.delegate

import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.widget.stateful.IStatefulImg
import com.example.administrator.androidtest.widget.stateful.IStatefulView
import kotlinx.android.parcel.Parcelize

class StatefulImgDelegate(
    enableViewDelegate: Boolean = true,
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate(enableViewDelegate)
) : BaseViewDelegate<ImageView>(), IStatefulView by viewDelegate, IStatefulImg {
    private var imgState = ImgState()

    override fun attachView(view: View?) {
        view ?: return
        super.attachView(view)
        viewDelegate.attachView(view)
        // post 代码保证覆盖原有的size
//        attachedView?.post {
//            updateImg()
//        }
    }

    override fun initAttributeSet(attrs: AttributeSet?) {
        super.initAttributeSet(attrs)
        viewDelegate.initAttributeSet(attrs)
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
            setNormalImg(typedArray.getResourceId(R.styleable.StatefulImg_sf_img, ImgState.INVALID_IMG_ID))
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_img_tint)) {
            setNormalImgTint(typedArray.getColor(R.styleable.StatefulImg_sf_img_tint, ImgState.INVALID_IMG_COLOR_TINT))
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_img_size)) {
            setNormalImgSize(typedArray.getImgSize(R.styleable.StatefulImg_sf_img_size))
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_img_width)) {
            setNormalImgWidth(typedArray.getImgSize(R.styleable.StatefulImg_sf_img_width))
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_img_height)) {
            setNormalImgHeight(typedArray.getImgSize(R.styleable.StatefulImg_sf_img_height))
        }
    }

    private fun parsePressedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_pressed_img)) {
            setPressedImg(typedArray.getResourceId(R.styleable.StatefulImg_sf_pressed_img, ImgState.INVALID_IMG_ID))
        } else {
            setPressedImg(imgState.normalImg)
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_pressed_img_tint)) {
            setPressedImgTint(typedArray.getColor(R.styleable.StatefulImg_sf_pressed_img_tint, ImgState.INVALID_IMG_COLOR_TINT))
        } else {
            setPressedImgTint(imgState.normalImgTint)
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_pressed_img_size)) {
            setPressedImgSize(typedArray.getImgSize(R.styleable.StatefulImg_sf_pressed_img_size))
        } else {
            setPressedImgSize(imgState.normalImgSize)
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_pressed_img_width)) {
            setPressedImgWidth(typedArray.getImgSize(R.styleable.StatefulImg_sf_pressed_img_width))
        } else {
            setPressedImgWidth(imgState.normalImgWidth)
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_pressed_img_height)) {
            setPressedImgHeight(typedArray.getImgSize(R.styleable.StatefulImg_sf_pressed_img_height))
        } else {
            setPressedImgHeight(imgState.normalImgHeight)
        }
    }

    private fun parseSelectedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_selected_img)) {
            setSelectedImg(typedArray.getResourceId(R.styleable.StatefulImg_sf_selected_img, ImgState.INVALID_IMG_ID))
        } else {
            setSelectedImg(imgState.normalImg)
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_selected_img_tint)) {
            setSelectedImgTint(typedArray.getColor(R.styleable.StatefulImg_sf_selected_img_tint, ImgState.INVALID_IMG_COLOR_TINT))
        } else {
            setSelectedImgTint(imgState.normalImgTint)
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_selected_img_size)) {
            setSelectedImgSize(typedArray.getImgSize(R.styleable.StatefulImg_sf_selected_img_size))
        } else {
            setSelectedImgSize(imgState.normalImgSize)
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_selected_img_width)) {
            setSelectedImgWidth(typedArray.getImgSize(R.styleable.StatefulImg_sf_selected_img_width))
        } else {
            setSelectedImgWidth(imgState.normalImgWidth)
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_selected_img_height)) {
            setSelectedImgHeight(typedArray.getImgSize(R.styleable.StatefulImg_sf_selected_img_height))
        } else {
            setSelectedImgHeight(imgState.normalImgHeight)
        }
    }

    override fun setNormalImg(imgResId: Int) {
        if (imgState.normalImg != imgResId) {
            imgState.normalImg = imgResId
            updateImg()
        }
    }

    override fun setNormalImgTint(tintColor: Int) {
        if (imgState.normalImgTint != tintColor) {
            imgState.normalImgTint = tintColor
            updateImg()
        }
    }

    override fun setNormalImgSize(imgSize: Int) {
        if (imgState.normalImgSize != imgSize) {
            imgState.normalImgSize = imgSize
            updateImg()
        }
    }

    override fun setNormalImgWidth(imgWidth: Int) {
        if (imgState.normalImgWidth != imgWidth) {
            imgState.normalImgWidth = imgWidth
            updateImg()
        }
    }

    override fun setNormalImgHeight(imgHeight: Int) {
        if (imgState.normalImgHeight != imgHeight) {
            imgState.normalImgHeight = imgHeight
            updateImg()
        }
    }

    override fun setPressedImg(imgResId: Int) {
        if (imgState.pressedImg != imgResId) {
            imgState.pressedImg = imgResId
            updateImg()
        }
    }

    override fun setPressedImgTint(tintColor: Int) {
        if (imgState.pressedImgTint != tintColor) {
            imgState.pressedImgTint = tintColor
            updateImg()
        }
    }

    override fun setPressedImgSize(imgSize: Int) {
        if (imgState.pressedImgSize != imgSize) {
            imgState.pressedImgSize = imgSize
            updateImg()
        }
    }

    override fun setPressedImgWidth(imgWidth: Int) {
        if (imgState.pressedImgWidth != imgWidth) {
            imgState.pressedImgWidth = imgWidth
            updateImg()
        }
    }

    override fun setPressedImgHeight(imgHeight: Int) {
        if (imgState.pressedImgHeight != imgHeight) {
            imgState.pressedImgHeight = imgHeight
            updateImg()
        }
    }

    override fun setSelectedImg(imgResId: Int) {
        if (imgState.selectedImg != imgResId) {
            imgState.selectedImg = imgResId
            updateImg()
        }
    }

    override fun setSelectedImgTint(tintColor: Int) {
        if (imgState.selectedImgTint != tintColor) {
            imgState.selectedImgTint = tintColor
            updateImg()
        }
    }

    override fun setSelectedImgSize(imgSize: Int) {
        if (imgState.selectedImgSize != imgSize) {
            imgState.selectedImgSize = imgSize
            updateImg()
        }
    }

    override fun setSelectedImgWidth(imgWidth: Int) {
        if (imgState.selectedImgWidth != imgWidth) {
            imgState.selectedImgWidth = imgWidth
            updateImg()
        }
    }

    override fun setSelectedImgHeight(imgHeight: Int) {
        if (imgState.selectedImgHeight != imgHeight) {
            imgState.selectedImgHeight = imgHeight
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

    override fun onSaveInstanceState(savedBundle: Bundle) {
        val bundle = Bundle()
        viewDelegate.onSaveInstanceState(savedBundle)
        savedBundle.putBundle("child_state", bundle)
        savedBundle.putParcelable("img_state", imgState)
    }

    override fun onRestoreInstanceState(restoredBundle: Bundle) {
        val bundle = restoredBundle.getBundle("child_state") ?: Bundle()
        viewDelegate.onRestoreInstanceState(bundle)
        imgState = restoredBundle.getParcelable("img_state") ?: ImgState()
        updateImg()
    }

    private fun updateImg(
        pressed: Boolean = attachedView?.isPressed ?: false,
        selected: Boolean = attachedView?.isSelected ?: false
    ) {
        when {
            selected -> {
                if (isSelectedEnable) {
                    if (imgState.selectedImg != ImgState.INVALID_IMG_ID) {
                        attachedView?.setImageResource(imgState.selectedImg)
                    }
                    if (imgState.selectedImgTint != ImgState.INVALID_IMG_COLOR_TINT) {
                        attachedView?.imageTintList = ColorStateList.valueOf(imgState.selectedImgTint)
                    }
                    val lp = attachedView?.layoutParams?.apply {
                        width =
                            if (imgState.selectedImgWidth != ImgState.INVALID_IMG_SIZE) imgState.selectedImgWidth else imgState.selectedImgSize
                        height =
                            if (imgState.selectedImgHeight != ImgState.INVALID_IMG_SIZE) imgState.selectedImgHeight else imgState.selectedImgSize
                    }
                    if (lp != null) {
                        attachedView?.layoutParams = lp
                    }
                }
            }

            pressed -> {
                if (isPressedEnable) {
                    if (imgState.pressedImg != ImgState.INVALID_IMG_ID) {
                        attachedView?.setImageResource(imgState.pressedImg)
                    }
                    if (imgState.pressedImgTint != ImgState.INVALID_IMG_COLOR_TINT) {
                        attachedView?.imageTintList = ColorStateList.valueOf(imgState.pressedImgTint)
                    }
                    val lp = attachedView?.layoutParams?.apply {
                        width =
                            if (imgState.pressedImgWidth != ImgState.INVALID_IMG_SIZE) imgState.pressedImgWidth else imgState.pressedImgSize
                        height =
                            if (imgState.pressedImgHeight != ImgState.INVALID_IMG_SIZE) imgState.pressedImgHeight else imgState.pressedImgSize
                    }
                    if (lp != null) {
                        attachedView?.layoutParams = lp
                    }
                }
            }

            else -> {
                if (imgState.normalImg != ImgState.INVALID_IMG_ID) {
                    attachedView?.setImageResource(imgState.normalImg)
                }
                if (imgState.normalImgTint != ImgState.INVALID_IMG_COLOR_TINT) {
                    attachedView?.imageTintList = ColorStateList.valueOf(imgState.normalImgTint)
                }
                val lp = attachedView?.layoutParams?.apply {
                    width =
                        if (imgState.normalImgWidth != ImgState.INVALID_IMG_SIZE) imgState.normalImgWidth else imgState.normalImgSize
                    height =
                        if (imgState.normalImgHeight != ImgState.INVALID_IMG_SIZE) imgState.normalImgHeight else imgState.normalImgSize
                }
                if (lp != null) {
                    attachedView?.layoutParams = lp
                }
            }
        }
    }

    private fun TypedArray.getImgSize(
        index: Int,
        defaultValue: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    ): Int {
        return when (getType(index)) {
            TypedValue.TYPE_DIMENSION -> {
                getDimensionPixelSize(index, defaultValue)
            }

            TypedValue.TYPE_INT_DEC -> {
                getInt(index, defaultValue)
            }

            else -> defaultValue
        }
    }
}

@Parcelize
private data class ImgState(
    var normalImg: Int = INVALID_IMG_ID,
    var normalImgTint: Int = INVALID_IMG_COLOR_TINT,
    var normalImgSize: Int = DEFAULT_IMG_SIZE,
    var normalImgWidth: Int = INVALID_IMG_SIZE,
    var normalImgHeight: Int = INVALID_IMG_SIZE,

    var pressedImg: Int = INVALID_IMG_ID,
    var pressedImgTint: Int = INVALID_IMG_COLOR_TINT,
    var pressedImgSize: Int = DEFAULT_IMG_SIZE,
    var pressedImgWidth: Int = INVALID_IMG_SIZE,
    var pressedImgHeight: Int = INVALID_IMG_SIZE,

    var selectedImg: Int = INVALID_IMG_ID,
    var selectedImgTint: Int = INVALID_IMG_COLOR_TINT,
    var selectedImgSize: Int = DEFAULT_IMG_SIZE,
    var selectedImgWidth: Int = INVALID_IMG_SIZE,
    var selectedImgHeight: Int = INVALID_IMG_SIZE
) : Parcelable {
    companion object {
        const val INVALID_IMG_ID = -1
        const val INVALID_IMG_COLOR_TINT = Color.TRANSPARENT
        const val INVALID_IMG_SIZE = Int.MIN_VALUE
        const val DEFAULT_IMG_SIZE = ViewGroup.LayoutParams.WRAP_CONTENT
    }
}