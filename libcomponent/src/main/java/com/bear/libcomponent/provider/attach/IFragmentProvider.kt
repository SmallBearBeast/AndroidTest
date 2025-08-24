package com.bear.libcomponent.provider.attach

import androidx.fragment.app.Fragment
import com.bear.libcomponent.host.ComponentFragment

interface IFragmentProvider {
    fun attachFragment(fragment: ComponentFragment<*>?)

    val fragment: Fragment?
}
