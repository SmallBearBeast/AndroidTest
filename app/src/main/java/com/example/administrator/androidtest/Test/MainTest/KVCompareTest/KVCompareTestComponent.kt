package com.example.administrator.androidtest.Test.MainTest.KVCompareTest

import android.view.View
import com.example.administrator.androidtest.R
import com.example.administrator.androidtest.Test.MainTest.TestComponent

class KVCompareTestComponent : TestComponent() {
    override fun onCreate() {
        clickListener(this, R.id.kvCompareTestButton)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.kvCompareTestButton -> {
                KVCompareTestAct.start(dependence)
            }
        }
    }
}