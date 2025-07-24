package com.example.administrator.androidtest

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.administrator.androidtest.demo.optdemo.launchoptdemo.AppLaunchTracer
import com.example.administrator.androidtest.demo.optdemo.launchoptdemo.launchtask.LaunchTaskManager

class AndroidTestApplication : Application() {

    init {
        AppLaunchTracer.markApplicationInit()
    }

    override fun onCreate() {
        AppLaunchTracer.markApplicationCreate()
        super.onCreate()
        context = this
        LaunchTaskManager.init(this)
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
