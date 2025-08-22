package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.bizcompoent

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.bear.libbase.fragment.BaseFragment
import java.util.Objects

open class BizComponent<VB : ViewBinding> : BaseFragment<VB>(), IBizComponentApi {

    private val componentApiContainer by lazy {
        BizComponentApiContainer()
    }

    fun <API : IBizComponentApi> regComponentApi(lifecycle: Lifecycle, api: API, tag: String? = null) {
        componentApiContainer.regComponentApi(lifecycle, api, tag)
    }

    fun <API : IBizComponentApi> unRegComponentApi(clz: Class<API>, tag: String? = null) {
        componentApiContainer.unRegComponentApi(clz, tag)
    }

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
        var api = componentApiContainer.getComponentApi(clz, tag)
        if (api != null) {
            return api
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
        return null
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

@Suppress("UNCHECKED_CAST")
class BizComponentApiContainer {
    private val bizComponentApiMap = hashMapOf<BizComponentApiKey<*>, IBizComponentApi>()

    fun <API : IBizComponentApi> regComponentApi(lifecycle: Lifecycle, api: API, tag: String?) {
        val apiKey = BizComponentApiKey(api.javaClass, tag)
        if (bizComponentApiMap.containsKey(apiKey)) {
            throw RuntimeException("Can not register component with same type and tag")
        }
        bizComponentApiMap[apiKey] = api
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    bizComponentApiMap.remove(apiKey)
                }
            }
        })
    }

    fun <API : IBizComponentApi> unRegComponentApi(clz: Class<API>, tag: String?) {
        val targetKey = BizComponentApiKey(clz, tag)
        bizComponentApiMap.remove(targetKey)
    }

    fun <API : IBizComponentApi> getComponentApi(clz: Class<API>, tag: String?): API? {
        return bizComponentApiMap[BizComponentApiKey(clz, tag)] as? API
    }
}

internal class BizComponentApiKey<C : IBizComponentApi>(
    private val clz: Class<C>,
    private val tag: String?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val that = other as BizComponentApiKey<*>
        return clz == that.clz && tag == that.tag
    }

    override fun hashCode(): Int {
        return Objects.hash(clz, tag)
    }
}


interface IBizComponentApi {

}
