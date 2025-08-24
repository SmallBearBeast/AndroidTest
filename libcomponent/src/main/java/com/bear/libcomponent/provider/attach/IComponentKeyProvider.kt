package com.bear.libcomponent.provider.attach

import com.bear.libcomponent.component.base.ComponentKey

internal interface IComponentKeyProvider {
    fun attachComponentKey(key: ComponentKey<*>?)

    val key: ComponentKey<*>?
}
