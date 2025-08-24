package com.bear.libcomponent.component.ui

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.bear.libcomponent.host.ComponentFragment
import com.bear.libcomponent.component.base.ViewBindingComponent
import com.bear.libcomponent.provider.IBackPressedProvider
import com.bear.libcomponent.provider.attach.IFragmentProvider
import com.bear.libcomponent.provider.IMenuProvider

open class FragmentComponent<VB : ViewBinding> @JvmOverloads constructor(
    lifecycle: Lifecycle? = null
) : ViewBindingComponent<VB>(lifecycle), IFragmentProvider, IBackPressedProvider, IMenuProvider {
    private var componentFragment: ComponentFragment<*>? = null

    override val fragment: ComponentFragment<*>?
        get() = componentFragment

    val arguments: Bundle?
        get() = componentFragment?.arguments

    override fun attachFragment(fragment: ComponentFragment<*>?) {
        componentFragment = fragment
    }

    open fun onCreateView() {

    }

    open fun onDestroyView() {

    }

    open fun onFirstVisible() {

    }

    @JvmOverloads
    fun regComponent(component: FragmentComponent<VB>, tag: Any? = null) {
        super.regComponent(component, tag)
    }

    @JvmOverloads
    fun regComponent(component: ViewComponent<*>, tag: Any? = null) {
        super.regComponent(component, tag)
    }
}

