package com.example.libbase.Util;

import android.os.SystemClock;

import java.util.HashMap;
import java.util.Map;

public class TimeUtil {
    private static final String TAG = "TimeUtil";
    private static Map<String, Long> sStartTimeMap = new HashMap<>();
    private static Map<String, Long> sEndTimeMap = new HashMap<>();

    public static void markStart(String name){
        synchronized (TimeUtil.class) {
            sStartTimeMap.put(name, SystemClock.elapsedRealtime());
        }
    }

    public static void markEnd(String name){
        synchronized (TimeUtil.class) {
            sEndTimeMap.put(name, SystemClock.elapsedRealtime());
        }
    }

    public static long getDuration(String name){
        synchronized (TimeUtil.class) {
            if (sStartTimeMap.containsKey(name) && sEndTimeMap.containsKey(name)) {
                long startTime = sStartTimeMap.get(name);
                long endTime = sEndTimeMap.get(name);
                return endTime - startTime;
            }
        }
        return 0;
    }
}
