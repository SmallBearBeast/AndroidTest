package com.example.administrator.androidtest.demo.bizdemo.tiktokdemo

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.Lifecycle
import com.bear.libcomponent.component.ui.ActivityComponent
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.databinding.ActBizDemoListBinding
import com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.detail.TikTokVideoDetailActivity

class TikTokDemoComponent(lifecycle: Lifecycle?) : ActivityComponent<ActBizDemoListBinding>(lifecycle), View.OnClickListener {
    override fun onCreate() {
        requireBinding().tiktokDemoButton.setOnClickListener(this)
    }

    @SuppressLint("NonConstantResourceId")
    override fun onClick(view: View) {
        when (view.id) {
            R.id.tiktokDemoButton -> {
                TikTokVideoDetailActivity.go(requireContext())
            }

            else -> Unit
        }
    }
}
