package com.example.administrator.androidtest.demo.ComponentDemo.Component;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;

import com.bear.libcommon.util.ResourceUtil;
import com.bear.libcommon.util.ToastUtil;
import com.bear.libcomponent.component.ActivityComponent;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.databinding.ActComponentTestBinding;
import com.example.administrator.androidtest.demo.ComponentDemo.ComponentDemoFragment;
import com.example.administrator.androidtest.demo.ComponentDemo.ComponentSpecialDemoFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DemoActComponent extends ActivityComponent<ActComponentTestBinding> {
    public DemoActComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initViewPagerAndTabLayout();
    }

    private void initViewPagerAndTabLayout() {
        int[] colors = new int[]{
                ResourceUtil.getColor(R.color.colorFF5722),
                ResourceUtil.getColor(R.color.color03A9F4),
                ResourceUtil.getColor(R.color.color9C27B0),
                ResourceUtil.getColor(R.color.color8BC34A),
                ResourceUtil.getColor(R.color.colorFF9800),
        };

        ArrayList<String> buttonTextList = new ArrayList<>();
        buttonTextList.add("Call DemoActComponent");
        for (int i = 0; i < colors.length; i++) {
            buttonTextList.add("Call DemoFragComponent " + i);
        }
        buttonTextList.add("Call ShowMainTv");
        buttonTextList.add("Call ShowMinorTv");

        TabLayout tabLayout = getBinding().tabLayout;
        ViewPager viewPager = getBinding().viewpager;
        viewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                ArrayList<String> btnTextList = new ArrayList<>(buttonTextList);
                btnTextList.remove("Call DemoFragComponent " + position);
                if (position == colors.length - 1) {
                    String text = "ComponentSpecialDemoFrag" + "-" + position;
                    return ComponentSpecialDemoFragment.get(position, text, text, colors[position], colors[position - 1], btnTextList);
                }
                return ComponentDemoFragment.get(position, "ComponentDemoFrag-" + position, null, colors[position], -1, btnTextList);
            }

            @Override
            public int getCount() {
                return colors.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "Tab-" + position;
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    public void showToast() {
        ToastUtil.showToast("I am " + TAG);
    }
}
