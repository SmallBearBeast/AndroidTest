package com.example.administrator.androidtest.Test.Frag.visibility;

import com.bear.libcomponent.ComponentFrag;
import com.example.administrator.androidtest.R;
import com.example.libframework.Page.IPage;
import com.example.liblog.SLog;

public class FragmentSix extends ComponentFrag {

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
