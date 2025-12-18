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
import com.example.administrator.androidtest.widget.stateful.IStatefulSubImg
import com.example.administrator.androidtest.widget.stateful.IStatefulView
import kotlinx.android.parcel.Parcelize

class StatefulSubImgDelegate(
    enableViewDelegate: Boolean = true,
    private val viewDelegate: StatefulViewDelegate = StatefulViewDelegate(enableViewDelegate)
) : BaseViewDelegate<ImageView>(), IStatefulView by viewDelegate, IStatefulSubImg {
    private var subImgState = SubImgState()


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
            setNormalSubImgSize(typedArray.getImgSize(R.styleable.StatefulSubImg_sf_sub_img_size))
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_sub_img_width)) {
            setNormalSubImgWidth(typedArray.getImgSize(R.styleable.StatefulSubImg_sf_sub_img_width))
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_sub_img_height)) {
            setNormalSubImgHeight(typedArray.getImgSize(R.styleable.StatefulSubImg_sf_sub_img_height))
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
            setPressedSubImgSize(typedArray.getImgSize(R.styleable.StatefulSubImg_sf_pressed_sub_img_size))
        } else {
            setPressedSubImgSize(subImgState.normalImgSize)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_pressed_sub_img_width)) {
            setPressedSubImgWidth(typedArray.getImgSize(R.styleable.StatefulSubImg_sf_pressed_sub_img_width))
        } else {
            setPressedSubImgWidth(subImgState.normalImgWidth)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_pressed_sub_img_height)) {
            setPressedSubImgHeight(typedArray.getImgSize(R.styleable.StatefulSubImg_sf_pressed_sub_img_height))
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
            setSelectedSubImgSize(typedArray.getImgSize(R.styleable.StatefulSubImg_sf_selected_sub_img_size))
        } else {
            setSelectedSubImgSize(subImgState.normalImgSize)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_selected_sub_img_width)) {
            setSelectedSubImgWidth(typedArray.getImgSize(R.styleable.StatefulSubImg_sf_selected_sub_img_width))
        } else {
            setSelectedSubImgWidth(subImgState.normalImgWidth)
        }
        if (typedArray.hasValue(R.styleable.StatefulSubImg_sf_selected_sub_img_height)) {
            setSelectedSubImgHeight(typedArray.getImgSize(R.styleable.StatefulSubImg_sf_selected_sub_img_height))
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

    override fun setNormalSubImgSize(imgSize: Int) {
        if (subImgState.normalImgSize != imgSize) {
            subImgState.normalImgSize = imgSize
            updateImg()
        }
    }

    override fun setNormalSubImgWidth(imgWidth: Int) {
        if (subImgState.normalImgWidth != imgWidth) {
            subImgState.normalImgWidth = imgWidth
            updateImg()
        }
    }

    override fun setNormalSubImgHeight(imgHeight: Int) {
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

    override fun setPressedSubImgSize(imgSize: Int) {
        if (subImgState.pressedImgSize != imgSize) {
            subImgState.pressedImgSize = imgSize
            updateImg()
        }
    }

    override fun setPressedSubImgWidth(imgWidth: Int) {
        if (subImgState.pressedImgWidth != imgWidth) {
            subImgState.pressedImgWidth = imgWidth
            updateImg()
        }
    }

    override fun setPressedSubImgHeight(imgHeight: Int) {
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

    override fun setSelectedSubImgSize(imgSize: Int) {
        if (subImgState.selectedImgSize != imgSize) {
            subImgState.selectedImgSize = imgSize
            updateImg()
        }
    }

    override fun setSelectedSubImgWidth(imgWidth: Int) {
        if (subImgState.selectedImgWidth != imgWidth) {
            subImgState.selectedImgWidth = imgWidth
            updateImg()
        }
    }

    override fun setSelectedSubImgHeight(imgHeight: Int) {
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
        viewDelegate.onSaveInstanceState(savedBundle)
        savedBundle.putBundle("child_state", bundle)
        savedBundle.putParcelable("sub_img_state", subImgState)
    }

    override fun onRestoreInstanceState(restoredBundle: Bundle) {
        val bundle = restoredBundle.getBundle("child_state") ?: Bundle()
        viewDelegate.onRestoreInstanceState(bundle)
        subImgState = restoredBundle.getParcelable("sub_img_state") ?: SubImgState()
        updateImg()
    }

    private fun updateImg(
        pressed: Boolean = attachedView?.isPressed ?: false,
        selected: Boolean = attachedView?.isSelected ?: false
    ) {
        when {
            selected -> {
                if (isSelectedEnable) {
                    if (subImgState.selectedImg != SubImgState.INVALID_IMG_ID) {
                        attachedView?.setImageResource(subImgState.selectedImg)
                    }
                    if (subImgState.selectedImgTint != SubImgState.INVALID_IMG_COLOR_TINT) {
                        attachedView?.imageTintList = ColorStateList.valueOf(subImgState.selectedImgTint)
                    }
                    val lp = attachedView?.layoutParams?.apply {
                        width =
                            if (subImgState.selectedImgWidth != SubImgState.INVALID_IMG_SIZE) subImgState.selectedImgWidth else subImgState.selectedImgSize
                        height =
                            if (subImgState.selectedImgHeight != SubImgState.INVALID_IMG_SIZE) subImgState.selectedImgHeight else subImgState.selectedImgSize
                    }
                    if (lp != null) {
                        attachedView?.layoutParams = lp
                    }
                }
            }

            pressed -> {
                if (isPressedEnable) {
                    if (subImgState.pressedImg != SubImgState.INVALID_IMG_ID) {
                        attachedView?.setImageResource(subImgState.pressedImg)
                    }
                    if (subImgState.pressedImgTint != SubImgState.INVALID_IMG_COLOR_TINT) {
                        attachedView?.imageTintList = ColorStateList.valueOf(subImgState.pressedImgTint)
                    }
                    val lp = attachedView?.layoutParams?.apply {
                        width =
                            if (subImgState.pressedImgWidth != SubImgState.INVALID_IMG_SIZE) subImgState.pressedImgWidth else subImgState.pressedImgSize
                        height =
                            if (subImgState.pressedImgHeight != SubImgState.INVALID_IMG_SIZE) subImgState.pressedImgHeight else subImgState.pressedImgSize
                    }
                    if (lp != null) {
                        attachedView?.layoutParams = lp
                    }
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
                        if (subImgState.normalImgWidth != SubImgState.INVALID_IMG_SIZE) subImgState.normalImgWidth else subImgState.normalImgSize
                    height =
                        if (subImgState.normalImgHeight != SubImgState.INVALID_IMG_SIZE) subImgState.normalImgHeight else subImgState.normalImgSize
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
private data class SubImgState(
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