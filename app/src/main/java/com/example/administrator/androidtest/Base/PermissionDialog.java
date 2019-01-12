package com.example.administrator.androidtest.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.administrator.androidtest.Common.Util.ScreenUtil;
import com.example.administrator.androidtest.R;


public class PermissionDialog extends BaseDialog {
    public PermissionDialog(@NonNull Context context) {
        super(context);
    }

    public PermissionDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected int layoutId() {
        return R.layout.dialog_permission;
    }

//    @Override
//    protected int[] getWidthAndHeight() {
//        return new int[]{
//                DensityUtil.dip2Px(mContext, 300), DensityUtil.dip2Px(mContext, 400)
//                //ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
//        };
//    }

    //DensityUtil.dip2Px(mContext, 300), DensityUtil.dip2Px(mContext, 400)


    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
//        ScreenUtil.immersiveFullScreen(getWindow());
    }

    @Override
    protected float getDim() {
        return 0.0f;
    }
}
