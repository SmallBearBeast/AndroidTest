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
    viewDelegate: StatefulTextDelegate = StatefulTextDelegate()
) : BaseStatefulTextView<StatefulTextDelegate>(
    context, attrs, defStyleAttr, viewDelegate
), IStatefulView by viewDelegate, IStatefulText by viewDelegate

class StatefulImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    viewDelegate: StatefulImgDelegate = StatefulImgDelegate()
) : BaseStatefulImageView<StatefulImgDelegate>(
    context, attrs, defStyleAttr, viewDelegate
), IStatefulView by viewDelegate, IStatefulImg by viewDelegate

class StatefulEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    viewDelegate: StatefulTextDelegate = StatefulTextDelegate()
) : BaseStatefulEditText<StatefulTextDelegate>(
    context, attrs, defStyleAttr, viewDelegate
), IStatefulView by viewDelegate, IStatefulText by viewDelegate

class Stateful2TextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    viewDelegate: Stateful2TextDelegate = Stateful2TextDelegate()
) : BaseStatefulLinearLayout<Stateful2TextDelegate>(
    context, attrs, defStyleAttr, viewDelegate, R.layout.view_stateful_2_text
), IStatefulView by viewDelegate, IStatefulText by viewDelegate, IStatefulSubText by viewDelegate, IStateful2Text by viewDelegate

class Stateful2ImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    viewDelegate: Stateful2ImgDelegate = Stateful2ImgDelegate()
) : BaseStatefulLinearLayout<Stateful2ImgDelegate>(
    context, attrs, defStyleAttr, viewDelegate, R.layout.view_stateful_2_img
), IStatefulView by viewDelegate, IStatefulImg by viewDelegate, IStatefulSubImg by viewDelegate,
    IStateful2Img by viewDelegate

class StatefulImgTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    viewDelegate: StatefulImgTextDelegate = StatefulImgTextDelegate()
) : BaseStatefulLinearLayout<StatefulImgTextDelegate>(
    context, attrs, defStyleAttr, viewDelegate, R.layout.view_stateful_img_text
), IStatefulView by viewDelegate, IStatefulText by viewDelegate, IStatefulImg by viewDelegate,
    IStatefulImgText by viewDelegate

abstract class BaseStatefulTextView<Delegate : IStateful>(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    protected val viewDelegate: Delegate
) : AppCompatTextView(context, attrs, defStyleAttr),
    IStateful by viewDelegate {

    init {
        viewDelegate.attachView(this)
        viewDelegate.initAttributeSet(attrs)
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

abstract class BaseStatefulEditText<Delegate : IStateful>(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    protected val viewDelegate: Delegate
) : AppCompatEditText(context, attrs, defStyleAttr),
    IStateful by viewDelegate {

    init {
        viewDelegate.attachView(this)
        viewDelegate.initAttributeSet(attrs)
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

abstract class BaseStatefulImageView<Delegate : IStateful>(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    protected val viewDelegate: Delegate
) : AppCompatImageView(context, attrs, defStyleAttr),
    IStateful by viewDelegate {

    init {
        viewDelegate.attachView(this)
        viewDelegate.initAttributeSet(attrs)
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
        if (!areLayoutParamsEqual(oldParams, params)) {
            onLayoutParamsChanged()
        }
    }
}

abstract class BaseStatefulLinearLayout<Delegate : IStateful>(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    protected val viewDelegate: Delegate,
    layoutResId: Int // 子类布局ID
) : LinearLayout(context, attrs, defStyleAttr),
    IStateful by viewDelegate {

    init {
        // 统一加载布局、设置重心
        inflate(context, layoutResId, this)
        gravity = Gravity.CENTER
        // 统一初始化
        viewDelegate.attachView(this)
        viewDelegate.initAttributeSet(attrs)
    }

    override fun dispatchSetPressed(pressed: Boolean) {
        super.dispatchSetPressed(pressed)
        onPressedChanged(pressed)
    }

    override fun dispatchSetSelected(selected: Boolean) {
        super.dispatchSetSelected(selected)
        onSelectedChanged(selected)
    }

    // 统一监听LayoutParams变化（与ImageView逻辑一致）
    override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
        val oldParams = layoutParams
        super.setLayoutParams(params)
        if (!areLayoutParamsEqual(oldParams, params)) {
            onLayoutParamsChanged()
        }
    }
}

private fun areLayoutParamsEqual(oldParams: ViewGroup.LayoutParams?, newParams: ViewGroup.LayoutParams?): Boolean {
    if (oldParams == null && newParams == null) return true
    if (oldParams == null || newParams == null) return false
    return oldParams.width == newParams.width && oldParams.height == newParams.height
}