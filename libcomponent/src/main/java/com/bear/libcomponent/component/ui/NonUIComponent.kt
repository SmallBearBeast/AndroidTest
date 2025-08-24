package com.bear.libcomponent.component.ui

import androidx.lifecycle.Lifecycle
import com.bear.libcomponent.component.base.LifeComponent

open class NonUIComponent @JvmOverloads constructor(
    lifecycle: Lifecycle? = null
) : LifeComponent(lifecycle) {

}