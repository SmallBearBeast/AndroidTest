package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.Detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.bear.libcomponent.host.ComponentActivity
import com.example.administrator.androidtest.databinding.ActTiktokVideoDetailBinding

class TikTokVideoDetailActivity : ComponentActivity<ActTiktokVideoDetailBinding>() {
    companion object {
        @JvmStatic
        fun go(context: Context) {
            val intent = Intent(context, TikTokVideoDetailActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun inflateViewBinding(inflater: LayoutInflater) = ActTiktokVideoDetailBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        regComponent(ToolbarComponent())
        regComponent(AdapterComponent())
    }
}
