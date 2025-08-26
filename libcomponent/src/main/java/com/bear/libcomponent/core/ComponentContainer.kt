package com.bear.libcomponent.core

import com.bear.libcomponent.component.base.ComponentKey
import com.bear.libcomponent.component.base.GroupComponent

internal class ComponentContainer {
    val rootComponent = RootComponent()

    val componentMap: Map<ComponentKey<*>, IComponent>
        get() = rootComponent.componentMap

    fun <C : IComponent> regComponent(component: C, tag: Any? = null) {
        rootComponent.regComponent(component, tag)
    }

    fun <C : IComponent> getComponent(clz: Class<C>, tag: Any? = null): C? {
        return rootComponent.getComponent(clz, tag)
    }

    fun <C : IComponent> contain(clz: Class<C>, tag: Any? = null): Boolean {
        return rootComponent.getComponent(clz, tag) != null
    }
}

internal class RootComponent : GroupComponent()