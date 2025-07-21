package com.bear.libcommon.util

import android.os.SystemClock

object TimeRecordUtil {
    private val startTimeMap: MutableMap<String, Long> = HashMap()
    private val endTimeMap: MutableMap<String, Long> = HashMap()

    @JvmStatic
    fun markStart(name: String) {
        synchronized(this) {
            startTimeMap[name] = SystemClock.elapsedRealtime()
        }
    }

    @JvmStatic
    fun markEnd(name: String) {
        synchronized(this) {
            endTimeMap[name] = SystemClock.elapsedRealtime()
        }
    }

    @JvmStatic
    fun remove(name: String) {
        synchronized(this) {
            startTimeMap.remove(name)
            endTimeMap.remove(name)
        }
    }

    @JvmStatic
    fun markAndGetDuration(name: String): Long {
        synchronized(this) {
            markEnd(name)
            return getDuration(name)
        }
    }

    @JvmStatic
    fun getDuration(name: String): Long {
        synchronized(this) {
            val startTime = startTimeMap[name]
            val endTime = endTimeMap[name]
            if (startTime != null && endTime != null) {
                return endTime - startTime
            }
        }
        return 0
    }
}
