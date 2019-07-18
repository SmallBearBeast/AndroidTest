package com.example.administrator.androidtest.Test.Frag.visibility;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.administrator.androidtest.Base.ActAndFrag.ComponentFrag;
import com.example.administrator.androidtest.Base.Page.IPage;
import com.example.administrator.androidtest.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentThreeVp extends ComponentFrag {

    private ViewPager vpContainer;
    private MyAdapter myAdapter;

    @Override
    public int layoutId() {
        return R.layout.frag_vp;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        vpContainer = mContentView.findViewById(R.id.vp_container);
        myAdapter = new MyAdapter(getChildFragmentManager());
        vpContainer.setAdapter(myAdapter);
        vpContainer.setOffscreenPageLimit(myAdapter.getCount());
    }

    @Override
    public int pageId() {
        return IPage.FragmentThreeVp;
    }


    static class MyAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList = new ArrayList<>();
        public MyAdapter(FragmentManager fm) {
            super(fm);
            fragmentList.add(new FragmentFour());
            fragmentList.add(new FragmentFive());
            fragmentList.add(new FragmentSix());
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
