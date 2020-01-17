package com.example.administrator.androidtest.Test.Frag.visibility;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.ViewModelTest.LifeCycleTestObserver;
import com.example.libframework.ActAndFrag.ComponentFrag;
import com.example.libframework.Component.FragLifeDebug;
import com.example.libframework.Page.IPage;
import com.example.liblog.SLog;

public class FragmentSeven extends ComponentFrag {

    @Override
    public int layoutId() {
        return R.layout.frag_text_7;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public int pageId() {
        return IPage.FragmentSeven;
    }
}
