package com.example.administrator.androidtest.widget.stateful.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.widget.stateful.IStateful
import com.example.administrator.androidtest.widget.stateful.IStateful2Img
import com.example.administrator.androidtest.widget.stateful.IStateful2Text
import com.example.administrator.androidtest.widget.stateful.IStatefulImg
import com.example.administrator.androidtest.widget.stateful.IStatefulImgText
import com.example.administrator.androidtest.widget.stateful.IStatefulSubImg
import com.example.administrator.androidtest.widget.stateful.IStatefulSubText
import com.example.administrator.androidtest.widget.stateful.IStatefulText
import com.example.administrator.androidtest.widget.stateful.IStatefulView
import com.example.administrator.androidtest.widget.stateful.delegate.Stateful2ImgDelegate
import com.example.administrator.androidtest.widget.stateful.delegate.Stateful2TextDelegate
import com.example.administrator.androidtest.widget.stateful.delegate.StatefulImgDelegate
import com.example.administrator.androidtest.widget.stateful.delegate.StatefulImgTextDelegate
import com.example.administrator.androidtest.widget.stateful.delegate.StatefulTextDelegate

class StatefulTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val viewDelegate: StatefulTextDelegate = StatefulTextDelegate()
) : AppCompatTextView(context, attrs, defStyleAttr), IStatefulView by viewDelegate, IStatefulText by viewDelegate, IStateful by viewDelegate {
    init {
        attachView(this)
        initAttributeSet(attrs)
    }

    override fun dispatchSetPressed(pressed: Boolean) {
        super.dispatchSetPressed(pressed)
        onPressedChanged(pressed)
    }

    override fun dispatchSetSelected(selected: Boolean) {
        super.dispatchSetSelected(selected)
        onSelectedChanged(selected)
    }
}

class StatefulImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val viewDelegate: StatefulImgDelegate = StatefulImgDelegate()
) : AppCompatImageView(context, attrs, defStyleAttr), IStatefulView by viewDelegate, IStatefulImg by viewDelegate, IStateful by viewDelegate {
    init {
        attachView(this)
        initAttributeSet(attrs)
    }

    override fun dispatchSetPressed(pressed: Boolean) {
        super.dispatchSetPressed(pressed)
        onPressedChanged(pressed)
    }

    override fun dispatchSetSelected(selected: Boolean) {
        super.dispatchSetSelected(selected)
        onSelectedChanged(selected)
    }

    override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
        val oldParams = layoutParams
        super.setLayoutParams(params)
        // 触发监听（如果有 listener 且新旧参数不同）
        if (!areLayoutParamsEqual(oldParams, params)) {
            onLayoutParamsChanged()
        }
    }

    private fun areLayoutParamsEqual(oldParams: ViewGroup.LayoutParams?, newParams: ViewGroup.LayoutParams?): Boolean {
        if (oldParams == null && newParams == null) return true
        if (oldParams == null || newParams == null) return false
        // 这里仅判断宽高，可根据需要添加margin等其他属性的判断
        return oldParams.width == newParams.width && oldParams.height == newParams.height
    }
}

class StatefulEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val viewDelegate: StatefulTextDelegate = StatefulTextDelegate()
) : AppCompatEditText(context, attrs, defStyleAttr), IStatefulView by viewDelegate, IStatefulText by viewDelegate, IStateful by viewDelegate {
    init {
        attachView(this)
        initAttributeSet(attrs)
    }

    override fun dispatchSetPressed(pressed: Boolean) {
        super.dispatchSetPressed(pressed)
        onPressedChanged(pressed)
    }

    override fun dispatchSetSelected(selected: Boolean) {
        super.dispatchSetSelected(selected)
        onSelectedChanged(selected)
    }
}

