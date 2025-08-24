package com.bear.libcomponent.provider.attach

import androidx.lifecycle.Lifecycle

interface ILifecycleProvider {
    fun attachLifecycle(lifecycle: Lifecycle?)

    val lifecycle: Lifecycle?
}
