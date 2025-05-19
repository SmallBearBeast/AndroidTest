package com.bear.libbase.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import android.view.*;

/**
 * onAttach -> onCreate -> onCreateDialog -> onCreateView
 */
public abstract class BaseDialogFragment extends DialogFragment {
    protected View mContentView;
    protected FragmentActivity mActivity;
    protected Window mWindow;
    protected Dialog mDialog;

    protected BaseDialogFragment(FragmentActivity activity) {
        mActivity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialog = super.onCreateDialog(savedInstanceState);
        mWindow = mDialog.getWindow();
        if (mWindow != null) {
            mWindow.requestFeature(Window.FEATURE_NO_TITLE);
            mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        //在onCreate()设置dialog参数发现此时dialog=null，因此放到onCreateDialog()里面执行
        //在这里设置dialog属性发现UI高度有变化
        //setup(dialog);
        return mDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView != null) {
            if (mContentView.getParent() instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) mContentView.getParent();
                viewGroup.removeView(mContentView);
            }
        } else {
            mContentView = inflater.inflate(layoutId(), null);
        }
        init(savedInstanceState);
        return mContentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        setup();
    }

    /**
     * 设置对话框的大小，显示位置，dim，动画
     */
    private void setup() {
        if (mWindow != null) {
            mWindow.setGravity(getGravity());
            WindowManager.LayoutParams lp = mWindow.getAttributes();
            if (getWidthAndHeight() != null) {
                lp.width = getWidthAndHeight()[0];
                lp.height = getWidthAndHeight()[1];
            }
            if (getDim() != -1) {
                lp.dimAmount = getDim();
            }
            mWindow.setAttributes(lp);
        }
    }

    protected abstract int layoutId();
    protected View layoutView(){
        return null;
    }

    /**
     * 重写这个方法重新设置对话框的宽高，返回一个int[2]数组
     */
    protected int[] getWidthAndHeight() {
        return null;
    }

    /**
     * 重写这个方法重新设置对话框的显示位置
     */
    protected int getGravity() {
        return Gravity.CENTER;
    }

    /**
     * 重写这个方法控制不透明度
     */
    protected float getDim() {
        return -1F;
    }

    /**
     * UI初始化和其他变量初始化和恢复数据
     */
    protected void init(Bundle savedInstanceState) {

    }

    protected <T extends View> T findViewById(@IdRes int id) {
        if (mContentView != null) {
            return mContentView.findViewById(id);
        }
        return null;
    }

    public void show() {
        show(mActivity.getSupportFragmentManager(), getClass().getSimpleName());
    }

    public void show(@Nullable String tag) {
        show(mActivity.getSupportFragmentManager(), tag);
    }
}
