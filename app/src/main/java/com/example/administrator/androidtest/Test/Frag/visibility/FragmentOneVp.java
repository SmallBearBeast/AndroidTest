package com.example.administrator.androidtest.Test.Frag.visibility;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.administrator.androidtest.R;
import com.example.libframework.CoreUI.ComponentFrag;
import com.example.libframework.Page.IPage;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vpContainer = findViewById(R.id.vp_container);
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
