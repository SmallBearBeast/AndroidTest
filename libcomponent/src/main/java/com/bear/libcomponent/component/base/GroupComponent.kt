package com.bear.libcomponent.component.base

import android.content.Context
import androidx.lifecycle.Lifecycle
import com.bear.libcomponent.core.IComponent

abstract class GroupComponent @JvmOverloads constructor(
    lifecycle: Lifecycle? = null
) : LifeComponent(lifecycle) {
    private var parentComponent: GroupComponent? = null

    internal val componentMap: MutableMap<ComponentKey<*>, IComponent> = HashMap()

    private val pendingComponentMap = hashMapOf<ComponentKey<*>, (component: IComponent) -> Unit>()

    override fun attachContext(context: Context?) {
        super.attachContext(context)
        for (component in componentMap.values) {
            if (component is ContextComponent) {
                if (component.context == null) {
                    component.attachContext(context)
                }
            }
        }
    }

    override fun attachLifecycle(lifecycle: Lifecycle?) {
        super.attachLifecycle(lifecycle)
        for (component in componentMap.values) {
            if (component is LifeComponent) {
                if (component.lifecycle == null) {
                    component.attachLifecycle(lifecycle)
                }
            }
        }
    }

    @JvmOverloads
    internal fun <C : IComponent> regComponent(component: C, tag: Any? = null) {
        if (getComponent(component.javaClass, tag) != null) {
            throw RuntimeException("Can not register component with same type and tag")
        }
        val componentKey = ComponentKey(component.javaClass, tag)
        if (component is ContextComponent) {
            if (component.context == null) {
                component.attachContext(context)
            }
            component.attachComponentKey(componentKey)
        }
        if (component is LifeComponent) {
            if (component.lifecycle == null) {
                component.attachLifecycle(lifecycle)
            }
            component.addLifecycleObserver { _, event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    componentMap.remove(componentKey)
                }
            }
        }
        (component as? GroupComponent)?.parentComponent = this
        componentMap[componentKey] = component
        pendingComponentMap.entries.find {
            it.key.tag == componentKey.tag && it.key.clz.isAssignableFrom(componentKey.clz)
        }?.let {
            it.value.invoke(component)
            pendingComponentMap.remove(componentKey)
        }
//        pendingComponentMap[componentKey]?.let { action ->
//            if (component is LifeComponent) {
//                component.addLifecycleObserver { _, event ->
//                    if (event == Lifecycle.Event.ON_RESUME) {
//                        action(component)
//                        pendingComponentMap.remove(componentKey)
//                        component.removeLifecycleObserver(this)
//                    }
//                }
//            }
//        }
    }

    @JvmOverloads
    fun <C : IComponent> unRegComponent(clz: Class<C>, tag: Any? = null) {
        val targetKey = ComponentKey(clz, tag)
        componentMap.remove(targetKey)
    }

    @JvmOverloads
    fun <C : IComponent> getComponent(clz: Class<C>, tag: Any? = null, onComponentReady: (component: C) -> Unit) {
        // 首先在当前组件中查找
        val targetKey = ComponentKey(clz, tag)
        var targetComponent = findInComponentTree<IComponent>(targetKey)
        if (targetComponent != null) {
            (targetComponent as? C)?.let {
                onComponentReady(it)
            }
        }

        targetComponent = findInParentComponentTree(targetKey)
        if (targetComponent != null) {
            (targetComponent as? C)?.let {
                onComponentReady(it)
            }
        }

        pendingComponentMap[targetKey] = { comp ->
            (comp as? C)?.let(onComponentReady)
        }
    }

    @JvmOverloads
    fun <C : IComponent> getComponent(clz: Class<C>, tag: Any? = null): C? {
        // 首先在当前组件中查找
        val targetKey = ComponentKey(clz, tag)
        var targetComponent = findInComponentTree<IComponent>(targetKey)
        if (targetComponent != null) {
            return targetComponent as? C
        }

        targetComponent = findInParentComponentTree(targetKey)
        if (targetComponent != null) {
            return targetComponent as? C
        }

        return null
    }

    private fun <C : IComponent> findInComponentTree(targetKey: ComponentKey<*>, excludeComponent: IComponent? = null): C? {
        key?.let {
            if (it.tag == targetKey.tag && targetKey.clz.isAssignableFrom(it.clz)) {
                return this as? C
            }
        }

        componentMap.entries.find {
            it.key.tag == targetKey.tag && targetKey.clz.isAssignableFrom(it.key.clz)
        }?.let {
            return it.value as? C
        }

        for (component in componentMap.values) {
            if (component === excludeComponent) {
                continue
            }
            if (component is GroupComponent) {
                val targetComponent = component.findInComponentTree<IComponent>(targetKey, excludeComponent)
                if (targetComponent != null) {
                    return targetComponent as? C
                }
            }

        }
        return null
    }

    // 向上遍历父组件
    private fun <C : IComponent> findInParentComponentTree(targetKey: ComponentKey<*>): C? {
        var targetComponent: C? = null
        var pComponent = parentComponent
        var excludeComponent = this
        while (targetComponent == null && pComponent != null) {
            targetComponent = pComponent.findInComponentTree(targetKey, excludeComponent)
            excludeComponent = pComponent
            pComponent = pComponent.parentComponent
        }
        return targetComponent
    }
}

data class ComponentKey<C : IComponent>(
    val clz: Class<C>,
    val tag: Any?
)
