package com.example.libframework.ActAndFrag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.example.libframework.Page.IPage;
import com.example.libframework.Page.Page;
import com.example.libframework.Page.PageProvider;

import java.util.List;
import java.util.Map;

public abstract class BaseFrag extends Fragment implements IPage {
    private static String TAG = "BaseFrag";
    private boolean foreground = false;
    //保留上一次可见的子fragment页面位置
    private int mLastVisibleFragPos = 0;
    private boolean mIsDoneSetUserVisibleHint;
    private boolean mIsVisibleToUser;
    private boolean mIsDoneStart;
    protected int fragmentId = IContext.FRAGMENT_ID_NONE; /**相同类型fragment复用时候需要一个fragmentId来区分**/
    private Page mPage;
    protected BaseAct mBaseAct;
    protected BaseFrag mBaseFrag;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getContext() instanceof BaseAct){
            mBaseAct = (BaseAct) getContext();
        }
        if(getParentFragment() instanceof BaseFrag){
            mBaseFrag = (BaseFrag) getParentFragment();
        }
        Intent intent = mBaseAct.getIntent();
        if(intent != null){
            handleIntent(intent, intent.getBundleExtra(IContext.BUNDLE));
        }
        Bundle bundle = getArguments();
        if(bundle != null){
            handleArgument(bundle);
            fragmentId = bundle.getInt(IContext.FRAGMENT_ID, IContext.FRAGMENT_ID_NONE);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsDoneSetUserVisibleHint = true;
        mIsVisibleToUser = isVisibleToUser;
        if (getActivity() != null) {
            if(mIsDoneStart && mIsVisibleToUser){
                addPage(null);
                onNotifyVisiable();
            }
            List<Fragment> childs = getChildFragmentManager().getFragments();
            boolean hasChild = (childs != null && childs.size() > 0);
            if (hasChild) {
                for (int i = 0; i < childs.size(); i++) {
                    Fragment f = childs.get(i);
                    if (f.getUserVisibleHint())
                        mLastVisibleFragPos = i;
                    if (mLastVisibleFragPos == i)
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
        mIsDoneStart = true;
        if(mIsDoneSetUserVisibleHint){
            if(mIsVisibleToUser) {
                addPage(null);
                onNotifyVisiable();
            }
        }else{
            addPage(null);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden){
            PageProvider.getInstance().addPage(mBaseAct.getPage(), createPage());
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPage = null;
    }

    /**
     * 通知应用是否在前后台，获取应用前后台状态
     */
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

    public boolean isForeground() {
        return foreground;
    }

    protected void onNotifyForeground(boolean fore) {
        Log.d(TAG, "class = " + getClass().getSimpleName() + "   " + "onNotifyForeground: fore = " + fore);
    }
    /**通知应用是否在前后台，获取应用前后台状态**/

    /**
     * 页面处理相关方法
     */
    private Page createPage(){
        mPage = new Page(pageId());
        return mPage;
    }

    public Page getPage(){
        return mPage;
    }

    private void addPage(Page backPage){
        if(mBaseFrag != null){
            PageProvider.getInstance().addPage(mBaseFrag.getPage(), backPage != null ? backPage : createPage());
        }else {
            PageProvider.getInstance().addPage(mBaseAct.getPage(), backPage != null ? backPage : createPage());
        }
    }
    /**页面处理相关方法**/

    /**
     * Bundle传递相关方法
     */
    protected static Bundle buildArguments(int id){
        Bundle bundle = new Bundle();;
        bundle.putInt(IContext.FRAGMENT_ID, id);
        return bundle;
    }

    protected void handleIntent(@NonNull Intent intent, @Nullable Bundle bundle){}

    protected void handleArgument(@NonNull Bundle bundle){};
    /**Bundle传递相关方法**/

    /**
     * Fragment需要实现的方法
     */
    protected abstract int layoutId();

    protected abstract void init(Bundle savedInstanceState);
    /**Fragment需要实现的方法**/


    /**
     * fragment可见性时候调用
     */
    protected void onNotifyVisiable() {
        Log.d(TAG, "class = " + getClass().getSimpleName() + "   " + "onNotifyVisiable");
    }
}
