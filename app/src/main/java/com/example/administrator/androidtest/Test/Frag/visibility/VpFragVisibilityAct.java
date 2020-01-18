package com.example.administrator.androidtest.Test.Frag.visibility;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.androidtest.R;
import com.example.libframework.CoreUI.ComponentAct;
import com.example.libframework.Page.IPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Viewpager + Fragment在不嵌套的情况下切换setUserVisibleHint()逻辑是没有问题的
 */
public class VpFragVisibilityAct extends ComponentAct {

    private ViewPager vpContainer;
    private MyAdapter myAdapter;
    private TextView tvFragVisibility;
    private TextView mTvPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vpContainer = findViewById(R.id.vp_container);
        tvFragVisibility = findViewById(R.id.tv_frag_visibility);
        mTvPage = findViewById(R.id.tv_page);
        myAdapter = new MyAdapter(getSupportFragmentManager());
        vpContainer.setAdapter(myAdapter);
        vpContainer.setCurrentItem(1);
        vpContainer.setOffscreenPageLimit(myAdapter.getCount());
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_checkout_1:
                vpContainer.setCurrentItem(0, false);
                break;

            case R.id.bt_checkout_2:
                vpContainer.setCurrentItem(1, false);
                break;

            case R.id.bt_checkout_3:
                vpContainer.setCurrentItem(2, false);
                break;
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.act_vp_frag_visibility;
    }

//    @Override
//    public void onVisibilityChanged() {
//        Map<String, Boolean> map = App.FragVisibiableMap;
//        StringBuilder builder = new StringBuilder();
//        if (map.get("FragmentOneVp") != null)
//            builder.append("FragmentOneVp = ").append(map.get("FragmentOneVp")).append("\n");
//        if (map.get("FragmentOne") != null)
//            builder.append("FragmentOne = ").append(map.get("FragmentOne")).append("\n");
//        if (map.get("FragmentTwo") != null)
//            builder.append("FragmentTwo = ").append(map.get("FragmentTwo")).append("\n");
//        if (map.get("FragmentThree") != null)
//            builder.append("FragmentThree = ").append(map.get("FragmentThree")).append("\n");
//
//
//        if (map.get("FragmentSeven") != null)
//            builder.append("FragmentSeven = ").append(map.get("FragmentSeven")).append("\n");
//
//        if (map.get("FragmentThreeVp") != null)
//            builder.append("FragmentThreeVp = ").append(map.get("FragmentThreeVp")).append("\n");
//        if (map.get("FragmentFour") != null)
//            builder.append("FragmentFour = ").append(map.get("FragmentFour")).append("\n");
//        if (map.get("FragmentFive") != null)
//            builder.append("FragmentFive = ").append(map.get("FragmentFive")).append("\n");
//        if (map.get("FragmentSix") != null)
//            builder.append("FragmentSix = ").append(map.get("FragmentSix"));
//
//        tvFragVisibility.setText(builder.toString());
//    }

    @Override
    public int pageId() {
        return IPage.VpFragVisibilityAct;
    }


    static class MyAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList = new ArrayList<>();

        public MyAdapter(FragmentManager fm) {
            super(fm);
            fragmentList.add(new FragmentOneVp());
            fragmentList.add(new FragmentSeven());
            fragmentList.add(new FragmentThreeVp());
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
