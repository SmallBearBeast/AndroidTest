package com.bear.libcomponent.provider.attach

import com.bear.libcomponent.host.ComponentActivity

interface IActivityProvider {
    fun attachActivity(activity: ComponentActivity<*>?)

    val activity: ComponentActivity<*>?
}
