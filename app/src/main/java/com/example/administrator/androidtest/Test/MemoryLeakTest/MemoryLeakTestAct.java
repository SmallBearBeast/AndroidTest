package com.example.administrator.androidtest.Test.MemoryLeakTest;

import android.view.View;
import com.example.administrator.androidtest.R;
import com.example.libframework.CoreUI.ComponentAct;

public class MemoryLeakTestAct extends ComponentAct {
    @Override
    protected int layoutId() {
        return R.layout.act_memory_leak_test;
    }

    public void onClick(View view) {
        FirstAct.go(this);
    }

}
