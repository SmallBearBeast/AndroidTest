package com.bear.libcomponent.host

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.bear.libbase.fragment.BaseFragment
import com.bear.libcomponent.component.base.GroupComponent
import com.bear.libcomponent.component.ui.FragmentComponent
import com.bear.libcomponent.component.ui.NonUIComponent
import com.bear.libcomponent.component.ui.ViewComponent
import com.bear.libcomponent.core.ComponentManager

abstract class ComponentFragment<VB : ViewBinding> : BaseFragment<VB>() {
    private val componentManager: ComponentManager
        get() {
            if (activity is ComponentActivity<*>) {
                return (activity as ComponentActivity<*>).componentManager
            }
            throw RuntimeException("getComponentManager return null")
        }

    @CallSuper
    override fun onAttach(context: Context) {
        super.onAttach(context)
        componentManager.dispatchOnAttach(this, context)
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = super.onCreateView(inflater, container, savedInstanceState)
        componentManager.dispatchOnCreateView(this, binding)
        return contentView
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        componentManager.dispatchOnDestroyView(this)
    }

    @CallSuper
    override fun onDetach() {
        super.onDetach()
        componentManager.dispatchOnDetach(this)
    }

    override fun onFirstVisible() {
        componentManager.dispatchOnFirstVisible(this)
    }

    @JvmOverloads
    fun regComponent(component: FragmentComponent<VB>, tag: Any? = null) {
        componentManager.regComponent(this, component, tag)
    }

    @JvmOverloads
    fun regComponent(component: ViewComponent<*>, tag: Any? = null) {
        componentManager.regComponent(requireContext(), component, tag)
    }

    @JvmOverloads
    fun regComponent(component: NonUIComponent, tag: Any? = null) {
        componentManager.regComponent(requireContext(), component, tag)
    }

    @JvmOverloads
    fun <C : GroupComponent> getComponent(clz: Class<C>, tag: Any? = null): C? {
        return componentManager.getComponent(clz, tag)
    }

    abstract override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
}
