package com.example.administrator.androidtest.Test.Frag.visibility;

import android.os.Bundle;

import com.example.administrator.androidtest.R;
import com.example.libframework.ActAndFrag.ComponentFrag;
import com.example.libframework.Page.IPage;

public class FragmentFour extends ComponentFrag {


    @Override
    public int layoutId() {
        return R.layout.frag_text_4;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public int pageId() {
        return IPage.FragmentFour;
    }
}
