package com.example.administrator.androidtest.ActLifeCallbacks;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.administrator.androidtest.BaseAct;
import com.example.administrator.androidtest.R;

import java.util.ArrayList;
import java.util.List;

public class AlcOneAct extends BaseAct {

    private ViewPager vpContainer;
    private MyAdapter myAdapter;

    @Override
    protected void init() {
        vpContainer = findViewById(R.id.vp_container);
        myAdapter = new MyAdapter(getSupportFragmentManager());
        vpContainer.setAdapter(myAdapter);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_alc_1;
    }

    static class MyAdapter extends FragmentPagerAdapter {

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
