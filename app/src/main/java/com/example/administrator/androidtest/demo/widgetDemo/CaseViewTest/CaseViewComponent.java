package com.example.administrator.androidtest.demo.widgetDemo.CaseViewTest;


import androidx.lifecycle.Lifecycle;

import com.bear.libcommon.util.ToastUtil;
import com.example.administrator.androidtest.demo.widgetDemo.BaseWidgetDemoComponent;
import com.example.administrator.androidtest.demo.widgetDemo.CaseViewTest.Case.CaseHelper;

public class CaseViewComponent extends BaseWidgetDemoComponent {
    public CaseViewComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        CaseHelper.showTestCaseView(getBinding().caseView, view -> {
            ToastUtil.showToast("Click the case view button");
        });
    }
}
