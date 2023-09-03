package com.example.administrator.androidtest.Test.MainTest.ToolbarTest;

import android.view.View;

import com.bear.libcomponent.component.ActivityComponent;
import com.example.administrator.androidtest.R;

public class ToolbarTestComponent extends ActivityComponent implements View.OnClickListener{
    @Override
    protected void onCreate() {
        findViewAndSetListener(this, R.id.toolbarTestButton);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbarTestButton:
                ToolbarTestAct.start(getContext());
                break;
        }
    }
}
