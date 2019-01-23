package com.example.administrator.androidtest.Base.ActAndFrag;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.administrator.androidtest.App;
import com.example.administrator.androidtest.Common.Page.IPage;
import com.example.administrator.androidtest.Common.Page.Page;
import com.example.administrator.androidtest.Common.Page.PageProvider;

import java.util.List;
import java.util.Map;

public abstract class BaseFrag extends Fragment implements IPage {
    private static String TAG = "BaseFrag";
    private boolean foreground = false;
    //保留子fragment页面位置
    private int lastChildFragment = 0;
    private boolean mIsReal;

    protected int fragmentId = IContext.FRAGMENT_ID_NONE; /**相同类型fragment复用时候需要一个fragmentId来区分**/
    protected BaseAct mBaseAct;

    public void setUserVisibleHint(boolean isVisibleToUser, boolean isReal){
        setUserVisibleHint(isVisibleToUser);
        mIsReal = isReal;
    }

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
                        f.setUserVisibleHint(isVisibleToUser);
                }
            }
        }
    }

    /**
     * 只有fragment里面有嵌套fragment才会调用改方法
     * 解决初始化时候多个子fragment的visiable为true
     * 依据是 只要父类fragment的visiable为false，那么子fragment肯定就是不显示，都设置为false
     */
    @Override
    public void onAttachFragment(Fragment childFragment) {
        if (childFragment instanceof BaseFrag) {
            ((BaseFrag) childFragment).notifyForeground(foreground);
        }
        boolean isVisibleToUser = getUserVisibleHint();
        if (!isVisibleToUser) {
            if (childFragment.getUserVisibleHint()) {
                childFragment.setUserVisibleHint(false);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        PageProvider.getInstance().addPage(mBaseAct, this);
    }

    public void notifyForeground(boolean fore) {
        foreground = fore;
        onNotifyForeground(fore);
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment frag : fragments) {
                if(frag instanceof BaseFrag){
                    ((BaseFrag) frag).notifyForeground(fore);
                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            mBaseAct = (BaseAct) context;
            Intent intent = mBaseAct.getIntent();
            if(intent != null){
                handleIntent(intent, intent.getBundleExtra(IContext.BUNDLE));
            }
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            handleArgument(bundle);
            fragmentId = bundle.getInt(IContext.FRAGMENT_ID, IContext.FRAGMENT_ID_NONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void onNotifyForeground(boolean fore) {
        Log.e(TAG, "class = " + getClass().getSimpleName() + "   " + "onNotifyForeground: fore = " + fore);
    }

    /**
     * fragment可见性会多次调用，但是会保证最后一次调用是正确的
     */
    protected void onNotifyVisiable(boolean visiable) {
        Log.e(TAG, "class = " + getClass().getSimpleName() + "   " + "onNotifyVisiable: fore = " + visiable);
    }

    public boolean isForeground() {
        return foreground;
    }

    protected static Bundle buildArguments(int id){
        Bundle bundle = buildArguments();
        bundle.putInt(IContext.ARGUMENT, id);
        return bundle;
    }

    protected static Bundle buildArguments(){
        return new Bundle();
    }

    protected void handleIntent(Intent intent, Bundle bundle){}

    protected void handleArgument(Bundle bundle){};

    public abstract int layoutId();

    public abstract void init(Bundle savedInstanceState);

    @Override
    public Page page() {
        return null;
    }

    /**
     * 测试fragment可见性方法
     */
    private void setToMap(boolean isVisibleToUser) {
        Map<String, Boolean> map = App.FragVisibiableMap;
        String name = getClass().getSimpleName();
        map.put(name, isVisibleToUser);
        App.fragVisiableListener.onVisibilityChanged();
    }


    public interface FragVisiableListener {
        void onVisibilityChanged();
    }

    /****/
}
