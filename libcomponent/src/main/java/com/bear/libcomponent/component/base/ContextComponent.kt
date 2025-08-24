package com.bear.libcomponent.component.base

import android.content.Context
import com.bear.libcomponent.core.IComponent
import com.bear.libcomponent.host.ComponentActivity
import com.bear.libcomponent.provider.attach.IComponentKeyProvider
import com.bear.libcomponent.provider.attach.IContextProvider

abstract class ContextComponent : IContextProvider, IComponentKeyProvider, IComponent {
    private var componentContext: Context? = null

    private var componentKey: ComponentKey<*>? = null

    final override val key: ComponentKey<*>?
        get() = componentKey

    final override val context: Context?
        get() = componentContext

    override val activity: ComponentActivity<*>?
        get() = componentContext as? ComponentActivity<*>

    override fun requireContext() = componentContext!!

    override fun attachComponentKey(key: ComponentKey<*>?) {
        componentKey = key
    }

    override fun attachContext(context: Context?) {
        componentContext = context
    }
}
