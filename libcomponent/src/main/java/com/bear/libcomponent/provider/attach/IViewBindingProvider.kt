package com.bear.libcomponent.provider.attach

import androidx.viewbinding.ViewBinding

interface IViewBindingProvider<VB : ViewBinding> {
    fun attachViewBinding(binding: VB)

    fun requireBinding(): VB

    val viewBinding: VB?
}
