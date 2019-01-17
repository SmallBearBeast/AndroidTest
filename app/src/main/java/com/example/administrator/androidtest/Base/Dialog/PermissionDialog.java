package com.example.administrator.androidtest.Base.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.administrator.androidtest.Base.Dialog.BaseDialog;
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
//        ScreenUtil.normalScreen(getWindow(), R.color.cl_transparent, IContext.COLOR_ID_NONE, false, true, null);
    }

    @Override
    protected float getDim() {
        return 0.0f;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}
