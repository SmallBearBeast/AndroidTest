package com.bear.libcomponent.component.ui

import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.bear.libcomponent.host.ComponentActivity
import com.bear.libcomponent.component.base.ViewBindingComponent
import com.bear.libcomponent.provider.attach.IActivityProvider
import com.bear.libcomponent.provider.IBackPressedProvider
import com.bear.libcomponent.provider.IMenuProvider

open class ActivityComponent<VB : ViewBinding> @JvmOverloads constructor(
    lifecycle: Lifecycle? = null
) : ViewBindingComponent<VB>(lifecycle), IActivityProvider, IBackPressedProvider, IMenuProvider {
    private var componentActivity: ComponentActivity<*>? = null

    override val activity: ComponentActivity<*>?
        get() = componentActivity

    override fun attachActivity(activity: ComponentActivity<*>?) {
        componentActivity = activity
    }

    @JvmOverloads
    fun regComponent(component: ActivityComponent<VB>, tag: Any? = null) {
        super.regComponent(component, tag)
    }

    @JvmOverloads
    fun regComponent(component: ViewComponent<*>, tag: Any? = null) {
        super.regComponent(component, tag)
    }
}