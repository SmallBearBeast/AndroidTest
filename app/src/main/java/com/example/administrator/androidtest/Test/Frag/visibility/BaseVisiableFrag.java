package com.example.administrator.androidtest.Test.Frag.visibility;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.administrator.androidtest.App;
import com.example.libframework.ActAndFrag.ComponentFrag;

import java.util.List;
import java.util.Map;

public abstract class BaseVisiableFrag extends ComponentFrag {

    private boolean waitingShowToUser;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 如果自己是显示状态，但父Fragment却是隐藏状态，就把自己也改为隐藏状态，并且设置一个等待显示标记
        if(getUserVisibleHint()){
            Fragment parentFragment = getParentFragment();
            if(parentFragment != null && !parentFragment.getUserVisibleHint()){
                waitingShowToUser = true;
                setUserVisibleHint(false);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);
        setToMap(isVisibleToUser);
        // 父Fragment还没显示，你着什么急
        if (isVisibleToUser) {
            Fragment parentFragment = getParentFragment();
            if (parentFragment != null && !parentFragment.getUserVisibleHint()) {
                waitingShowToUser = true;
                super.setUserVisibleHint(false);
                return;
            }
        }

        if(getActivity() != null) {
            List<Fragment> childFragmentList = getChildFragmentManager().getFragments();
            if (isVisibleToUser) {
                // 将所有正等待显示的子Fragment设置为显示状态，并取消等待显示标记
                if (childFragmentList != null && childFragmentList.size() > 0) {
                    for (Fragment childFragment : childFragmentList) {
                        if (childFragment instanceof BaseVisiableFrag) {
                            BaseVisiableFrag childBaseFragment = (BaseVisiableFrag) childFragment;
                            if (childBaseFragment.isWaitingShowToUser()) {
                                childBaseFragment.setWaitingShowToUser(false);
                                childFragment.setUserVisibleHint(true);
                            }
                        }
                    }
                }
            } else {
                // 将所有正在显示的子Fragment设置为隐藏状态，并设置一个等待显示标记
                if (childFragmentList != null && childFragmentList.size() > 0) {
                    for (Fragment childFragment : childFragmentList) {
                        if (childFragment instanceof BaseVisiableFrag) {
                            BaseVisiableFrag childBaseFragment = (BaseVisiableFrag) childFragment;
                            if (childFragment.getUserVisibleHint()) {
                                childBaseFragment.setWaitingShowToUser(true);
                                childFragment.setUserVisibleHint(false);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean isWaitingShowToUser() {
        return waitingShowToUser;
    }

    public void setWaitingShowToUser(boolean waitingShowToUser) {
        this.waitingShowToUser = waitingShowToUser;
    }

    //保留子fragment页面位置
    private int lastChildFragment = 0;

    /*

     */
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        setToMap(isVisibleToUser);
//        super.setUserVisibleHint(isVisibleToUser);
//        if(getActivity() != null){
//            List<Fragment> childs = getChildFragmentManager().getFragments();
//            boolean hasChild = (childs != null && childs.size() > 0);
//            if(hasChild){
//                for (int i = 0; i < childs.size(); i++) {
//                    Fragment f = childs.get(i);
//                    if(f.getUserVisibleHint()){
//                        lastChildFragment = i;
//                    }
//                    f.setUserVisibleHint(false);
//                }
//                if(isVisibleToUser)
//                    childs.get(lastChildFragment).setUserVisibleHint(true);
//            }
//        }
//    }

    /*
        只有fragment里面有嵌套fragment才会调用改方法
        解决初始化时候多个子fragment的visiable为true
        依据是 只要父类fragment的visiable为false，那么子fragment肯定就是不显示，都设置为false
     */
//    @Override
//    public void onAttachFragment(Fragment childFragment) {
//        super.onAttachFragment(childFragment);
//        boolean isVisibleToUser = getUserVisibleHint();
//        if (!isVisibleToUser)
//            childFragment.setUserVisibleHint(false);
//    }

    private void setToMap(boolean isVisibleToUser) {
        Map<String, Boolean> map = App.FragVisibiableMap;
        String name = getClass().getSimpleName();
        map.put(name, isVisibleToUser);
    }


    public interface FragVisiableListener {
        void onVisibilityChanged();
    }
}
