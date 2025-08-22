package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.bizcompoent

import android.util.Log
import androidx.viewbinding.ViewBinding

class BizComponentFragment<VB : ViewBinding> : BizComponent<VB>() {

    fun addComponent(component: BizComponent<*>, containerId: Int, tag: String? = null, addToBackStack: Boolean = false) {
        try {
            val transaction = childFragmentManager.beginTransaction()
            transaction.add(containerId, component, tag)
            if (addToBackStack) {
                transaction.addToBackStack(tag)
            }
            transaction.commitAllowingStateLoss()
            Log.d(TAG, "addComponent: success, fragment = ${component::class.java.simpleName}")
        } catch (e: Exception) {
            Log.d(TAG, "addComponent: failed, fragment = ${component::class.java.simpleName}", e)
        }
    }
}