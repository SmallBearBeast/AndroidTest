package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.bizcompoent

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding

class BizComponentFragment<VB : ViewBinding> : BizComponent<VB>() {

    fun addComponent(lifecycle: Lifecycle, component: BizComponent<*>, containerId: Int, tag: String? = null, addToBackStack: Boolean = false) {
        try {
            val transaction = childFragmentManager.beginTransaction()
            transaction.add(containerId, component, tag)
            if (addToBackStack) {
                transaction.addToBackStack(tag)
            }
            transaction.commitAllowingStateLoss()

            regComponentApi(lifecycle, component, tag)
            Log.d(TAG, "addComponent: success, fragment = ${component::class.java.simpleName}")
        } catch (e: Exception) {
            Log.d(TAG, "addComponent: failed, fragment = ${component::class.java.simpleName}", e)
        }
    }
}