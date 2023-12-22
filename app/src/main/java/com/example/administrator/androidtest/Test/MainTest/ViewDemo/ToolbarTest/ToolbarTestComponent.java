package com.example.administrator.androidtest.Test.MainTest.ViewDemo.ToolbarTest;

import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestComponent;

public class ToolbarTestComponent extends TestComponent implements View.OnClickListener{
    @Override
    protected void onCreate() {
        super.onCreate();
        setOnClickListener(this, R.id.toolbarTestButton);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbarTestButton) {
            ToolbarTestAct.start(getContext());
        }
    }
}
