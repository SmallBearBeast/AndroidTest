package com.bear.libcomponent.component.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.bear.libcomponent.provider.ILifecycleEventProvider
import com.bear.libcomponent.provider.attach.ILifecycleProvider

abstract class LifeComponent @JvmOverloads constructor(
    private var componentLifecycle: Lifecycle? = null
) : ContextComponent(), ILifecycleProvider, ILifecycleEventProvider {

    // Tips: 为了在 Java 中可以直接访问 TAG，需要使用 @JvmField 注解
    @JvmField
    protected val TAG: String = javaClass.simpleName

    private var lifecycleObservers = arrayListOf<LifecycleEventObserver>()

    override val lifecycle: Lifecycle?
        get() = componentLifecycle

    init {
        doAttachLifecycle(componentLifecycle)
    }

    // 当类被继承时，如果子类重写了该方法，就会在子类未完全初始化时调用重写后的版本。
    //这可能会导致难以发现的 Bug，因为重写的函数可能会尝试访问子类中尚未初始化的属性或状态。
    override fun attachLifecycle(lifecycle: Lifecycle?) {
        doAttachLifecycle(lifecycle)
    }

    private fun doAttachLifecycle(lifecycle: Lifecycle?) {
        componentLifecycle?.removeObserver(this)
        componentLifecycle = lifecycle
        componentLifecycle?.addObserver(this)
    }

    fun addLifecycleObserver(observer: LifecycleEventObserver) {
        lifecycleObservers.add(observer)
    }

    fun removeLifecycleObserver(observer: LifecycleEventObserver) {
        lifecycleObservers.remove(observer)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                onCreate()
            }

            Lifecycle.Event.ON_START -> {
                onStart()
            }

            Lifecycle.Event.ON_RESUME -> {
                onResume()
            }

            Lifecycle.Event.ON_PAUSE -> {
                onPause()
            }

            Lifecycle.Event.ON_STOP -> {
                onStop()
            }

            Lifecycle.Event.ON_DESTROY -> {
                onDestroy()
                componentLifecycle?.removeObserver(this)
                lifecycleObservers.clear()
            }

            else -> Unit
        }
        lifecycleObservers.forEach {
            it.onStateChanged(source, event)
        }
    }

    protected open fun onCreate() {

    }

    protected open fun onStart() {

    }

    protected open fun onResume() {

    }

    protected open fun onPause() {

    }

    protected open fun onStop() {

    }

    protected open fun onDestroy() {

    }
}