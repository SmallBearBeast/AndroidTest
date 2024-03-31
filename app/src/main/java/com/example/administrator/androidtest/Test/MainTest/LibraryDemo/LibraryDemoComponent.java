package com.example.administrator.androidtest.Test.MainTest.LibraryDemo;

import android.annotation.SuppressLint;
import android.view.View;

import com.bear.libcomponent.component.ActivityComponent;
import com.example.administrator.androidtest.R;

public class LibraryDemoComponent extends ActivityComponent implements View.OnClickListener {

    private static final String TAG = "LibraryDemoComponent";

    @Override
    protected void onCreate() {
        setOnClickListener(this, R.id.libraryDemoButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.libraryDemoButton:
                LibraryDemoAct.go(getContext());
                break;

            default:
                break;
        }
    }
}
