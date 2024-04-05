package com.example.administrator.androidtest.Test.MainTest.WidgetDemo.CaseViewTest;


import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.WidgetDemo.CaseViewTest.Case.CaseHelper;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;
import com.example.libbase.Util.ToastUtil;

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
