package com.example.administrator.androidtest.demo.LibraryDemo;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.TestActivityComponent;

public class LibraryDemoComponent extends TestActivityComponent {

    private static final String TAG = "LibraryDemoComponent";

    public LibraryDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

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
