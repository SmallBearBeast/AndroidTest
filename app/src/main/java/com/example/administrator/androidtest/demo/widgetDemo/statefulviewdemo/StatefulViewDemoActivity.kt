package com.example.administrator.androidtest.demo.widgetDemo.statefulviewdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.bear.libcomponent.host.ComponentActivity
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.databinding.ActStatefulViewDemoBinding
import java.io.BufferedReader
import java.io.InputStreamReader

class StatefulViewDemoActivity : ComponentActivity<ActStatefulViewDemoBinding>(), View.OnClickListener {
    override fun inflateViewBinding(inflater: LayoutInflater): ActStatefulViewDemoBinding {
        return ActStatefulViewDemoBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireBinding().apply {
            statefulImgTextView.setOnClickListener { v ->
                statefulImgTextView.isSelected = !statefulImgTextView.isSelected
            }
            stateful2TextView.setOnClickListener { v ->
                stateful2TextView.isSelected = !stateful2TextView.isSelected
            }
            statefulImageView.setOnClickListener { v ->
                statefulImageView.isSelected = !statefulImageView.isSelected
            }
            changeStyle1Btn.setOnClickListener { v ->
                statefulImgTextView.setSolid(ContextCompat.getColor(this@StatefulViewDemoActivity, R.color.colorPrimary))
                // 获取设备型号（类似于 adb shell getprop ro.product.model）
                val result = executeCommandWithErrorStream("getprop ro.product.model")
                Log.d(TAG, "设备型号: $result")
            }
        }
    }

    override fun onClick(v: View?) {

    }

    companion object Companion {
        @JvmStatic
        fun go(context: Context) {
            val intent = Intent(context, StatefulViewDemoActivity::class.java)
            context.startActivity(intent)
        }
    }

    fun executeCommandWithErrorStream(command: String): String {
        val output = StringBuilder()
        try {
            val process = Runtime.getRuntime().exec(command)

            // 读取标准输出流（正常信息）
            val inputReader = BufferedReader(InputStreamReader(process.inputStream))
            // 读取错误流
            val errorReader = BufferedReader(InputStreamReader(process.errorStream))

            var line: String?
            // 读取标准输出
            while (inputReader.readLine().also { line = it } != null) {
                output.append("[OUT] $line\n")
            }
            // 读取错误信息
            while (errorReader.readLine().also { line = it } != null) {
                output.append("[ERR] $line\n")
            }

            process.waitFor()
        } catch (e: Exception) {
            return "Exception: ${e.message}"
        }
        return output.toString()
    }
}
