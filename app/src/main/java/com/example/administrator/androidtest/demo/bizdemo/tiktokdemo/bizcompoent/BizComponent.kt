package com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.bizcompoent

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bear.libbase.fragment.BaseFragment

open class BizComponent<VB : ViewBinding> : BaseFragment<VB>(), IBizComponentApi {

    fun <API : IBizComponentApi> getComponentApi(clz: Class<API>, tag: String? = null): API? {
        // 首先在当前组件中查找
        var api = findInComponentTree(clz, tag)
        if (api != null) {
            return api
        }

        // 向上遍历父组件
        api = findInParentComponentTree(clz, tag)
        if (api != null) {
            return api
        }

        // 在Activity的其他Fragment中查找
        return findInActivityComponentTree(clz, tag)
    }

    // 首先在当前组件中查找
    internal fun <API : IBizComponentApi> findInComponentTree(clz: Class<API>, tag: String?, excludeComponent: Fragment? = null): API? {
        var api: API? = null
        if (clz.isInstance(this)) {
            api = this as? API
            if (api != null) {
                return api
            }
        }
        childFragmentManager.fragments.forEach {
            if (clz.isInstance(it) && it.tag == tag) {
                api = it as? API
                if (api != null) {
                    return api
                }
            }
        }
        childFragmentManager.fragments.forEach {
            if (it == excludeComponent) {
                return@forEach
            }
            if (it is BizComponent<*>) {
                api = it.findInComponentTree(clz, tag, excludeComponent)
                if (api != null) {
                    return api
                }
            }
        }
        return api
    }

    // 向上遍历父组件
    private fun <API : IBizComponentApi> findInParentComponentTree(clz: Class<API>, tag: String?): API? {
        var api: API? = null
        var parentComponent = parentFragment
        var excludeComponent: Fragment? = this
        while (api == null && parentComponent != null) {
            if (parentComponent is BizComponent<*>) {
                api = parentComponent.findInComponentTree(clz, tag, excludeComponent)
                excludeComponent = parentComponent
                parentComponent = parentComponent.parentFragment
            }
        }
        return api
    }

    // 在Activity的其他Fragment中查找
    private fun <API : IBizComponentApi> findInActivityComponentTree(clz: Class<API>, tag: String?): API? {
        var parentComponent = parentFragment
        var excludeComponent: Fragment? = this
        while (parentComponent != null) {
            excludeComponent = parentComponent
            parentComponent = parentComponent.parentFragment
        }

        var api: API? = null
        activity?.supportFragmentManager?.fragments?.forEach {
            if (it == excludeComponent) {
                return@forEach
            }
            if (it is BizComponent<*>) {
                api = it.findInComponentTree(clz, tag, excludeComponent)
                if (api != null) {
                    return api
                }
            }
        }
        return api
    }
}

interface IBizComponentApi {

}
