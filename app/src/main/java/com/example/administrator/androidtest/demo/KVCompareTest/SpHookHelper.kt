package com.example.administrator.androidtest.demo.KVCompareTest

import android.os.Build
import android.os.Handler
import android.os.Message
import android.util.Log
import java.util.LinkedList
import java.util.concurrent.ConcurrentLinkedQueue

object SpHookHelper {
    private const val TAG = "HookHelper"
    private var initPendingWork = false
    private var sWorkList: LinkedList<Runnable> = LinkedList()
    private var sFinishersList: LinkedList<Runnable> = LinkedList()
    private var sPendingWorkList: LinkedList<Runnable> = LinkedList()
    private var sPendingWorkFinishersList: ConcurrentLinkedQueue<Runnable> = ConcurrentLinkedQueue()
    private var sPendingFinishersList: LinkedList<Runnable> = LinkedList()
    fun init() {
        try {
            val activityThreadClass = Class.forName("android.app.ActivityThread")
            val currentAtyThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread")
            val activityThread = currentAtyThreadMethod.invoke(null)
            val mHField = activityThreadClass.getDeclaredField("mH")
            mHField.isAccessible = true
            val handler = mHField.get(activityThread) as Handler
            val mCallbackField = Handler::class.java.getDeclaredField("mCallback")
            mCallbackField.isAccessible = true
            mCallbackField.set(handler, SpCompatCallback())
        } catch (e: Exception) {

        }
    }

    private class SpCompatCallback : Handler.Callback {
        override fun handleMessage(msg: Message): Boolean {
            Log.d(TAG, "handleMessage: msg.what = ${msg.what}")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (msg.what == 159 && msg.obj != null) {
                    try {
                        val clientTransactionClass = msg.obj.javaClass
                        val getLifecycleStateRequestMethod = clientTransactionClass.getDeclaredMethod("getLifecycleStateRequest")
                        getLifecycleStateRequestMethod.isAccessible = true
                        val lifecycleStateRequest = getLifecycleStateRequestMethod.invoke(msg.obj)
                        if (lifecycleStateRequest != null) {
                            Log.d(TAG, "handleMessage: mLifecycleStateRequest = ${lifecycleStateRequest.javaClass.simpleName}")
                            if ("StopActivityItem" == lifecycleStateRequest.javaClass.simpleName) {
                                beforeSpBlock("StopActivityItem")
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    when (msg.what) {
                        SERVICE_ARGS ->
                            beforeSpBlock("SERVICE_ARGS")
                        STOP_SERVICE ->
                            beforeSpBlock("STOP_SERVICE")
                        SLEEPING ->
                            beforeSpBlock("SLEEPING")
                    }
                }
            } else {
                when (msg.what) {
                    SERVICE_ARGS ->
                        beforeSpBlock("SERVICE_ARGS")
                    STOP_SERVICE ->
                        beforeSpBlock("STOP_SERVICE")
                    SLEEPING ->
                        beforeSpBlock("SLEEPING")
                    STOP_ACTIVITY_SHOW ->
                        beforeSpBlock("STOP_ACTIVITY_SHOW")
                    STOP_ACTIVITY_HIDE ->
                        beforeSpBlock("STOP_ACTIVITY_HIDE")
                    PAUSE_ACTIVITY ->
                        beforeSpBlock("PAUSE_ACTIVITY")
                    PAUSE_ACTIVITY_FINISHING ->
                        beforeSpBlock("PAUSE_ACTIVITY_FINISHING")
                }
            }
            return false
        }

        companion object {
            //handleServiceArgs
            private const val SERVICE_ARGS = 115

            //handleStopService
            private const val STOP_SERVICE = 116

            //handleSleeping
            private const val SLEEPING = 137

            //handleStopActivity
            private const val STOP_ACTIVITY_SHOW = 103

            //handleStopActivity
            private const val STOP_ACTIVITY_HIDE = 104

            //handlePauseActivity
            private const val PAUSE_ACTIVITY = 101

            //handlePauseActivity
            private const val PAUSE_ACTIVITY_FINISHING = 102
        }
    }

    private fun getPendingWorkFinishers() {
        Log.d(TAG, "getPendingWorkFinishers")
        try {
            val queuedWorkClass = Class.forName("android.app.QueuedWork")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                The api is non-sdk belongs to the blocked greylist-max-o level
//                val sWorkField = queuedWorkClass.getDeclaredField("sWork")
//                sWorkField.isAccessible = true
//                sWorkList = sWorkField.get(null) as LinkedList<Runnable>
                val sFinishersField = queuedWorkClass.getDeclaredField("sFinishers")
                sFinishersField.isAccessible = true
                sFinishersList = sFinishersField.get(null) as LinkedList<Runnable>
            } else {
                val sPendingWorkFinishersField = queuedWorkClass.getDeclaredField("sPendingWorkFinishers")
                sPendingWorkFinishersField.isAccessible = true
                sPendingWorkFinishersList = sPendingWorkFinishersField.get(null) as ConcurrentLinkedQueue<Runnable>
            }
            Log.d(TAG, "getPendingWorkFinishers success")
            initPendingWork = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun beforeSpBlock(tag: String) {
        if (!initPendingWork) {
            getPendingWorkFinishers()
        }
        Log.d(TAG, "beforeSpBlock: $tag, sWorkList = ${sWorkList.size}, sFinishersList = ${sFinishersList.size}, sPendingWorkFinishersList = ${sPendingWorkFinishersList.size}")
        sWorkList.apply {
            if (isNotEmpty()) {
                sPendingWorkList = clone() as LinkedList<Runnable>
                clear()
            }
        }
        sFinishersList.apply {
            if (isNotEmpty()) {
                sPendingFinishersList = clone() as LinkedList<Runnable>
                clear()
            }
        }
        sPendingWorkFinishersList.apply {
            if (isNotEmpty()) {
                clear()
            }
        }
//        ExecutorUtil.execute {
//            Log.d(TAG, "beforeSpBlock: sPendingWorkList.size = ${sPendingWorkList.size}, sPendingFinishersList.size = ${sPendingFinishersList.size}")
//            val startTs = System.currentTimeMillis()
//            sPendingWorkList.apply {
//                forEach {
//                    it.run()
//                }
//            }
//            sPendingFinishersList.apply {
//                forEach {
//                    it.run()
//                }
//            }
//            Log.d(TAG, "beforeSpBlock do rest pending work cost: ${System.currentTimeMillis() - startTs}ms")
//        }
    }

}