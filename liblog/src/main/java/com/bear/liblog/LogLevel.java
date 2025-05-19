package com.bear.liblog;


import androidx.annotation.IntDef;

@IntDef({LogConfig.VERBOSE, LogConfig.DEBUG, LogConfig.INFO, LogConfig.WARN, LogConfig.ERROR, LogConfig.ASSERT})
public @interface LogLevel {
}
