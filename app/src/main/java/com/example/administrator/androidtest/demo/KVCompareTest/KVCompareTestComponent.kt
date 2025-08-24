package com.example.administrator.androidtest.demo.KVCompareTest

import android.view.View
import androidx.lifecycle.Lifecycle
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.demo.TestActivityComponent

class KVCompareTestComponent(lifecycle: Lifecycle?) : TestActivityComponent(lifecycle) {
    override fun onCreate() {
        viewBinding?.kvCompareTestButton?.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.kvCompareTestButton -> {
                context?.let {
                    KVCompareTestActivity.start(it)
                }
            }
        }
    }
}