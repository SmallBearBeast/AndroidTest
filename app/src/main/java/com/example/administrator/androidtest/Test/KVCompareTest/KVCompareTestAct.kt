package com.example.administrator.androidtest.Test.KVCompareTest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bear.libcomponent.ComponentAct
import com.example.administrator.androidtest.R
import com.example.libbase.Util.ToastUtil
import java.lang.Exception

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
                testReflect()
                ToastUtil.showToast(TAG)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SpHookHelper.init()
        KvCompareHelper.preLoadTestSp()
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
}