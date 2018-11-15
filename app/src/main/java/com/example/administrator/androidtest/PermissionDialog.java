package com.example.administrator.androidtest;

import android.view.Gravity;
import android.view.ViewGroup;

import com.example.administrator.androidtest.Common.Util.DensityUtil;

public class PermissionDialog extends BaseDialog {
    @Override
    protected int layoutId() {
        return R.layout.dialog_permission;
    }

    @Override
    protected int[] getWidthAndHeight() {
        return new int[]{
                ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2Px(mActivity,200)
        };
    }

    @Override
    protected int getGravity() {
        return Gravity.CENTER;
    }
}
