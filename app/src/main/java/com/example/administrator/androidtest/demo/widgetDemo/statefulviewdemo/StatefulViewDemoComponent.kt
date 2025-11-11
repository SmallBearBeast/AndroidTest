package com.example.administrator.androidtest.demo.widgetDemo.statefulviewdemo

import android.view.View
import androidx.lifecycle.Lifecycle
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.demo.widgetDemo.BaseWidgetDemoComponent

class StatefulViewDemoComponent(lifecycle: Lifecycle?) : BaseWidgetDemoComponent(lifecycle) {
    override fun onCreate() {
        super.onCreate()
        requireBinding().statefulViewButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.statefulViewButton -> StatefulViewDemoActivity.go(requireContext())
        }
    }
}
