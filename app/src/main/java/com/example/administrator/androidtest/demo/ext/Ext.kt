package com.example.administrator.androidtest.demo.ext

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.view.WindowCompat

inline fun <T> safeTryCatch(
    handleError: (Throwable) -> T? = { null },
    block: () -> T
): T? {
    return try {
        block()
    } catch (e: Exception) {
        Log.e("Ext", "safeTryCatch: error msg = ${e.message}")
        handleError(e)
    }
}

fun Activity.setupNormalSystemBar(lightStatusBars: Boolean = true, lightNavigationBars: Boolean = true) {
    // 允许内容延伸到状态栏区域
    WindowCompat.setDecorFitsSystemWindows(window, false)
    // 设置状态栏、导航栏透明
    window.apply {
        statusBarColor = Color.TRANSPARENT
        navigationBarColor = Color.TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isNavigationBarContrastEnforced = false
            isStatusBarContrastEnforced = false
        }
    }
    // 获取WindowInsetsController
    val controller = WindowCompat.getInsetsController(window, window.decorView)
    controller?.apply {
        isAppearanceLightStatusBars = lightStatusBars
        isAppearanceLightNavigationBars = lightNavigationBars
    }
}