package com.example.administrator.androidtest

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import com.example.administrator.androidtest.demo.optdemo.bootoptdemo.AppLaunchTracer
import com.example.administrator.androidtest.demo.optdemo.bootoptdemo.BootTaskManager
import com.example.administrator.androidtest.demo.optdemo.bootoptdemo.MonitorClassLoader

class AndroidTestApplication : Application() {

    init {
        AppLaunchTracer.markApplicationInit()
    }

    override fun onCreate() {
        AppLaunchTracer.markApplicationCreate()
        super.onCreate()
        MonitorClassLoader.hook(this, true)
        Log.i(TAG, "onCreate: enter")
        context = this
        registerActivityLifecycleCallbacks(Callback())

        BootTaskManager.getInstance().init()
    }

    internal class Callback : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
            val s: String? = null
        }

        override fun onActivityStarted(activity: Activity) {
            val s: String? = null
        }

        override fun onActivityResumed(activity: Activity) {
            val s: String? = null
        }

        override fun onActivityPaused(activity: Activity) {
            val s: String? = null
        }

        override fun onActivityStopped(activity: Activity) {
            val s: String? = null
        }

        override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
            val s: String? = null
        }

        override fun onActivityDestroyed(activity: Activity) {
            Log.e(TAG, "onActivityDestroyed: " + "class = " + activity.javaClass.simpleName + "     ")
            val s: String? = null
        }
    }

    override fun getSharedPreferences(name: String, mode: Int): SharedPreferences {
//        Log.d(TAG, "getSharedPreferences: name = " + name + ", mode = " + mode);
//        SharedPreferences sp = super.getSharedPreferences(name, mode);
//        return PackMMKV.getSharedPreferences(this, name, sp);
        return super.getSharedPreferences(name, mode)
    }

    companion object {
        private const val TAG = "AndroidTestApplication"
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        @JvmField
        var FragVisibiableMap: Map<String, Boolean> = HashMap()
    }
}
