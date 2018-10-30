package com.example.administrator.androidtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

public class BaseFrag extends Fragment {
    private static String TAG = "BaseFrag";
    private boolean foreground = false;
    //保留子fragment页面位置
    private int lastChildFragment = 0;
    private boolean isAttachFragment = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        setToMap(isVisibleToUser);
        if (getActivity() != null) {
            onNotifyVisiable(isVisibleToUser);
            List<Fragment> childs = getChildFragmentManager().getFragments();
            boolean hasChild = (childs != null && childs.size() > 0);
            if (hasChild) {
                for (int i = 0; i < childs.size(); i++) {
                    Fragment f = childs.get(i);
                    if (f.getUserVisibleHint())
                        lastChildFragment = i;
                    if (lastChildFragment == i)
                        f.setUserVisibleHint(isVisibleToUser ? true : false);
                }
            }
        }
    }

    /*
        只有fragment里面有嵌套fragment才会调用改方法
        解决初始化时候多个子fragment的visiable为true
        依据是 只要父类fragment的visiable为false，那么子fragment肯定就是不显示，都设置为false
     */
    @Override
    public void onAttachFragment(Fragment childFragment) {
        isAttachFragment = true;
        if (childFragment instanceof BaseFrag) {
            ((BaseFrag) childFragment).notifyForeground(foreground);
            ((BaseFrag) childFragment).isAttachFragment = isAttachFragment;
        }
        boolean isVisibleToUser = getUserVisibleHint();
        if (!isVisibleToUser) {
            if (childFragment.getUserVisibleHint()) {
                isAttachFragment = false;
                childFragment.setUserVisibleHint(false);
                isAttachFragment = true;
            }
        }else {
            if(childFragment.getUserVisibleHint()){
                childFragment.setUserVisibleHint(true);
            }
        }
    }

    protected void onNotifyForeground(boolean fore) {
        Log.e(TAG, "class = " + getClass().getSimpleName() + "   " + "onNotifyForeground: fore = " + fore);
    }

    /*
        fragment可见性会多次调用，但是会保证最后一次调用是正确的
     */
    protected void onNotifyVisiable(boolean visiable) {
        Log.e(TAG, "class = " + getClass().getSimpleName() + "   " + "onNotifyVisiable: fore = " + visiable);
    }

    public void notifyForeground(boolean fore) {
        foreground = fore;
        onNotifyForeground(fore);
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                ((BaseFrag) frag).notifyForeground(fore);
            }
        }
    }

    public boolean isForeground() {
        return foreground;
    }

    private void setToMap(boolean isVisibleToUser) {
        Map<String, Boolean> map = App.FragVisibiableMap;
        String name = getClass().getSimpleName();
        map.put(name, isVisibleToUser);
        App.fragVisiableListener.onVisibilityChanged();
    }


    public interface FragVisiableListener {
        void onVisibilityChanged();
    }

    public Bundle buildArguments(){
        Bundle bundle = new Bundle();
        return bundle;
    }


    protected void restoreInstanceState(Bundle savedInstanceState){

    }

    protected void initView(){

    }

    protected int layoutId(){
        return -1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
