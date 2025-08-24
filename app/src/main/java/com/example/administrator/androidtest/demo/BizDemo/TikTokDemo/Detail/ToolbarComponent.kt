package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.Detail

import com.bear.libcomponent.component.ui.ActivityComponent
import com.example.administrator.androidtest.databinding.ActTiktokVideoDetailBinding

class ToolbarComponent : ActivityComponent<ActTiktokVideoDetailBinding>() {
    override fun onCreate() {
        super.onCreate()
        requireBinding().apply {
            toolbar.setNavigationOnClickListener {
                activity?.finish()
            }
        }
    }
}