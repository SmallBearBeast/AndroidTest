package com.example.administrator.androidtest.demo.DialogTest;

import androidx.fragment.app.FragmentActivity;

import com.example.administrator.androidtest.R;
import com.bear.libbase.dialog.BaseDialogFragment;

public class TestDialog extends BaseDialogFragment {
    public TestDialog(FragmentActivity activity) {
        super(activity);
    }

    @Override
    protected int layoutId() {
        return R.layout.test_dialog;
    }
}
