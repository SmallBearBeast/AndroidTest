package com.bear.libcommon.util

import android.os.SystemClock

object TimeRecordUtil {
    private val startTimeMap: MutableMap<String, Long> = HashMap()
    private val endTimeMap: MutableMap<String, Long> = HashMap()

    @JvmStatic
    fun markStart(tag: String) {
        startTimeMap[tag] = SystemClock.elapsedRealtime()
    }

    @JvmStatic
    fun markEnd(tag: String) {
        endTimeMap[tag] = SystemClock.elapsedRealtime()
    }

    @JvmStatic
    fun remove(tag: String) {
        startTimeMap.remove(tag)
        endTimeMap.remove(tag)
    }

    @JvmStatic
    fun markAndGetDuration(tag: String): Long {
        markEnd(tag)
        return getDuration(tag)
    }

    @JvmStatic
    fun getDuration(tag: String): Long {
        val startTime = startTimeMap[tag]
        val endTime = endTimeMap[tag]
        if (startTime != null && endTime != null) {
            return endTime - startTime
        }
        return 0
    }
}
