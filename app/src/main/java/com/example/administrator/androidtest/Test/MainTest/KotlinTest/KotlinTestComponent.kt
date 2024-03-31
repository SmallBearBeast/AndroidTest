package com.example.administrator.androidtest.Test.MainTest.KotlinTest

import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class KotlinTestComponent : TestActivityComponent() {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        scope.launch {
            val dbJob = async { loadFromDB() }
            val netJob = async { loadFromNet() }
            dbJob.await()
            netJob.await()
        }
    }

    private suspend fun loadFromDB(): String {
        delay(2000)
        return "loadFromDB"
    }

    private suspend fun loadFromNet(): String {
        delay(2000)
        return "loadFromNet"
    }
}