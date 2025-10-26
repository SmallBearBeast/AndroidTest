package com.example.administrator.androidtest.widget.stateful.delegate

import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.widget.stateful.IStateful
import com.example.administrator.androidtest.widget.stateful.IStatefulSubImg
import com.example.administrator.androidtest.widget.stateful.IStatefulView
import kotlinx.android.parcel.Parcelize

class StatefulSubImgDelegate(
    private val enableViewDelegate: Boolean = true,
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate()
) : IStatefulView by viewDelegate, IStatefulSubImg, IStateful {
    private var subImgState = SubImgState()

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
            setNormalSubImg(typedArray.getResourceId(R.styleable.StatefulSubImg_sf_sub_img, SubImgState.INVALID_IMG_ID))
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_sub_img_tint)) {
            setNormalSubImgTint(typedArray.getColor(R.styleable.StatefulSubImg_sf_sub_img_tint, SubImgState.INVALID_IMG_COLOR_TINT))
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_sub_img_size)) {
            setNormalSubImgSize(typedArray.getDimension(R.styleable.StatefulSubImg_sf_sub_img_size, SubImgState.DEFAULT_IMG_SIZE))
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_sub_img_width)) {
            setNormalSubImgWidth(typedArray.getDimension(R.styleable.StatefulSubImg_sf_sub_img_width, SubImgState.INVALID_IMG_SIZE))
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_sub_img_height)) {
            setNormalSubImgHeight(typedArray.getDimension(R.styleable.StatefulSubImg_sf_sub_img_height, SubImgState.INVALID_IMG_SIZE))
        }
    }

    private fun parsePressedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_pressed_sub_img)) {
            setPressedSubImg(typedArray.getResourceId(R.styleable.StatefulSubImg_sf_pressed_sub_img, SubImgState.INVALID_IMG_ID))
        } else {
            setPressedSubImg(subImgState.normalImg)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_pressed_sub_img_tint)) {
            setPressedSubImgTint(typedArray.getColor(R.styleable.StatefulSubImg_sf_pressed_sub_img_tint, SubImgState.INVALID_IMG_COLOR_TINT))
        } else {
            setPressedSubImgTint(subImgState.normalImgTint)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_pressed_sub_img_size)) {
            setPressedSubImgSize(typedArray.getDimension(R.styleable.StatefulSubImg_sf_pressed_sub_img_size, SubImgState.DEFAULT_IMG_SIZE))
        } else {
            setPressedSubImgSize(subImgState.normalImgSize)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_pressed_sub_img_width)) {
            setPressedSubImgWidth(typedArray.getDimension(R.styleable.StatefulSubImg_sf_pressed_sub_img_width, SubImgState.INVALID_IMG_SIZE))
        } else {
            setPressedSubImgWidth(subImgState.normalImgWidth)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_pressed_sub_img_height)) {
            setPressedSubImgHeight(typedArray.getDimension(R.styleable.StatefulSubImg_sf_pressed_sub_img_height, SubImgState.INVALID_IMG_SIZE))
        } else {
            setPressedSubImgHeight(subImgState.normalImgHeight)
        }
    }

    private fun parseSelectedAttrs(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_selected_sub_img)) {
            setSelectedSubImg(typedArray.getResourceId(R.styleable.StatefulSubImg_sf_selected_sub_img, SubImgState.INVALID_IMG_ID))
        } else {
            setSelectedSubImg(subImgState.normalImg)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_selected_sub_img_tint)) {
            setSelectedSubImgTint(typedArray.getColor(R.styleable.StatefulSubImg_sf_selected_sub_img_tint, SubImgState.INVALID_IMG_COLOR_TINT))
        } else {
            setSelectedSubImgTint(subImgState.normalImgTint)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_selected_sub_img_size)) {
            setSelectedSubImgSize(typedArray.getDimension(R.styleable.StatefulSubImg_sf_selected_sub_img_size, SubImgState.DEFAULT_IMG_SIZE))
        } else {
            setSelectedSubImgSize(subImgState.normalImgSize)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_selected_sub_img_width)) {
            setSelectedSubImgWidth(typedArray.getDimension(R.styleable.StatefulSubImg_sf_selected_sub_img_width, SubImgState.INVALID_IMG_SIZE))
        } else {
            setSelectedSubImgWidth(subImgState.normalImgWidth)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_selected_sub_img_height)) {
            setSelectedSubImgHeight(typedArray.getDimension(R.styleable.StatefulSubImg_sf_selected_sub_img_height, SubImgState.INVALID_IMG_SIZE))
        } else {
            setSelectedSubImgHeight(subImgState.normalImgHeight)
        }
    }

    override fun setNormalSubImg(imgResId: Int) {
        if (subImgState.normalImg != imgResId) {
            subImgState.normalImg = imgResId
            updateImg()
        }
    }

    override fun setNormalSubImgTint(tintColor: Int) {
        if (subImgState.normalImgTint != tintColor) {
            subImgState.normalImgTint = tintColor
            updateImg()
        }
    }

    override fun setNormalSubImgSize(imgSize: Float) {
        if (subImgState.normalImgSize != imgSize) {
            subImgState.normalImgSize = imgSize
            updateImg()
        }
    }

    override fun setNormalSubImgWidth(imgWidth: Float) {
        if (subImgState.normalImgWidth != imgWidth) {
            subImgState.normalImgWidth = imgWidth
            updateImg()
        }
    }

    override fun setNormalSubImgHeight(imgHeight: Float) {
        if (subImgState.normalImgHeight != imgHeight) {
            subImgState.normalImgHeight = imgHeight
            updateImg()
        }
    }

    override fun setPressedSubImg(imgResId: Int) {
        if (subImgState.pressedImg != imgResId) {
            subImgState.pressedImg = imgResId
            updateImg()
        }
    }

    override fun setPressedSubImgTint(tintColor: Int) {
        if (subImgState.pressedImgTint != tintColor) {
            subImgState.pressedImgTint = tintColor
            updateImg()
        }
    }

    override fun setPressedSubImgSize(imgSize: Float) {
        if (subImgState.pressedImgSize != imgSize) {
            subImgState.pressedImgSize = imgSize
            updateImg()
        }
    }

    override fun setPressedSubImgWidth(imgWidth: Float) {
        if (subImgState.pressedImgWidth != imgWidth) {
            subImgState.pressedImgWidth = imgWidth
            updateImg()
        }
    }

    override fun setPressedSubImgHeight(imgHeight: Float) {
        if (subImgState.pressedImgHeight != imgHeight) {
            subImgState.pressedImgHeight = imgHeight
            updateImg()
        }
    }

    override fun setSelectedSubImg(imgResId: Int) {
        if (subImgState.selectedImg != imgResId) {
            subImgState.selectedImg = imgResId
            updateImg()
        }
    }

    override fun setSelectedSubImgTint(tintColor: Int) {
        if (subImgState.selectedImgTint != tintColor) {
            subImgState.selectedImgTint = tintColor
            updateImg()
        }
    }

    override fun setSelectedSubImgSize(imgSize: Float) {
        if (subImgState.selectedImgSize != imgSize) {
            subImgState.selectedImgSize = imgSize
            updateImg()
        }
    }

    override fun setSelectedSubImgWidth(imgWidth: Float) {
        if (subImgState.selectedImgWidth != imgWidth) {
            subImgState.selectedImgWidth = imgWidth
            updateImg()
        }
    }

    override fun setSelectedSubImgHeight(imgHeight: Float) {
        if (subImgState.selectedImgHeight != imgHeight) {
            subImgState.selectedImgHeight = imgHeight
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
        if (enableViewDelegate) {
            viewDelegate.onSaveInstanceState(savedBundle)
        }
        savedBundle.putBundle("child_state", bundle)
        savedBundle.putParcelable("sub_img_state", subImgState)
    }

    override fun onRestoreInstanceState(restoredBundle: Bundle) {
        val bundle = restoredBundle.getBundle("child_state") ?: Bundle()
        if (enableViewDelegate) {
            viewDelegate.onRestoreInstanceState(bundle)
        }
        subImgState = restoredBundle.getParcelable("sub_img_state") ?: SubImgState()
        updateImg()
    }

    private fun updateImg(
        pressed: Boolean = attachedView?.isPressed ?: false,
        selected: Boolean = attachedView?.isSelected ?: false
    ) {
        when {
            selected -> {
                if (subImgState.selectedImg != SubImgState.INVALID_IMG_ID) {
                    attachedView?.setImageResource(subImgState.selectedImg)
                }
                if (subImgState.selectedImgTint != SubImgState.INVALID_IMG_COLOR_TINT) {
                    attachedView?.imageTintList = ColorStateList.valueOf(subImgState.selectedImgTint)
                }
                val lp = attachedView?.layoutParams?.apply {
                    width =
                        if (subImgState.selectedImgWidth != SubImgState.INVALID_IMG_SIZE) subImgState.selectedImgWidth.toInt() else subImgState.selectedImgSize.toInt()
                    height =
                        if (subImgState.selectedImgHeight != SubImgState.INVALID_IMG_SIZE) subImgState.selectedImgHeight.toInt() else subImgState.selectedImgSize.toInt()
                }
                if (lp != null) {
                    attachedView?.layoutParams = lp
                }
            }

            pressed -> {
                if (subImgState.pressedImg != SubImgState.INVALID_IMG_ID) {
                    attachedView?.setImageResource(subImgState.pressedImg)
                }
                if (subImgState.pressedImgTint != SubImgState.INVALID_IMG_COLOR_TINT) {
                    attachedView?.imageTintList = ColorStateList.valueOf(subImgState.pressedImgTint)
                }
                val lp = attachedView?.layoutParams?.apply {
                    width =
                        if (subImgState.pressedImgWidth != SubImgState.INVALID_IMG_SIZE) subImgState.pressedImgWidth.toInt() else subImgState.pressedImgSize.toInt()
                    height =
                        if (subImgState.pressedImgHeight != SubImgState.INVALID_IMG_SIZE) subImgState.pressedImgHeight.toInt() else subImgState.pressedImgSize.toInt()
                }
                if (lp != null) {
                    attachedView?.layoutParams = lp
                }
            }

            else -> {
                if (subImgState.normalImg != SubImgState.INVALID_IMG_ID) {
                    attachedView?.setImageResource(subImgState.normalImg)
                }
                if (subImgState.normalImgTint != SubImgState.INVALID_IMG_COLOR_TINT) {
                    attachedView?.imageTintList = ColorStateList.valueOf(subImgState.normalImgTint)
                }
                val lp = attachedView?.layoutParams?.apply {
                    width =
                        if (subImgState.normalImgWidth != SubImgState.INVALID_IMG_SIZE) subImgState.normalImgWidth.toInt() else subImgState.normalImgSize.toInt()
                    height =
                        if (subImgState.normalImgHeight != SubImgState.INVALID_IMG_SIZE) subImgState.normalImgHeight.toInt() else subImgState.normalImgSize.toInt()
                }
                if (lp != null) {
                    attachedView?.layoutParams = lp
                }
            }
        }
    }
}

@Parcelize
private data class SubImgState(
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
        const val INVALID_IMG_COLOR_TINT = -1
        const val INVALID_IMG_SIZE = -1F
        const val DEFAULT_IMG_SIZE = 64F
    }
}