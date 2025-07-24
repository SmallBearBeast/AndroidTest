package com.example.administrator.androidtest.demo.optdemo.launchoptdemo.launchtask

import android.app.Application
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.bear.libcommon.util.AppInitUtil
import com.bear.libcommon.util.MainHandlerUtil
import com.bear.libcommon.util.TimeRecordUtil
import com.bear.libfresco.FrescoUtil
import com.bear.libkv.MmkvVal.MmkvVal
import com.bear.libkv.SpVal.SpHelper
import com.bear.libokhttp.OkHelper
import com.bumptech.glide.Glide
import com.effective.android.anchors.task.Task
import com.example.administrator.androidtest.AndroidTestApplication
import com.example.administrator.androidtest.BuildConfig
import com.example.administrator.androidtest.demo.SpValHelper
import com.example.administrator.androidtest.demo.optdemo.launchoptdemo.ClassPreloadExecutor
import com.example.administrator.androidtest.demo.optdemo.launchoptdemo.MonitorClassLoader

abstract class BaseLaunchTask(id: String, isAsyncTask: Boolean) : Task(id, isAsyncTask) {
    protected val TAG: String = this.javaClass.simpleName

    override fun run(name: String) {
        doLaunchJob(name)
    }

    abstract fun doLaunchJob(name: String)

    protected fun sleep(millis: Long) {
//        try {
//            Thread.sleep(millis)
//        } catch (e: Exception) {
//            throw RuntimeException(e)
//        }
    }
}

class ImmediateTask : BaseLaunchTask(TASK_IMMEDIATE, false) {

    init {
        priority = PRIORITY_HIGH
    }

    // 初始化crash sdk，日志sdk，统计sdk
    override fun doLaunchJob(name: String) {
        TimeRecordUtil.markStart("ImmediateTask")
        sleep(200)
        MonitorClassLoader.hook(AndroidTestApplication.context as Application, true)
        initFlutterEngine()
        Log.d(TAG, "doLaunchJob: cost time " + TimeRecordUtil.markAndGetDuration("ImmediateTask") + "ms")
    }

    private fun initFlutterEngine() {
        // 需要放在主线程
        TimeRecordUtil.markStart("InitFlutterEngine")
        // Instantiate a FlutterEngine.
//        FlutterEngine flutterEngine = new FlutterEngine(AndroidTestApplication.getContext());
//        flutterEngine.getDartExecutor().executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault());
//        // Cache the FlutterEngine to be used by FlutterActivity.
//        FlutterEngineCache.getInstance().put("my_engine_id", flutterEngine);
        Log.i(TAG, "initFlutterEngine: cost time " + TimeRecordUtil.markAndGetDuration("InitFlutterEngine") + " ms")
    }
}


class BackgroundTask1 : BaseLaunchTask(TASK_BACKGROUND_1, true) {

    init {
        priority = PRIORITY_HIGH
    }

    // 网络初始化，so库加载，数据库初始化，文件操作初始化(读取文件配置内容)，sp预加载(内部启一个线程去加载的)
    // 反射优化(ARouter, Retrofit)，预加载操作(图片缓存预加载，View预加载，首页数据序列化数据加载)
    override fun doLaunchJob(name: String) {
        TimeRecordUtil.markStart("BackgroundTask1")
        initOkHttp()
        Log.d(TAG, "doLaunchJob: cost time " + TimeRecordUtil.markAndGetDuration("BackgroundTask1") + "ms")
    }

    private fun initOkHttp() {
        TimeRecordUtil.markStart("initOkHttp")
        OkHelper.init(AndroidTestApplication.context as Application, null)
        Log.d(TAG, "initOkHttp: cost time " + TimeRecordUtil.markAndGetDuration("initOkHttp") + "ms")
    }
}

class BackgroundTask2 : BaseLaunchTask(TASK_BACKGROUND_2, true) {

    init {
        priority = PRIORITY_HIGH
    }

    override fun doLaunchJob(name: String) {
        // 初始化模块，初始化Third sdk(里面可能有一些反射操作，IO等耗时操作)，配置初始化。
        TimeRecordUtil.markStart("BackgroundTask2")
        initCommon()
        Log.d(TAG, "doLaunchJob: cost time " + TimeRecordUtil.markAndGetDuration("BackgroundTask2") + "ms")
    }

    private fun initCommon() {
        TimeRecordUtil.markStart("initCommon")
        val context = AndroidTestApplication.context
        AppInitUtil.init(context)
        FrescoUtil.init(context)
        SpHelper.init(context)
        MmkvVal.init(context)
        SpHelper.preload(SpValHelper.SP_GLOBAL_CONFIG)
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(context as Application)

        // 初始化图片加载库Glide，也可以使用时候在加载。
        Glide.get(context)
        Log.d(TAG, "initCommon: cost time " + TimeRecordUtil.markAndGetDuration("initCommon") + "ms")
    }
}

class AnchorWaitTask : BaseLaunchTask(TASK_ANCHOR_WAIT, true) {

    init {
        priority = PRIORITY_HIGH
    }

    override fun doLaunchJob(name: String) {
        TimeRecordUtil.markStart("AnchorWaitTask")
        Log.d(TAG, "doLaunchJob: cost time " + TimeRecordUtil.markAndGetDuration("AnchorWaitTask") + "ms")
    }
}

class NonBlockingTask : BaseLaunchTask(TASK_NON_BLOCKING, true) {

    init {
        priority = PRIORITY_LOW
    }

    // 同步数据任务，上传任务，其他一些启动执行任务。
    override fun doLaunchJob(name: String) {
        TimeRecordUtil.markStart("NonBlockingTask")
        sleep(200)
        initClassPreload()
        MainHandlerUtil.post {
            sleep(200)
        }
        Log.d(TAG, "doLaunchJob: cost time " + TimeRecordUtil.markAndGetDuration("NonBlockingTask") + "ms")
    }

    // 类预加载优化(Activity，启动相关耗时的类)
    private fun initClassPreload() {
        TimeRecordUtil.markStart("initClassPreload")
        ClassPreloadExecutor.doPreload()
        Log.d(TAG, "initClassPreload: cost time " + TimeRecordUtil.markAndGetDuration("initClassPreload") + "ms")
    }
}

class AllTask : BaseLaunchTask(TASK_ALL, false) {
    override fun doLaunchJob(name: String) {
        TimeRecordUtil.markStart("AllTask")
        ImmediateTask().doLaunchJob(TASK_IMMEDIATE)
        BackgroundTask1().doLaunchJob(TASK_BACKGROUND_1)
        BackgroundTask2().doLaunchJob(TASK_BACKGROUND_2)
        AnchorWaitTask().doLaunchJob(TASK_ANCHOR_WAIT)
        NonBlockingTask().doLaunchJob(TASK_NON_BLOCKING)
        Log.d(TAG, "run: cost time " + TimeRecordUtil.markAndGetDuration("AllTask") + "ms")
    }
}