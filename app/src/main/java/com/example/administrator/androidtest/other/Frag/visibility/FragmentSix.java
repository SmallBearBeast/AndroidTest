package com.example.administrator.androidtest.other.Frag.visibility;

import com.bear.libcomponent.component.ComponentFragment;
import com.example.administrator.androidtest.R;
import com.bear.libother.page.IPage;
import com.example.liblog.SLog;

public class FragmentSix extends ComponentFragment {

    @Override
    public int layoutId() {
        return R.layout.frag_text_6;
    }

    public int pageId() {
        return IPage.FragmentSix;
    }

    @Override
    protected void onFirstVisible() {
        super.onFirstVisible();
        SLog.d(TAG, "onFirstVisible");
    }
}
