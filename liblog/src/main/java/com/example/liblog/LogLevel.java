package com.example.liblog;


import android.support.annotation.IntDef;

@IntDef({LogConfig.VERBOSE, LogConfig.DEBUG, LogConfig.INFO, LogConfig.WARN, LogConfig.ERROR, LogConfig.ASSERT})
public @interface LogLevel {
}
