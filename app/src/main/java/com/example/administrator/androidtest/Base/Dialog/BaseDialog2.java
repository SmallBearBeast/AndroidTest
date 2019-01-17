package com.example.administrator.androidtest.Base.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseDialog2 extends DialogFragment {
    protected View mContentView;
    protected Activity mActivity;
    protected Window mWindow;
    protected Dialog mDialog;
    private boolean isSetup = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentView = LayoutInflater.from(getContext()).inflate(layoutId(), null);
        mActivity = getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!isSetup){
            isSetup = true;
            setup();
        }
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(mDialog == null){
            mDialog = super.onCreateDialog(savedInstanceState);
            mWindow = mDialog.getWindow();
            mWindow.requestFeature(Window.FEATURE_NO_TITLE);
        }
        //在onCreate()设置dialog参数发现此时dialog=null，因此放到onCreateDialog()里面执行
        //在这里设置dialog属性发现UI高度有变化
        //setup(dialog);
        return mDialog;
    }

    /**
     * 设置对话框的大小，显示位置，dim，动画
     */
    private void setup() {
        if(mWindow != null){
            mWindow.setGravity(getGravity());
            WindowManager.LayoutParams lp = mWindow.getAttributes();
            if(getWidthAndHeight() != null){
                lp.width = getWidthAndHeight()[0];
                lp.height = getWidthAndHeight()[1];
            }
            lp.dimAmount = getDim();
            mWindow.setAttributes(lp);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mContentView == null){
            mContentView = inflater.inflate(layoutId(), container, false);
            mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            init(savedInstanceState);
        }
        return mContentView;
    }

    protected abstract int layoutId();

    public Bundle buildArguments(){
        Bundle bundle = new Bundle();
        return bundle;
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
        return 0f;
    }

    /**
     * UI初始化和其他变量初始化和恢复数据
     */
    protected void init(Bundle savedInstanceState){
        
    }

}
