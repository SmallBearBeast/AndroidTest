package com.example.administrator.androidtest.demo.optdemo.launchoptdemo

import android.util.Log
import com.bear.libcommon.util.TimeRecordUtil

object AppLaunchTracer {
    private const val TAG = "AppLaunchTracer"
    private const val APPLICATION_INIT = "application_init"
    private const val APPLICATION_CREATE = "application_create"
    private const val ACTIVITY_CREATE = "activity_create"

    fun markApplicationInit() {
        TimeRecordUtil.markStart(APPLICATION_INIT)
    }

    fun markApplicationCreate() {
        TimeRecordUtil.markEnd(APPLICATION_INIT)
        TimeRecordUtil.markStart(APPLICATION_CREATE)
    }

    fun markActivityCreate() {
        TimeRecordUtil.markEnd(APPLICATION_CREATE)
        TimeRecordUtil.markStart(ACTIVITY_CREATE)
    }

    fun markFirstFrameDraw() {
        TimeRecordUtil.markEnd(ACTIVITY_CREATE)
        val applicationInitToCreateTime = TimeRecordUtil.getDuration(APPLICATION_INIT)
        val applicationCreateToActivityCreateTime = TimeRecordUtil.getDuration(APPLICATION_CREATE)
        val activityCreateToFirstFrameDrawTime = TimeRecordUtil.getDuration(ACTIVITY_CREATE)
        Log.i(
            TAG,
            "markFirstFrameDraw: applicationInitToCreateTime = $applicationInitToCreateTime, " +
                    "applicationCreateToActivityCreateTime = $applicationCreateToActivityCreateTime, " +
                    "activityCreateToFirstFrameDrawTime = $activityCreateToFirstFrameDrawTime"
        )

        val coldStartTime = if (applicationInitToCreateTime == 0L) {
            0
        } else {
            applicationInitToCreateTime + applicationCreateToActivityCreateTime + activityCreateToFirstFrameDrawTime
        }
        Log.i(TAG, "markFirstFrameDraw: coldStartTime = $coldStartTime, warmStartTime = $activityCreateToFirstFrameDrawTime")

        TimeRecordUtil.remove(APPLICATION_INIT)
        TimeRecordUtil.remove(APPLICATION_CREATE)
        TimeRecordUtil.remove(ACTIVITY_CREATE)
    }
}