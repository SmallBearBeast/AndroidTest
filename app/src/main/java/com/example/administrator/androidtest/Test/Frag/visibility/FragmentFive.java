package com.example.administrator.androidtest.Test.Frag.visibility;

import com.example.administrator.androidtest.R;
import com.example.libframework.CoreUI.ComponentFrag;
import com.example.libframework.Page.IPage;
import com.example.liblog.SLog;

public class FragmentFive extends ComponentFrag {

    @Override
    public int layoutId() {
        return R.layout.frag_text_5;
    }

    public int pageId() {
        return IPage.FragmentFive;
    }

    @Override
    protected void onFirstVisible() {
        super.onFirstVisible();
        SLog.d(TAG, "onFirstVisible");
    }
}
