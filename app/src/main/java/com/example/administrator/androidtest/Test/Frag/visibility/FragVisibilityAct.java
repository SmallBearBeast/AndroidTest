package com.example.administrator.androidtest.Test.Frag.visibility;

import android.os.Bundle;
import android.view.View;
import com.example.administrator.androidtest.Base.ActAndFrag.ComponentAct;
import com.example.administrator.androidtest.Common.Page.IPage;
import com.example.administrator.androidtest.R;


public class FragVisibilityAct extends ComponentAct {

    private FragmentOne fragmentOne;
    private FragmentTwo fragmentTwo;
    private FragmentThree fragmentThree;

    @Override
    protected int layoutId() {
        return R.layout.act_frag_visibility;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        fragmentOne = new FragmentOne();
        fragmentTwo = new FragmentTwo();
        fragmentThree = new FragmentThree();
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add_frag:
                getSupportFragmentManager().beginTransaction().add(R.id.ll_frag_container, fragmentOne).commit();
                break;

            case R.id.bt_show_frag:
                getSupportFragmentManager().beginTransaction().hide(fragmentOne).commit();
                break;

            case R.id.bt_replace_frag:
                getSupportFragmentManager().beginTransaction().replace(R.id.ll_frag_container, fragmentTwo).show(fragmentTwo).commit();
                break;
        }
    }

    @Override
    public int pageId() {
        return IPage.FragVisibilityAct;
    }
}
