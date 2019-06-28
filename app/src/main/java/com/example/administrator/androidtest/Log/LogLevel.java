package com.example.administrator.androidtest.Log;

import android.annotation.IntDef;

@IntDef({LogConfig.VERBOSE, LogConfig.DEBUG, LogConfig.INFO, LogConfig.WARN, LogConfig.ERROR, LogConfig.ASSERT})
public @interface LogLevel {
}
