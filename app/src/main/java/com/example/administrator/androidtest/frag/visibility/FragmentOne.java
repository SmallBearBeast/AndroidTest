package com.example.administrator.androidtest.frag.visibility;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.androidtest.BaseFrag;
import com.example.administrator.androidtest.R;

public class FragmentOne extends BaseFrag {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_text_1, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
        这个方法只有调用FragmentTransaction->hide()和show()方法时候才会回调
        hide()时候hidden值为true
        show()时候hidden值为false
        不会触发fragment其他生命周期(比如onResume()，onPause()，setUserVisibleHint())
        FragmentTransaction->add()和remove()方法是对应的，会触发生命周期方法
        add()时候会走onResume()
        remove()时候会走onPause()
        FragmentTransaction->replace()相当于调用了先remove()再add()
        以上这些操作都不会触发setUserVisibleHint()方法
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}
