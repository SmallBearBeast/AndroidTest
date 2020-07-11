package com.example.administrator.androidtest.Test.Frag.visibility;

import com.bear.libcomponent.ComponentFrag;
import com.example.administrator.androidtest.R;
import com.example.libframework.Page.IPage;
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
