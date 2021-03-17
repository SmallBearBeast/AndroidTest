package com.example.administrator.androidtest

import android.util.Log

class Tire {
    fun rotate(text: String): String {
        Log.d("TAG", "rotate: ")
        return text
    }

    companion object {
        private const val TAG = "Tire"
    }
}