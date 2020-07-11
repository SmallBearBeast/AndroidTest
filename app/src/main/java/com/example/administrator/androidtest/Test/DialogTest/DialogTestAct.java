package com.example.administrator.androidtest.Test.DialogTest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.view.View;

import com.bear.libcomponent.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.libframework.Dialog.BaseDialogFragment;

public class DialogTestAct extends ComponentAct {
    private TestDialog_1 mTestDialog_1;

    @Override
    protected int layoutId() {
        return R.layout.act_dialog_test;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTestDialog_1 = new TestDialog_1(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_1:
                mTestDialog_1.show();
                break;
        }
    }


    public static class TestDialog_1 extends BaseDialogFragment {
        public TestDialog_1(FragmentActivity activity) {
            super(activity);
        }

        @Override
        protected int layoutId() {
            return R.layout.dialog_permission;
        }
    }
}
