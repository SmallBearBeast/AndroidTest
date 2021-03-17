package com.example.administrator.androidtest.Test.MockkTest

class Car_1() {
    constructor(name: String) : this()

    private val tire by lazy {
        Tire_1()
    }

    fun start(): String {
        return tire.rotate("Hello")
    }

    companion object {
        private const val TAG = "Car_1"
    }
}