class Stateful2TextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val viewDelegate: Stateful2TextDelegate = Stateful2TextDelegate()
) : LinearLayout(context, attrs, defStyleAttr), IStatefulView by viewDelegate, IStatefulText by viewDelegate, IStatefulSubText by viewDelegate,
    IStateful2Text by viewDelegate, IStateful by viewDelegate {
    init {
        inflate(context, R.layout.view_stateful_2_text, this)
        gravity = Gravity.CENTER
        attachView(this)
        initAttributeSet(attrs)
    }

    override fun dispatchSetPressed(pressed: Boolean) {
        super.dispatchSetPressed(pressed)
        onPressedChanged(pressed)
    }

    override fun dispatchSetSelected(selected: Boolean) {
        super.dispatchSetSelected(selected)
        onSelectedChanged(selected)
    }

    override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
        val oldParams = layoutParams
        super.setLayoutParams(params)
        // 触发监听（如果有 listener 且新旧参数不同）
        if (!areLayoutParamsEqual(oldParams, params)) {
            onLayoutParamsChanged()
        }
    }

    private fun areLayoutParamsEqual(oldParams: ViewGroup.LayoutParams?, newParams: ViewGroup.LayoutParams?): Boolean {
        if (oldParams == null && newParams == null) return true
        if (oldParams == null || newParams == null) return false
        // 这里仅判断宽高，可根据需要添加margin等其他属性的判断
        return oldParams.width == newParams.width && oldParams.height == newParams.height
    }
}

class Stateful2ImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val viewDelegate: Stateful2ImgDelegate = Stateful2ImgDelegate()
) : LinearLayout(context, attrs, defStyleAttr), IStatefulView by viewDelegate, IStatefulImg by viewDelegate, IStatefulSubImg by viewDelegate,
    IStateful2Img by viewDelegate, IStateful by viewDelegate {
    init {
        inflate(context, R.layout.view_stateful_2_img, this)
        gravity = Gravity.CENTER
        attachView(this)
        initAttributeSet(attrs)
    }

    override fun dispatchSetPressed(pressed: Boolean) {
        super.dispatchSetPressed(pressed)
        onPressedChanged(pressed)
    }

    override fun dispatchSetSelected(selected: Boolean) {
        super.dispatchSetSelected(selected)
        onSelectedChanged(selected)
    }

    override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
        val oldParams = layoutParams
        super.setLayoutParams(params)
        // 触发监听（如果有 listener 且新旧参数不同）
        if (!areLayoutParamsEqual(oldParams, params)) {
            onLayoutParamsChanged()
        }
    }

    private fun areLayoutParamsEqual(oldParams: ViewGroup.LayoutParams?, newParams: ViewGroup.LayoutParams?): Boolean {
        if (oldParams == null && newParams == null) return true
        if (oldParams == null || newParams == null) return false
        // 这里仅判断宽高，可根据需要添加margin等其他属性的判断
        return oldParams.width == newParams.width && oldParams.height == newParams.height
    }
}

class StatefulImgTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val viewDelegate: StatefulImgTextDelegate = StatefulImgTextDelegate()
) : LinearLayout(context, attrs, defStyleAttr), IStatefulView by viewDelegate, IStatefulText by viewDelegate, IStatefulImg by viewDelegate,
    IStatefulImgText by viewDelegate, IStateful by viewDelegate {
    init {
        inflate(context, R.layout.view_stateful_img_text, this)
        gravity = Gravity.CENTER
        attachView(this)
        initAttributeSet(attrs)
    }

    override fun dispatchSetPressed(pressed: Boolean) {
        super.dispatchSetPressed(pressed)
        onPressedChanged(pressed)
    }

    override fun dispatchSetSelected(selected: Boolean) {
        super.dispatchSetSelected(selected)
        onSelectedChanged(selected)
    }

    override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
        val oldParams = layoutParams
        super.setLayoutParams(params)
        // 触发监听（如果有 listener 且新旧参数不同）
        if (!areLayoutParamsEqual(oldParams, params)) {
            onLayoutParamsChanged()
        }
    }

    private fun areLayoutParamsEqual(oldParams: ViewGroup.LayoutParams?, newParams: ViewGroup.LayoutParams?): Boolean {
        if (oldParams == null && newParams == null) return true
        if (oldParams == null || newParams == null) return false
        // 这里仅判断宽高，可根据需要添加margin等其他属性的判断
        return oldParams.width == newParams.width && oldParams.height == newParams.height
    }
}
