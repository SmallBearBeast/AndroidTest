package com.example.libframework.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseDialog extends Dialog {
    protected Context mContext;
    protected Window mWindow;

    public BaseDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        mWindow = getWindow();
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(layoutId());
        setup();
        init(savedInstanceState);
    }

    /**
     * 设置对话框的大小，显示位置，dim，动画，可重写。
     */
    protected void setup() {
        if(mWindow != null){
            mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mWindow.setGravity(getGravity());
            WindowManager.LayoutParams lp = mWindow.getAttributes();
            if(isFullScreen()){
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            } else if(getWidthAndHeight() != null){
                lp.width = getWidthAndHeight()[0];
                lp.height = getWidthAndHeight()[1];
            }
            lp.dimAmount = getDim();
            mWindow.setAttributes(lp);
        }
    }

    /**
     * 重写这个方法重新设置对话框的宽高，返回一个int[2]数组
     */
    protected int[] getWidthAndHeight(){
        return null;
    }

    /**
     * 重写这个方法重新设置对话框的显示位置
     * @return
     */
    protected int getGravity(){
        return Gravity.CENTER;
    }

    protected float getDim(){
        return 0.5f;
    }

    /**
     * 设置是否全屏对话框
     */
    protected boolean isFullScreen(){
        return false;
    }

    /**
     * UI初始化和其他变量初始化和恢复数据
     */
    protected void init(Bundle savedInstanceState){}


    protected abstract int layoutId();

    @Override
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
