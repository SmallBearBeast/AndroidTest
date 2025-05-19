package com.example.administrator.androidtest.other.Frag.visibility;

import com.bear.libcomponent.component.ComponentFrag;
import com.example.administrator.androidtest.R;
import com.bear.libother.page.IPage;
import com.example.liblog.SLog;

public class FragmentTwo extends ComponentFrag {
    @Override
    public int layoutId() {
        return R.layout.frag_text_2;
    }

    public int pageId() {
        return IPage.FragmentTwo;
    }

    @Override
    protected void onFirstVisible() {
        super.onFirstVisible();
        SLog.d(TAG, "onFirstVisible");
    }
}
