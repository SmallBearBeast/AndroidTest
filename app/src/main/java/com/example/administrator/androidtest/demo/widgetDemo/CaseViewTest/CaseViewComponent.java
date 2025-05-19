package com.example.administrator.androidtest.demo.widgetDemo.CaseViewTest;


import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.widgetDemo.CaseViewTest.Case.CaseHelper;
import com.example.administrator.androidtest.demo.TestActivityComponent;
import com.example.libcommon.util.ToastUtil;

public class CaseViewComponent extends TestActivityComponent {
    public CaseViewComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        CaseHelper.showTestCaseView(findViewById(R.id.caseView), view -> {
            ToastUtil.showToast("Click the case view button");
        });
    }
}
