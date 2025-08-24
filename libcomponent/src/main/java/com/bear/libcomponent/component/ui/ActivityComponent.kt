package com.bear.libcomponent.component.ui

import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.bear.libcomponent.component.base.ViewBindingComponent
import com.bear.libcomponent.host.ComponentActivity
import com.bear.libcomponent.provider.IBackPressedProvider
import com.bear.libcomponent.provider.IMenuProvider
import com.bear.libcomponent.provider.attach.IActivityProvider

open class ActivityComponent<VB : ViewBinding> @JvmOverloads constructor(
    lifecycle: Lifecycle? = null
) : ViewBindingComponent<VB>(lifecycle), IActivityProvider, IBackPressedProvider, IMenuProvider {
    private var componentActivity: ComponentActivity<*>? = null

    final override val activity: ComponentActivity<*>?
        get() = componentActivity

    final override fun requireActivity() = componentActivity!!

    override fun attachActivity(activity: ComponentActivity<*>?) {
        componentActivity = activity
        for (component in componentMap.values) {
            if (component is ActivityComponent<*>) {
                if (component.activity == null) {
                    component.attachActivity(activity)
                }
            }
        }
    }

    @JvmOverloads
    fun regComponent(component: ActivityComponent<VB>, tag: Any? = null) {
        super.regComponent(component, tag)
        if (component.activity == null) {
            component.attachActivity(activity)
        }
    }

    @JvmOverloads
    fun regComponent(component: ViewComponent<*>, tag: Any? = null) {
        super.regComponent(component, tag)
    }
}