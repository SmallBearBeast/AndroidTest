package com.bear.libcomponent.provider.attach

import android.content.Context
import com.bear.libcomponent.host.ComponentActivity

interface IContextProvider {
    fun attachContext(context: Context?)

    val context: Context?

    val activity: ComponentActivity<*>?
}
