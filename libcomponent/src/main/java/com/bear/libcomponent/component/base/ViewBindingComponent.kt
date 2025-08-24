package com.bear.libcomponent.component.base

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.bear.libcomponent.component.ui.NonUIComponent
import com.bear.libcomponent.provider.attach.IViewBindingProvider

// 持有的ViewBinding的组件。
// 如果是Activity组件，持有的就是decorView。
// 如果是Fragment组件，持有的就是Fragment的View。
// 如果是Dialog组件，持有的就是Dialog的View。
// 如果是View组件，持有的就是View本身。
// 默认会继承Activity的View或者Fragment的View。
abstract class ViewBindingComponent<VB : ViewBinding> @JvmOverloads constructor(
    lifecycle: Lifecycle? = null
) : GroupComponent(lifecycle), IViewBindingProvider<VB> {
    private var componentViewBinding: VB? = null

    override val viewBinding: VB?
        get() = componentViewBinding

    @CallSuper
    override fun attachViewBinding(binding: VB) {
        componentViewBinding = binding
        for (component in componentMap.values) {
            if (component is ViewBindingComponent<*>) {
                // 需要转成ViewBinding，而不是VB，有可能子Component的VB类型与父Component类型不一致，会类型转化错误。
                val viewBindingComponent = component as ViewBindingComponent<VB>
                // 子Component没有VB对象，复用父Component的VB。
                if (viewBindingComponent.viewBinding == null) {
                    viewBindingComponent.attachViewBinding(binding)
                }
            }
        }
    }

    internal fun <C : ViewBindingComponent<VB>> regComponent(component: C, tag: Any?) {
        super.regComponent(component, tag)
        if (component.viewBinding == null) {
            viewBinding?.let {
                component.attachViewBinding(it)
            }
        }
    }

    fun <C : NonUIComponent> regComponent(component: C, tag: Any?) {
        super.regComponent(component, tag)
    }
}
