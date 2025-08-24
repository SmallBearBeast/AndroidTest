package com.bear.libcomponent.component.ui

import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.bear.libcomponent.component.base.ViewBindingComponent

// 持有的ViewBinding的组件。
// 如果是Activity组件，持有的就是decorView。
// 如果是Fragment组件，持有的就是Fragment的View。
// 如果是Dialog组件，持有的就是Dialog的View。
// 如果是View组件，持有的就是View本身。
// 默认会继承Activity的View或者Fragment的View。
class ViewComponent<VB : ViewBinding> @JvmOverloads constructor(
    viewBinding: VB,
    lifecycle: Lifecycle? = null
) : ViewBindingComponent<VB>(lifecycle), View.OnAttachStateChangeListener {

    init {
        attachViewBinding(viewBinding)
        viewBinding.root.removeOnAttachStateChangeListener(this)
        viewBinding.root.addOnAttachStateChangeListener(this)
    }

    @CallSuper
    override fun onViewAttachedToWindow(view: View) {

    }

    @CallSuper
    override fun onViewDetachedFromWindow(view: View) {
        viewBinding?.root?.removeOnAttachStateChangeListener(this)
    }

    @JvmOverloads
    fun regComponent(component: ViewComponent<VB>, tag: Any? = null) {
        super.regComponent(component, tag)
    }
}
