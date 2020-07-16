package com.example.administrator.androidtest.Test.FragTest;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bear.libcomponent.ComponentAct;
import com.example.administrator.androidtest.R;

public class FragTestAct extends ComponentAct {
    @Override
    protected int layoutId() {
        return R.layout.act_frag_test;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        TestFrag testFrag = new TestFrag();
        // commit不会立马触发Fragment生命周期
        getSupportFragmentManager().beginTransaction().add(testFrag, "Hello").commit();
        // executePendingTransactions可以立即触发Fragment生命周期
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    protected void onStart() {
        // FragmentActivity的onStart方法有execPendingActions，因此正常Activity走到onStart，Fragment才开始走生命周期。
        super.onStart();
        Log.d(TAG, "onStart: ");
    }
}
