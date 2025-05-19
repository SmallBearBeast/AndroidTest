package com.bear.libcommon.util;

import android.os.SystemClock;

import java.util.HashMap;
import java.util.Map;

public class TimeRecordUtil {
    private static final Map<String, Long> sStartTimeMap = new HashMap<>();
    private static final Map<String, Long> sEndTimeMap = new HashMap<>();

    public static void markStart(String name){
        synchronized (TimeRecordUtil.class) {
            sStartTimeMap.put(name, SystemClock.elapsedRealtime());
        }
    }

    public static void markEnd(String name){
        synchronized (TimeRecordUtil.class) {
            sEndTimeMap.put(name, SystemClock.elapsedRealtime());
        }
    }

    public static void remove(String name){
        synchronized (TimeRecordUtil.class) {
            sStartTimeMap.remove(name);
            sEndTimeMap.remove(name);
        }
    }

    public static long markAndGetDuration(String name){
        synchronized (TimeRecordUtil.class) {
            markEnd(name);
            return getDuration(name);
        }
    }

    public static long getDuration(String name){
        synchronized (TimeRecordUtil.class) {
            if (sStartTimeMap.containsKey(name) && sEndTimeMap.containsKey(name)) {
                long startTime = sStartTimeMap.get(name);
                long endTime = sEndTimeMap.get(name);
                return endTime - startTime;
            }
        }
        return 0;
    }
}
