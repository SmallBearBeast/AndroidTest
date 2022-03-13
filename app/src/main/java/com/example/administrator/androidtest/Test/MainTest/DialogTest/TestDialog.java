package com.example.administrator.androidtest.Test.MainTest.DialogTest;

import androidx.fragment.app.FragmentActivity;

import com.example.administrator.androidtest.R;
import com.example.libframework.Dialog.BaseDialogFragment;

public class TestDialog extends BaseDialogFragment {
    public TestDialog(FragmentActivity activity) {
        super(activity);
    }

    @Override
    protected int layoutId() {
        return R.layout.test_dialog;
    }
}
