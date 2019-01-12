package com.example.administrator.androidtest.ActLifeCallbacks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.androidtest.Base.BaseFrag;
import com.example.administrator.androidtest.R;

public class FragmentOne extends BaseFrag {

    @Override
    public int layoutId() {
        return R.layout.frag_text_1;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }
}
