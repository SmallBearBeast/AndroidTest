package com.example.administrator.androidtest.other.Frag.visibility;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bear.libcomponent.component.ComponentFragment;
import com.bear.liblog.SLog;
import com.bear.libother.page.IPage;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.databinding.FragVpBinding;

import java.util.ArrayList;
import java.util.List;

public class FragmentThreeVp extends ComponentFragment<FragVpBinding> {

    private ViewPager vpContainer;
    private MyAdapter myAdapter;

    @Override
    public int layoutId() {
        return R.layout.frag_vp;
    }

    @Override
    protected FragVpBinding inflateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragVpBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        vpContainer = view.findViewById(R.id.vp_container);
        myAdapter = new MyAdapter(getChildFragmentManager());
        vpContainer.setAdapter(myAdapter);
        vpContainer.setOffscreenPageLimit(myAdapter.getCount());
        vpContainer.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                SLog.d(TAG, "onPageSelected: position = " + position);
            }
        });
    }

    public int pageId() {
        return IPage.FragmentThreeVp;
    }


    static class MyAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList = new ArrayList<>();
        public MyAdapter(FragmentManager fm) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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

    @Override
    protected void onFirstVisible() {
        super.onFirstVisible();
        SLog.d(TAG, "onFirstVisible");
    }
}
