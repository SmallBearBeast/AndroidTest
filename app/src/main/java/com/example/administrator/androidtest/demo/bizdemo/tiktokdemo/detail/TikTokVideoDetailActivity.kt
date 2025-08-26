package com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.bear.libcomponent.host.ComponentActivity
import com.example.administrator.androidtest.databinding.ActTiktokVideoDetailBinding
import com.example.administrator.androidtest.demo.ext.setupNormalSystemBar

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
        setupNormalSystemBar(lightStatusBars = false, lightNavigationBars = false)
        regComponent(ToolbarComponent())
        regComponent(AdapterComponent())
        regComponent(DataLoaderComponent())
    }
}
