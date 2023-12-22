package com.example.administrator.androidtest.Test.MainTest.WidgetDemo.CaseViewTest;


import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.WidgetDemo.CaseViewTest.Case.CaseHelper;
import com.example.administrator.androidtest.Test.MainTest.TestComponent;
import com.example.libbase.Util.ToastUtil;

public class CaseViewComponent extends TestComponent {
    @Override
    protected void onCreate() {
        CaseHelper.showTestCaseView(findViewById(R.id.caseView), view -> {
            ToastUtil.showToast("Click the case view button");
        });
    }
}
