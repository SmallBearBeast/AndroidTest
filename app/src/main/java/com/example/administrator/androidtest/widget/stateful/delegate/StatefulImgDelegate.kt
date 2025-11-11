package com.example.administrator.androidtest.widget.stateful.delegate

import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
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
            setNormalImgSize(typedArray.getDimension(R.styleable.StatefulImg_sf_img_size, ImgState.DEFAULT_IMG_SIZE))
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_img_width)) {
            setNormalImgWidth(typedArray.getDimension(R.styleable.StatefulImg_sf_img_width, ImgState.INVALID_IMG_SIZE))
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_img_height)) {
            setNormalImgHeight(typedArray.getDimension(R.styleable.StatefulImg_sf_img_height, ImgState.INVALID_IMG_SIZE))
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
            setPressedImgSize(typedArray.getDimension(R.styleable.StatefulImg_sf_pressed_img_size, ImgState.DEFAULT_IMG_SIZE))
        } else {
            setPressedImgSize(imgState.normalImgSize)
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_pressed_img_width)) {
            setPressedImgWidth(typedArray.getDimension(R.styleable.StatefulImg_sf_pressed_img_width, ImgState.INVALID_IMG_SIZE))
        } else {
            setPressedImgWidth(imgState.normalImgWidth)
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_pressed_img_height)) {
            setPressedImgHeight(typedArray.getDimension(R.styleable.StatefulImg_sf_pressed_img_height, ImgState.INVALID_IMG_SIZE))
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
            setSelectedImgSize(typedArray.getDimension(R.styleable.StatefulImg_sf_selected_img_size, ImgState.DEFAULT_IMG_SIZE))
        } else {
            setSelectedImgSize(imgState.normalImgSize)
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_selected_img_width)) {
            setSelectedImgWidth(typedArray.getDimension(R.styleable.StatefulImg_sf_selected_img_width, ImgState.INVALID_IMG_SIZE))
        } else {
            setSelectedImgWidth(imgState.normalImgWidth)
        }
        if (typedArray.hasValue(R.styleable.StatefulImg_sf_selected_img_height)) {
            setSelectedImgHeight(typedArray.getDimension(R.styleable.StatefulImg_sf_selected_img_height, ImgState.INVALID_IMG_SIZE))
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

    override fun setNormalImgSize(imgSize: Float) {
        if (imgState.normalImgSize != imgSize) {
            imgState.normalImgSize = imgSize
            updateImg()
        }
    }

    override fun setNormalImgWidth(imgWidth: Float) {
        if (imgState.normalImgWidth != imgWidth) {
            imgState.normalImgWidth = imgWidth
            updateImg()
        }
    }

    override fun setNormalImgHeight(imgHeight: Float) {
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

    override fun setPressedImgSize(imgSize: Float) {
        if (imgState.pressedImgSize != imgSize) {
            imgState.pressedImgSize = imgSize
            updateImg()
        }
    }

    override fun setPressedImgWidth(imgWidth: Float) {
        if (imgState.pressedImgWidth != imgWidth) {
            imgState.pressedImgWidth = imgWidth
            updateImg()
        }
    }

    override fun setPressedImgHeight(imgHeight: Float) {
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

    override fun setSelectedImgSize(imgSize: Float) {
        if (imgState.selectedImgSize != imgSize) {
            imgState.selectedImgSize = imgSize
            updateImg()
        }
    }

    override fun setSelectedImgWidth(imgWidth: Float) {
        if (imgState.selectedImgWidth != imgWidth) {
            imgState.selectedImgWidth = imgWidth
            updateImg()
        }
    }

    override fun setSelectedImgHeight(imgHeight: Float) {
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
                            if (imgState.selectedImgWidth != ImgState.INVALID_IMG_SIZE) imgState.selectedImgWidth.toInt() else imgState.selectedImgSize.toInt()
                        height =
                            if (imgState.selectedImgHeight != ImgState.INVALID_IMG_SIZE) imgState.selectedImgHeight.toInt() else imgState.selectedImgSize.toInt()
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
                            if (imgState.pressedImgWidth != ImgState.INVALID_IMG_SIZE) imgState.pressedImgWidth.toInt() else imgState.pressedImgSize.toInt()
                        height =
                            if (imgState.pressedImgHeight != ImgState.INVALID_IMG_SIZE) imgState.pressedImgHeight.toInt() else imgState.pressedImgSize.toInt()
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
                        if (imgState.normalImgWidth != ImgState.INVALID_IMG_SIZE) imgState.normalImgWidth.toInt() else imgState.normalImgSize.toInt()
                    height =
                        if (imgState.normalImgHeight != ImgState.INVALID_IMG_SIZE) imgState.normalImgHeight.toInt() else imgState.normalImgSize.toInt()
                }
                if (lp != null) {
                    attachedView?.layoutParams = lp
                }
            }
        }
    }
}

@Parcelize
private data class ImgState(
    var normalImg: Int = INVALID_IMG_ID,
    var normalImgTint: Int = INVALID_IMG_COLOR_TINT,
    var normalImgSize: Float = DEFAULT_IMG_SIZE,
    var normalImgWidth: Float = INVALID_IMG_SIZE,
    var normalImgHeight: Float = INVALID_IMG_SIZE,

    var pressedImg: Int = INVALID_IMG_ID,
    var pressedImgTint: Int = INVALID_IMG_COLOR_TINT,
    var pressedImgSize: Float = DEFAULT_IMG_SIZE,
    var pressedImgWidth: Float = INVALID_IMG_SIZE,
    var pressedImgHeight: Float = INVALID_IMG_SIZE,

    var selectedImg: Int = INVALID_IMG_ID,
    var selectedImgTint: Int = INVALID_IMG_COLOR_TINT,
    var selectedImgSize: Float = DEFAULT_IMG_SIZE,
    var selectedImgWidth: Float = INVALID_IMG_SIZE,
    var selectedImgHeight: Float = INVALID_IMG_SIZE
) : Parcelable {
    companion object {
        const val INVALID_IMG_ID = -1
        const val INVALID_IMG_COLOR_TINT = Color.TRANSPARENT
        const val INVALID_IMG_SIZE = -1F
        const val DEFAULT_IMG_SIZE = 64F
    }
}