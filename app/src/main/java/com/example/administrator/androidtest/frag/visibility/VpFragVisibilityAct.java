package com.example.administrator.androidtest.frag.visibility;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.androidtest.App;
import com.example.administrator.androidtest.Base.ActAndFrag.BaseAct;
import com.example.administrator.androidtest.Base.ActAndFrag.BaseFrag;
import com.example.administrator.androidtest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
    Viewpager + Fragment在不嵌套的情况下切换setUserVisibleHint()逻辑是没有问题的
 */
public class VpFragVisibilityAct extends BaseAct implements BaseFrag.FragVisiableListener {

    private ViewPager vpContainer;
    private MyAdapter myAdapter;
    private TextView tvFragVisibility;

    @Override
    protected void init(Bundle savedInstanceState) {
        App.fragVisiableListener = this;
        vpContainer = findViewById(R.id.vp_container);
        tvFragVisibility = findViewById(R.id.tv_frag_visibility);
        myAdapter = new MyAdapter(getSupportFragmentManager());
        vpContainer.setAdapter(myAdapter);
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

    @Override
    public void onVisibilityChanged() {
        Map<String, Boolean> map = App.FragVisibiableMap;
        StringBuilder builder = new StringBuilder();
        if (map.get("FragmentOneVp") != null)
            builder.append("FragmentOneVp = ").append(map.get("FragmentOneVp")).append("\n");
        if (map.get("FragmentOne") != null)
            builder.append("FragmentOne = ").append(map.get("FragmentOne")).append("\n");
        if (map.get("FragmentTwo") != null)
            builder.append("FragmentTwo = ").append(map.get("FragmentTwo")).append("\n");
        if (map.get("FragmentThree") != null)
            builder.append("FragmentThree = ").append(map.get("FragmentThree")).append("\n");


        if (map.get("FragmentSeven") != null)
            builder.append("FragmentSeven = ").append(map.get("FragmentSeven")).append("\n");

        if (map.get("FragmentThreeVp") != null)
            builder.append("FragmentThreeVp = ").append(map.get("FragmentThreeVp")).append("\n");
        if (map.get("FragmentFour") != null)
            builder.append("FragmentFour = ").append(map.get("FragmentFour")).append("\n");
        if (map.get("FragmentFive") != null)
            builder.append("FragmentFive = ").append(map.get("FragmentFive")).append("\n");
        if (map.get("FragmentSix") != null)
            builder.append("FragmentSix = ").append(map.get("FragmentSix"));

        tvFragVisibility.setText(builder.toString());
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
