package com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.detail

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