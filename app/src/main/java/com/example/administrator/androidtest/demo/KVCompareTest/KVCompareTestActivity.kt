package com.example.administrator.androidtest.demo.KVCompareTest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.bear.libcommon.util.ToastUtil
import com.bear.libcomponent.component.ComponentActivity
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.Settings
import com.example.administrator.androidtest.databinding.ActKvCompareTestBinding
import kotlin.random.Random

class KVCompareTestActivity: ComponentActivity<ActKvCompareTestBinding>(){
    override fun inflateViewBinding(inflater: LayoutInflater): ActKvCompareTestBinding {
        return ActKvCompareTestBinding.inflate(inflater)
    }

    fun onClick(view: View) {
        when(view.id) {
            R.id.loadFileButton -> {
                KvCompareHelper.loadFromSp()
                KvCompareHelper.loadFromMMKV()
                KvCompareHelper.loadFromDataStore()
            }
            R.id.writeFileButton -> {
                KvCompareHelper.writeToSp()
                KvCompareHelper.writeToMMKV()
                KvCompareHelper.writeToDataStore()
            }
            R.id.readFileButton -> {
                KvCompareHelper.readFromSp()
                KvCompareHelper.readFromMMKV()
                KvCompareHelper.readFromDataStore()
            }
            R.id.clearFileButton -> {
                KvCompareHelper.clearSp()
                KvCompareHelper.clearMMKV()
                KvCompareHelper.clearDataStore()
            }
            R.id.testContinueSpApplyButton -> {
                KvCompareHelper.testContinueSpApply()
                ToastUtil.showToast("Then click the Start Activity Button and click the Show Toast Button quickly")
            }
            R.id.clearContinueSpButton -> {
                KvCompareHelper.clearContinueSp()
            }
            R.id.startActivityButton -> {
                start(this)
            }
            R.id.showToastButton -> {
                ToastUtil.showToast(TAG)
            }
            R.id.dsWriteIntButton -> {
                val data = Random(System.currentTimeMillis()).nextInt(0, 1000)
                DataStoreHelper.putInt(KEY_INT, data)
                ToastUtil.showToast("Write data = $data")
            }
            R.id.dsReadIntButton -> {
                val data = DataStoreHelper.getInt(KEY_INT, 0)
                ToastUtil.showToast("Read data = $data")
            }
            R.id.dsWritePbButton -> {
                val random = Random(System.currentTimeMillis())
                val age = random.nextInt(0, 1000)
                val counter = random.nextInt(0, 1000)
                val settings = Settings.newBuilder().setExampleAge(age).setExampleCounter(counter).build()
                DataStoreHelper.putSettings(settings)
                ToastUtil.showToast("Write settings = {exampleCounter = ${settings.exampleCounter}, exampleAge = ${settings.exampleAge}}")
            }
            R.id.dsReadPbButton -> {
                val settings = DataStoreHelper.getSettings()
                ToastUtil.showToast("Read settings = {exampleCounter = ${settings.exampleCounter}, exampleAge = ${settings.exampleAge}}")
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

    companion object {
        private const val KEY_INT = "KEY_INT"

        fun start(context: Context) {
            context.startActivity(Intent(context, KVCompareTestActivity::class.java))
        }
    }
}