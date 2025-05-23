package com.example.administrator.androidtest.demo.SpAndMMKVDemo;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;

import com.bear.libkv.MmkvVal.MmkvVal;
import com.bear.libkv.SpVal.SpHelper;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.MmkvValHelper;
import com.example.administrator.androidtest.demo.SpValHelper;
import com.example.administrator.androidtest.demo.TestActivityComponent;

import java.util.Random;

public class SpAndMMKVDemoComponent extends TestActivityComponent {

    public SpAndMMKVDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        testGetSpVal();
        getBinding().setSpValButton.setOnClickListener(this);
        getBinding().getSpValButton.setOnClickListener(this);
        getBinding().spToMmkvButton.setOnClickListener(this);
        getBinding().showMoveToMMKVButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.setSpValButton:
                testSetSpVal();
                break;

            case R.id.getSpValButton:
                testGetSpVal();
                break;

            case R.id.spToMmkvButton:
                testSpToMmkv();
                break;

            case R.id.showMoveToMMKVButton:
                testShowMoveToMMKV();
                break;
        }
    }


    private void testSetSpVal() {
        Random random = new Random();
        int randomInt = random.nextInt(100);
        boolean randomBool = random.nextBoolean();
        randomInt = randomBool ? randomInt : -randomInt;
        SpValHelper.testBoolSp.set(randomBool);
        SpValHelper.testIntSp.set(randomInt);
        SpValHelper.testFloatSp.set((float) randomInt);
        SpValHelper.testStringSp.set(randomInt + "" + randomBool);
    }

    private void testGetSpVal() {
        TextView tvSpValTip = getBinding().spValTipTextView;
        StringBuilder builder = new StringBuilder();
        builder.append("testBoolSp = ").append(SpValHelper.testBoolSp.get()).append("\n");
        builder.append("testIntSp = ").append(SpValHelper.testIntSp.get()).append("\n");
        builder.append("testFloatSp = ").append(SpValHelper.testFloatSp.get()).append("\n");
        builder.append("testStringSp = ").append(SpValHelper.testStringSp.get());
        tvSpValTip.setText(builder.toString());
    }

    private void testSpToMmkv() {
        MmkvVal.importFromSharedPreferences(getContext(), MmkvValHelper.MMKV_GLOBAL_CONFIG, SpValHelper.SP_GLOBAL_CONFIG);
        MmkvVal.importFromSharedPreferences(getContext(), MmkvVal.DEFAULT_MMPVAL_ID, SpHelper.DEFAULT_SPVAL_NAME);
    }

    private void testShowMoveToMMKV() {
        TextView tvMmkvValTip = getBinding().tvMoveToMmkvTip;
        StringBuilder builder = new StringBuilder();
        builder.append("testBoolSp = ").append(MmkvValHelper.testBoolSp.get()).append("\n");
        builder.append("testIntSp = ").append(MmkvValHelper.testIntSp.get()).append("\n");
        builder.append("testFloatSp = ").append(MmkvValHelper.testFloatSp.get()).append("\n");
        builder.append("testStringSp = ").append(MmkvValHelper.testStringSp.get());
        tvMmkvValTip.setText(builder.toString());
    }
}
