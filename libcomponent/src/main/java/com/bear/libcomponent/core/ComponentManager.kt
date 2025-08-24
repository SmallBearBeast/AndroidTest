package com.bear.libcomponent.core

import android.content.Context
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.viewbinding.ViewBinding
import com.bear.libcomponent.component.base.GroupComponent
import com.bear.libcomponent.component.ui.ActivityComponent
import com.bear.libcomponent.component.ui.FragmentComponent
import com.bear.libcomponent.component.ui.NonUIComponent
import com.bear.libcomponent.component.ui.ViewComponent
import com.bear.libcomponent.host.ComponentActivity
import com.bear.libcomponent.host.ComponentFragment
import com.bear.libcomponent.provider.IBackPressedProvider
import com.bear.libcomponent.provider.IMenuProvider

internal class ComponentManager {
    private val componentContainer = ComponentContainer()

    fun <VB : ViewBinding> regComponent(activity: ComponentActivity<VB>, component: ActivityComponent<VB>, tag: Any?) {
        if (componentContainer.contain(component.javaClass, tag)) {
            throw RuntimeException("Can not register component with same type and tag")
        }
        if (component.context == null) {
            component.attachContext(activity)
        }
        if (component.activity == null) {
            component.attachActivity(activity)
        }
        if (component.lifecycle == null) {
            component.attachLifecycle(activity.lifecycle)
        }
        if (component.viewBinding == null) {
            activity.binding?.let { component.attachViewBinding(it) }
        }
        componentContainer.regComponent(component, tag)
    }

    fun regComponent(activity: ComponentActivity<*>, component: ViewComponent<*>, tag: Any?) {
        if (componentContainer.contain(component.javaClass, tag)) {
            throw RuntimeException("Can not register component with same type and tag")
        }
        if (component.context == null) {
            component.attachContext(activity)
        }
        if (component.lifecycle == null) {
            component.attachLifecycle(activity.lifecycle)
        }
        componentContainer.regComponent(component, tag)
    }

    fun regComponent(activity: ComponentActivity<*>, component: NonUIComponent, tag: Any?) {
        if (componentContainer.contain(component.javaClass, tag)) {
            throw RuntimeException("Can not register component with same type and tag")
        }
        if (component.context == null) {
            component.attachContext(activity)
        }
        if (component.lifecycle == null) {
            component.attachLifecycle(activity.lifecycle)
        }
        componentContainer.regComponent(component, tag)
    }

    fun <VB : ViewBinding> regComponent(fragment: ComponentFragment<VB>, component: FragmentComponent<VB>, tag: Any?) {
        if (componentContainer.contain(component.javaClass, tag)) {
            throw RuntimeException("Can not register component with same type and tag")
        }
        if (component.context == null && fragment.context != null) {
            component.attachContext(fragment.context)
        }
        if (component.fragment == null) {
            component.attachFragment(fragment)
        }
        if (component.lifecycle == null) {
            component.attachLifecycle(fragment.lifecycle)
        }
        if (component.viewBinding == null) {
            fragment.binding?.let { component.attachViewBinding(it) }
        }
        componentContainer.regComponent(component, tag)
    }

    fun regComponent(fragment: ComponentFragment<*>, component: ViewComponent<*>, tag: Any?) {
        if (componentContainer.contain(component.javaClass, tag)) {
            throw RuntimeException("Can not register component with same type and tag")
        }
        if (component.context == null && fragment.context != null) {
            component.attachContext(fragment.context)
        }
        if (component.lifecycle == null) {
            component.attachLifecycle(fragment.lifecycle)
        }
        componentContainer.regComponent(component, tag)
    }

    fun regComponent(fragment: ComponentFragment<*>, component: NonUIComponent, tag: Any?) {
        if (componentContainer.contain(component.javaClass, tag)) {
            throw RuntimeException("Can not register component with same type and tag")
        }
        if (component.context == null && fragment.context != null) {
            component.attachContext(fragment.context)
        }
        if (component.lifecycle == null) {
            component.attachLifecycle(fragment.lifecycle)
        }
        componentContainer.regComponent(component, tag)
    }

    fun <C : IComponent> getComponent(clz: Class<C>, tag: Any?): C? {
        return componentContainer.getComponent(clz, tag)
    }

    fun dispatchOnCreateView(componentFrag: ComponentFragment<*>, binding: ViewBinding?) {
        val componentMap = componentContainer.componentMap
        for (component in componentMap.values) {
            if (component is FragmentComponent<*> && component.fragment === componentFrag) {
                binding?.let {
                    if (component.viewBinding == null) {
                        (component as FragmentComponent<ViewBinding>).attachViewBinding(it)
                    }
                }
                component.onCreateView()
            }
        }
    }

    fun dispatchOnDestroyView(componentFrag: ComponentFragment<*>) {
        val componentMap = componentContainer.componentMap
        for (component in componentMap.values) {
            if (component is FragmentComponent<*> && component.fragment === componentFrag) {
                component.onDestroyView()
            }
        }
    }

    fun dispatchOnAttach(componentFrag: ComponentFragment<*>, context: Context?) {
        val componentMap = componentContainer.componentMap
        for (component in componentMap.values) {
            if (component is FragmentComponent<*> && component.fragment === componentFrag) {
                if (component.context == null) {
                    component.attachContext(context)
                }
            }
        }
    }

    fun dispatchOnFirstVisible(componentFrag: ComponentFragment<*>) {
        val componentMap = componentContainer.componentMap
        for (component in componentMap.values) {
            if (component is FragmentComponent<*> && component.fragment === componentFrag) {
                component.onFirstVisible()
            }
        }
    }

    fun dispatchOnBackPressed() {
        val rootComponent = componentContainer.rootComponent
        rootComponent.traverseAndConsume {
            if (it is IBackPressedProvider) {
                it.onBackPressed()
            }
            false
        }
    }

    fun dispatchOnCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater): Boolean {
        val rootComponent = componentContainer.rootComponent
        return rootComponent.traverseAndConsume {
            if (it is IMenuProvider) {
                return@traverseAndConsume it.onCreateOptionsMenu(menu, menuInflater)
            }
            false
        }
    }

    fun dispatchOnOptionsItemSelected(item: MenuItem): Boolean {
        val rootComponent = componentContainer.rootComponent
        return rootComponent.traverseAndConsume {
            if (it is IMenuProvider) {
                return@traverseAndConsume it.onOptionsItemSelected(item)
            }
            false
        }
    }

    fun dispatchOnCreateContextMenu(menu: ContextMenu, view: View, menuInfo: ContextMenuInfo) {
        val rootComponent = componentContainer.rootComponent
        rootComponent.traverseAndConsume {
            if (it is IMenuProvider) {
                (it as IMenuProvider).onCreateContextMenu(menu, view, menuInfo)
            }
            false
        }
    }

    fun dispatchOnContextItemSelected(item: MenuItem): Boolean {
        val rootComponent = componentContainer.rootComponent
        return rootComponent.traverseAndConsume {
            if (it is IMenuProvider) {
                return@traverseAndConsume it.onContextItemSelected(item)
            }
            false
        }
    }

    private fun IComponent.traverseAndConsume(action: (IComponent) -> Boolean): Boolean {
        val consumed = action(this)
        if (consumed) {
            return true
        }

        if (this is GroupComponent) {
            for (childComponent in componentMap.values) {
                if (childComponent.traverseAndConsume(action)) {
                    return true
                }
            }
        }
        return false
    }
}