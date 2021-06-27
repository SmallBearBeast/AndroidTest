package com.example.administrator.androidtest.Test.KVCompareTest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bear.libcomponent.ComponentAct
import com.example.administrator.androidtest.App
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.Settings
import com.example.administrator.androidtest.Test.OtherTest.SpValHelper
import com.example.libbase.Util.ToastUtil
import java.lang.Exception
import kotlin.random.Random

class KVCompareTestAct: ComponentAct(){
    override fun layoutId(): Int  = R.layout.act_kv_compare_test

    fun onClick(view: View) {
        when(view.id) {
            R.id.bt_1 -> {
                KvCompareHelper.loadFromSp()
                KvCompareHelper.loadFromMMKV()
                KvCompareHelper.loadFromDataStore()
            }
            R.id.bt_2 -> {
                KvCompareHelper.writeToSp()
                KvCompareHelper.writeToMMKV()
                KvCompareHelper.writeToDataStore()
            }
            R.id.bt_3 -> {
                KvCompareHelper.readFromSp()
                KvCompareHelper.readFromMMKV()
                KvCompareHelper.readFromDataStore()
            }
            R.id.bt_4 -> {
                startActivity(Intent(this, KVCompareTestAct::class.java))
            }
            R.id.bt_5 -> {
                KvCompareHelper.testSpCommit()
            }
            R.id.bt_6 -> {
                KvCompareHelper.clearTestSp()
            }
            R.id.bt_7 -> {
//                SpValHelper.testBoolSp.reverse()
//                SpValHelper.testIntSp.inc()
//                SpValHelper.testStringSp.set("Hugo")
                App.getContext().getSharedPreferences(SpValHelper.SP_GLOBAL_CONFIG, Context.MODE_PRIVATE).all
                testReflect()
                ToastUtil.showToast(TAG)
            }
            R.id.bt_8 -> {
                val data = Random(System.currentTimeMillis()).nextInt(0, 1000)
                DataStoreHelper.putInt(KEY_INT, data)
                ToastUtil.showToast("Write data = $data")
            }
            R.id.bt_9 -> {
                val data = DataStoreHelper.getInt(KEY_INT, 0)
                ToastUtil.showToast("Read data = $data")
            }
            R.id.bt_10 -> {
                val random = Random(System.currentTimeMillis())
                val age = random.nextInt(0, 1000)
                val counter = random.nextInt(0, 1000)
                val settings = Settings.newBuilder().setExampleAge(age).setExampleCounter(counter).build()
                DataStoreHelper.putSettings(settings)
                ToastUtil.showToast("Write settings = {exampleCounter = ${settings.exampleCounter}, exampleAge = ${settings.exampleAge}}")
            }
            R.id.bt_11 -> {
                val settings = DataStoreHelper.getSettings()
                ToastUtil.showToast("Write settings = {exampleCounter = ${settings.exampleCounter}, exampleAge = ${settings.exampleAge}}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        SpHookHelper.init()
//        KvCompareHelper.preLoadTestSp()
        Log.d(TAG, "onCreate: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    private fun testReflect() {
        try {
            val reflectData = ReflectData("Java", "PHP")
            val reflectDataClass = Class.forName("com.example.administrator.androidtest.Test.KVCompareTest.ReflectData")
            val privateReflectDataName1Field = reflectDataClass.getDeclaredField("privateReflectDataName1")
            privateReflectDataName1Field.isAccessible = true
            val privateReflectDataName1 = privateReflectDataName1Field.get(reflectData)

            val privateReflectDataName2Field = reflectDataClass.getDeclaredField("privateReflectDataName2")
            val privateReflectDataName2 = privateReflectDataName2Field.get(reflectData)

            val getName1Method = reflectDataClass.getDeclaredMethod("getName1")
            getName1Method.isAccessible = true
            val getName1Return = getName1Method.invoke(reflectData)

            val getName2Method = reflectDataClass.getDeclaredMethod("getName2")
            val getName2Return  = getName2Method.invoke(reflectData)

            Log.d(TAG, "testReflect: privateReflectDataName1 = $privateReflectDataName1" +
                    ", privateReflectDataName2 = $privateReflectDataName2" +
                    ", getName1Return = $getName1Return, getName2Return = $getName2Return")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val KEY_INT = "KEY_INT"
    }
}