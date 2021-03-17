package com.example.administrator.androidtest

import android.util.Log

class Car() {
    private val privateName
        get() = "privateName"
    private val tire by lazy {
        Tire()
    }

    constructor(name: String) : this() {

    }

    fun printPrivateName() {
        println("privateName = $privateName")
    }

    fun start(): String {
        return tire.rotate("Hello")
    }

    fun stop(): String {
        Log.d(TAG, "stop: ")
        return "Stop"
    }

    fun reset() {

    }

    fun print(carName: String, carNumber: String) {

    }

    private fun doPrivate(): String {
        return "doPrivate"
    }

    fun doPublic(): String {
        return doPrivate()
    }

    fun ObjExtension.printNameInCar() {
        println(name)
    }

    companion object {
        private const val TAG = "Car"

        @JvmStatic
        fun add(a: Int, b: Int) = a - b
    }
}