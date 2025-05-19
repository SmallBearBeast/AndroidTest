package com.example.administrator.androidtest.other.Frag.visibility;

import com.bear.libcomponent.component.ComponentFragment;
import com.example.administrator.androidtest.R;
import com.bear.libother.page.IPage;
import com.bear.liblog.SLog;

public class FragmentOne extends ComponentFragment {


    /**
        这个方法只有调用FragmentTransaction->hide()和show()方法时候才会回调
        hide()时候hidden值为true
        show()时候hidden值为false
        不会触发fragment其他生命周期(比如onResume()，onPause()，setUserVisibleHint())
        FragmentTransaction->add()和remove()方法是对应的，会触发生命周期方法
        add()时候会走onResume()
        remove()时候会走onPause()
        FragmentTransaction->replace()相当于调用了先remove()再add()
        以上这些操作都不会触发setUserVisibleHint()方法
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public int layoutId() {
        return R.layout.frag_text_1;
    }

    public int pageId() {
        return IPage.FragmentOne;
    }

    @Override
    protected void onFirstVisible() {
        super.onFirstVisible();
        SLog.d(TAG, "onFirstVisible");
    }
}
