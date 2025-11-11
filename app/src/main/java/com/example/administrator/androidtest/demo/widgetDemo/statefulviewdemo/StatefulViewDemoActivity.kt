package com.example.administrator.androidtest.demo.widgetDemo.statefulviewdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.bear.libcomponent.host.ComponentActivity
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.databinding.ActStatefulVideoDemoBinding

class StatefulViewDemoActivity : ComponentActivity<ActStatefulVideoDemoBinding>(), View.OnClickListener {
    override fun inflateViewBinding(inflater: LayoutInflater): ActStatefulVideoDemoBinding {
        return ActStatefulVideoDemoBinding.inflate(inflater)
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
            changeStyle1Btn.setOnClickListener { v ->
                statefulImgTextView.setSolid(ContextCompat.getColor(this@StatefulViewDemoActivity, R.color.colorPrimary))
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
}
