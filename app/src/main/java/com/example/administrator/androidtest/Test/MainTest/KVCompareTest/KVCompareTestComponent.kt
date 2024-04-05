package com.example.administrator.androidtest.Test.MainTest.KVCompareTest

import android.view.View
import androidx.lifecycle.Lifecycle
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent

class KVCompareTestComponent(lifecycle: Lifecycle?) : TestActivityComponent(lifecycle) {
    override fun onCreate() {
        setOnClickListener(this, R.id.kvCompareTestButton)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.kvCompareTestButton -> {
                KVCompareTestAct.start(context)
            }
        }
    }
}