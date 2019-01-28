package com.example.administrator.androidtest.frag.visibility;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.administrator.androidtest.Base.ActAndFrag.ComponentFrag;
import com.example.administrator.androidtest.Common.Page.IPage;
import com.example.administrator.androidtest.Common.Page.Page;
import com.example.administrator.androidtest.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentOneVp extends ComponentFrag {

    private ViewPager vpContainer;
    private MyAdapter myAdapter;

    @Override
    public int layoutId() {
        return R.layout.frag_vp;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        vpContainer = contentView.findViewById(R.id.vp_container);
        myAdapter = new MyAdapter(getChildFragmentManager());
        vpContainer.setAdapter(myAdapter);
        vpContainer.setOffscreenPageLimit(myAdapter.getCount());
        vpContainer.setCurrentItem(2);
    }

    @Override
    public int pageId() {
        return IPage.FragmentOneVp;
    }


    static class MyAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragmentList = new ArrayList<>();

        public MyAdapter(FragmentManager fm) {
            super(fm);
            fragmentList.add(new FragmentOne());
            fragmentList.add(new FragmentTwo());
            fragmentList.add(new FragmentThree());
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
