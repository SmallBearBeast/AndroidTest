package com.example.administrator.androidtest.demo.optdemo.launchoptdemo.launchtask

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.effective.android.anchors.AnchorsManager
import com.effective.android.anchors.anchors
import com.effective.android.anchors.debuggable
import com.effective.android.anchors.graphics
import com.effective.android.anchors.startUp
import com.effective.android.anchors.task.Task
import com.effective.android.anchors.task.TaskCreator
import com.effective.android.anchors.task.project.Project
import com.effective.android.anchors.taskFactory
import com.example.administrator.androidtest.BuildConfig
import java.util.concurrent.BlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.max
import kotlin.math.min

object LaunchTaskManager {

    private const val TAG = "LaunchTaskManager"

    private lateinit var anchorsManager: AnchorsManager

    fun init(context: Context) {
        registerActivityLifecycleCallbacks(context)
        anchorsManager = AnchorsManager.getInstance(createExecutor()).debuggable {
            BuildConfig.DEBUG
        }.taskFactory {
            Project.TaskFactory(LaunchTaskCreator())
        }.anchors {
            arrayOf(TASK_ANCHOR_WAIT)
        }.graphics {
            arrayOf(TASK_IMMEDIATE)
//            if (listOf(true, false).random()) {
//                arrayOf(TASK_IMMEDIATE)
//            } else {
//                arrayOf(TASK_ALL)
//            }
        }.startUp(autoBlock = false, autoRegister = true)
    }

    fun waitAnchorTaskFinished() {
        anchorsManager.waitAnchorTaskFinished()
    }

    private fun createExecutor(): ExecutorService {
        val cpuCount = Runtime.getRuntime().availableProcessors()
        val corePoolSize = max(4.0, min((cpuCount - 1).toDouble(), 8.0)).toInt()
        val maximumPoolSize = corePoolSize * 2 + 1
        val keepLivesSecond = 30L
        val blockingQueue: BlockingQueue<Runnable> = PriorityBlockingQueue(128)
        val threadFactory: ThreadFactory = object : ThreadFactory {
            private val count = AtomicInteger(1)

            override fun newThread(r: Runnable): Thread {
                val thread = Thread(r, "Boot Thread #" + count.getAndIncrement())
                // 由于存在后台Task运行的情况，后台线程优先级比较低，不容易获取到cpu资源，因此后台线程的优先级设置到最大(Thead.MAX_PRIORITY)，从而加快启动任务的处理。
                thread.priority = Thread.MAX_PRIORITY
                return thread
            }
        }
        return ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepLivesSecond, TimeUnit.SECONDS, blockingQueue, threadFactory)
    }

    private fun registerActivityLifecycleCallbacks(context: Context) {
        (context as? Application)?.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
                super.onActivityPreCreated(activity, savedInstanceState)
                Log.d(TAG, "onActivityPreCreated: ${activity.componentName} enter")
                waitAnchorTaskFinished()
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                Log.d(TAG, "onActivityCreated: ${activity.componentName} enter")
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }

        })
    }

}

private class LaunchTaskCreator : TaskCreator {
    override fun createTask(taskName: String): Task {
        return when (taskName) {
            TASK_IMMEDIATE -> ImmediateTask()
            TASK_BACKGROUND_1 -> BackgroundTask1()
            TASK_BACKGROUND_2 -> BackgroundTask2()
            TASK_ANCHOR_WAIT -> AnchorWaitTask()
            TASK_NON_BLOCKING -> NonBlockingTask()
            TASK_ALL -> AllTask()
            else -> throw RuntimeException("unknown task name: $taskName")
        }
    }
}