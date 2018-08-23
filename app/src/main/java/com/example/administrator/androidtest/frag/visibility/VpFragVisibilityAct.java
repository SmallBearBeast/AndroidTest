package com.example.administrator.androidtest.frag.visibility;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.administrator.androidtest.BaseAct;
import com.example.administrator.androidtest.R;

import java.util.ArrayList;
import java.util.List;

public class VpFragVisibilityAct extends BaseAct {

    private ViewPager vpContainer;
    private MyAdapter myAdapter;
    @Override
    protected void init() {
        vpContainer = findViewById(R.id.vp_container);
        myAdapter = new MyAdapter(getSupportFragmentManager());
        vpContainer.setAdapter(myAdapter);
        vpContainer.setOffscreenPageLimit(myAdapter.getCount());
    }

    @Override
    protected int layoutId() {
        return R.layout.act_vp_frag_visibility;
    }

    static class MyAdapter extends FragmentStatePagerAdapter{

        private List<Fragment> fragmentList = new ArrayList<>();
        public MyAdapter(FragmentManager fm) {
            super(fm);
            fragmentList.add(new FragmentOne());
            fragmentList.add(new FragmentTwo());
            fragmentList.add(new FragmentVp());
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
