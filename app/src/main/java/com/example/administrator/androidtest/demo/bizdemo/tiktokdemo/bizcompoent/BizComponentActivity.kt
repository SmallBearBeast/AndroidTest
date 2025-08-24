package com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.bizcompoent

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.bear.libbase.activity.BaseActivity

open class BizComponentActivity<VB : ViewBinding> : BaseActivity<VB>() {

    fun addComponent(component: BizComponent<*>, containerId: Int, tag: String? = null, addToBackStack: Boolean = false) {
        try {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(containerId, component, tag)
            if (addToBackStack) {
                transaction.addToBackStack(tag)
            }
            transaction.commitAllowingStateLoss()
//            supportFragmentManager.executePendingTransactions()
            Log.d(TAG, "addComponent: success, fragment = ${component::class.java.simpleName}")
        } catch (e: Exception) {
            Log.d(TAG, "addComponent: failed, fragment = ${component::class.java.simpleName}", e)
        }
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

    private val registerFragmentLifecycleCallbacksMap = hashMapOf<String, Boolean>()

    fun <API : IBizComponentApi> getComponentApi(clz: Class<API>, tag: String? = null, onApiReady: (api: API) -> Unit) {
        Log.d(TAG, "getComponentApi: clz = $clz, tag = $tag")
        supportFragmentManager.fragments.forEach {
            if (it is BizComponent<*>) {
                val api = it.findInComponentTree(clz, tag)
                if (api != null) {
                    onApiReady(api)
                    return
                }
            }
        }
        if (registerFragmentLifecycleCallbacksMap["${clz.name}-$tag"] == true) {
            return
        }
        registerFragmentLifecycleCallbacksMap["${clz.name}-$tag"] = true
        supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
                super.onFragmentViewCreated(fm, f, v, savedInstanceState)
                supportFragmentManager.fragments.forEach {
                    if (it is BizComponent<*>) {
                        val api = it.findInComponentTree(clz, tag)
                        if (api != null) {
                            onApiReady(api)
                            supportFragmentManager.unregisterFragmentLifecycleCallbacks(this)
                            registerFragmentLifecycleCallbacksMap["${clz.name}-$tag"] = false
                            return
                        }
                    }
                }
            }

            override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
                super.onFragmentViewDestroyed(fm, f)
                supportFragmentManager.unregisterFragmentLifecycleCallbacks(this)
                registerFragmentLifecycleCallbacksMap["${clz.name}-$tag"] = false
            }
        }, false)
    }
}


