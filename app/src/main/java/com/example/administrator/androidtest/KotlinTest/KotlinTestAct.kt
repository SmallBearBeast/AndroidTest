package com.example.administrator.androidtest.KotlinTest

class KotlinTestAct {

    //不可重新赋值
    val mTest_1 = 1
    //可以重新赋值
    var mTest_2 = 2
    fun sum(a: Int, b: Int): Int{
        return a + b
    }

    fun sub(a: Int, b: Int) = a - b

    fun max(a: Int, b: Int) = if(a > b) a else b

    fun print(a: Int, b: Int) = println("sum of $a and $b is ${sum(a, b)}")

    fun getStringLength(obj: Any): Int? = if(obj is String) obj.length else null

    fun forTest(){
        val items = listOf("apple", "banana", "kiwifruit")
        for (item in items) {
            println(item)
        }
    }

    fun whenTest(obj: Any){
        val list = listOf(1, 2, "abc")
        val map = mapOf(1 to 2, "abc" to "abc")
        when (obj) {
            1          -> "One"
            "Hello"    -> "Greeting"
            is Long    -> "Long"
            !is String -> "Not a string"
            else       -> "Unknown"
        }
    }

    data class Data(val name: String = "Jame", val email: String)
}