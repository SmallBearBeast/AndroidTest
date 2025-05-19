package com.example.administrator.androidtest.other.Frag.visibility;

import com.bear.libcomponent.component.ComponentFrag;
import com.example.administrator.androidtest.R;
import com.example.libframework.page.IPage;
import com.example.liblog.SLog;

public class FragmentFour extends ComponentFrag {


    @Override
    public int layoutId() {
        return R.layout.frag_text_4;
    }

    public int pageId() {
        return IPage.FragmentFour;
    }

    @Override
    protected void onFirstVisible() {
        super.onFirstVisible();
        SLog.d(TAG, "onFirstVisible");
    }
}
