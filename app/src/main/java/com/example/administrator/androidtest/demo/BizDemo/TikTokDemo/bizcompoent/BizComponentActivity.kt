package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.bizcompoent

import android.util.Log
import androidx.viewbinding.ViewBinding
import com.bear.libbase.activity.BaseActivity

open class BizComponentActivity<VB : ViewBinding> : BaseActivity<VB>() {

    private val componentApiContainer by lazy {
        BizComponentApiContainer()
    }

    fun addComponent(component: BizComponent<*>, containerId: Int, tag: String? = null, addToBackStack: Boolean = false) {
        try {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(containerId, component, tag)
            if (addToBackStack) {
                transaction.addToBackStack(tag)
            }
            transaction.commitAllowingStateLoss()

            regComponentApi(component, tag)
            Log.d(TAG, "addComponent: success, fragment = ${component::class.java.simpleName}")
        } catch (e: Exception) {
            Log.d(TAG, "addComponent: failed, fragment = ${component::class.java.simpleName}", e)
        }
    }

    fun <API : IBizComponentApi> regComponentApi(api: API, tag: String? = null) {
        componentApiContainer.regComponentApi(lifecycle, api, tag)
    }

    fun <API : IBizComponentApi> unRegComponentApi(clz: Class<API>, tag: String? = null) {
        componentApiContainer.unRegComponentApi(clz, tag)
    }

    fun <API : IBizComponentApi> getComponentApi(clz: Class<API>, tag: String? = null): API? {
        var api: API? = null
        supportFragmentManager.fragments.forEach {
            if (it is BizComponent<*>) {
                api = it.findInComponentTree(clz, tag)
                if (api != null) {
                    return api
                }
            }
        }
        return api
    }
}
