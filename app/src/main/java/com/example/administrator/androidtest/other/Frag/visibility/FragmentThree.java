package com.example.administrator.androidtest.other.Frag.visibility;

import com.bear.libcomponent.component.ComponentFrag;
import com.example.administrator.androidtest.R;
import com.bear.libother.page.IPage;
import com.example.liblog.SLog;

public class FragmentThree extends ComponentFrag {


    @Override
    public int layoutId() {
        return R.layout.frag_text_3;
    }

    public int pageId() {
        return IPage.FragmentThree;
    }

    @Override
    protected void onFirstVisible() {
        super.onFirstVisible();
        SLog.d(TAG, "onFirstVisible");
    }
}
