package com.example.administrator.androidtest.Common.Case;


import com.example.administrator.androidtest.R;

public class LoadingCaseInfo extends CaseInfo {

    @Override
    protected int type() {
        return CaseHelper.CASE_TYPE_LOADING;
    }

    @Override
    protected boolean showProgress() {
        return true;
    }

    @Override
    protected int title() {
        return R.string.app_name;
    }

    @Override
    protected int description() {
        return R.string.bottom_sheet_behavior;
    }

    @Override
    protected int clickText() {
        return R.string.app_name;
    }
}
