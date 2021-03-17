package com.example.administrator.androidtest.Test.MockkTest

import android.util.Log

class Tire_1 {
    fun rotate(text: String): String {
        Log.d(TAG, "rotate: ")
        return text
    }

    companion object {
        private const val TAG = "Tire_1"
    }
}