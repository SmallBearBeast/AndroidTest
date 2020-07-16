package com.example.administrator.androidtest.Test.FragTest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bear.libcomponent.ComponentFrag;
import com.example.administrator.androidtest.R;

public class TestFrag extends ComponentFrag {
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected int layoutId() {
        return R.layout.act_frag_test;
    }
}
