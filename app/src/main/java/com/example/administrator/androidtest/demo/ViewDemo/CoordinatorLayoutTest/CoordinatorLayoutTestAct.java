package com.example.administrator.androidtest.demo.ViewDemo.CoordinatorLayoutTest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.TestListFragment;
import com.example.libcommon.util.DensityUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

/**
 * Add CollapsingToolbarLayout and Toolbar in AppBarLayout, the Toolbar will have no effect.
 */
public class CoordinatorLayoutTestAct extends ComponentAct implements AppBarLayout.OnOffsetChangedListener {
    private Toolbar mCommonToolbar;
    private Toolbar mCollapsingToolbar;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initViewPagerAndTabLayout();
    }

    @Override
    protected int layoutId() {
        return R.layout.act_coordinatorlayout_test;
    }

    private void initToolbar() {
        mCommonToolbar = findViewById(R.id.commonToolbar);
        mCollapsingToolbar = findViewById(R.id.collapsingToolbar);
        mAppBarLayout = findViewById(R.id.appBarLayout);
        mCollapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
    }

    private void initViewPagerAndTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return TestListFragment.get(TAG + "-" + position);
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return "Tab-" + position;
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    private void removeCollapsingToolbarLayout() {
        mAppBarLayout.removeView(mCollapsingToolbarLayout);
        if (mCommonToolbar.getParent() == null) {
            mAppBarLayout.addView(mCommonToolbar, 0);
        }
    }

    private void removeCommonToolbar() {
        mAppBarLayout.removeView(mCommonToolbar);
        if (mCollapsingToolbarLayout.getParent() == null) {
            mAppBarLayout.addView(mCollapsingToolbarLayout, 0);
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        removeOnOffsetChangedListener();
        switch (view.getId()) {
            case R.id.setEnterAlwaysFlagButton:
                removeCollapsingToolbarLayout();
                setScrollFlag(mCommonToolbar, AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
                break;

            case R.id.setEnterAlwaysCollapsedFlagButton:
                removeCollapsingToolbarLayout();
                setScrollFlag(mCommonToolbar, AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED);
                break;

            case R.id.setExitUntilCollapsedFlagButton:
                removeCollapsingToolbarLayout();
                setScrollFlag(mCommonToolbar, AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
                break;

            case R.id.setExitUntilCollapsedAndEnterAlwaysFlagButton:
                removeCollapsingToolbarLayout();
                setScrollFlag(mCommonToolbar, AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
                break;

            case R.id.setSnapFlagButton:
                removeCollapsingToolbarLayout();
                setScrollFlag(mCommonToolbar, AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
                break;

            case R.id.setPinCollapseModeButton:
                removeCommonToolbar();
                setCollapsingMode(mCollapsingToolbar, CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN);
                break;

            case R.id.setParallaxCollapseModeButton:
                removeCommonToolbar();
                setCollapsingMode(mCollapsingToolbar, CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_OFF);
                break;

            case R.id.setCollapseToolbarAlphaButton:
                removeCommonToolbar();
                setCollapsingMode(mCollapsingToolbar, CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN);
                addOnOffsetChangedListener();
                break;

            case R.id.setCollapseToolbarLayoutAttrButton:
                removeCommonToolbar();
                setCollapsingToolbarLayoutAttr();
                break;
            default:
                break;
        }
    }

    private void setScrollFlag(Toolbar toolbar, @AppBarLayout.LayoutParams.ScrollFlags int flag) {
        if (toolbar.getLayoutParams() instanceof AppBarLayout.LayoutParams) {
            AppBarLayout.LayoutParams lp = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
            lp.setScrollFlags(flag | AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);
            toolbar.setLayoutParams(lp);
        }
    }

    private void setCollapsingMode(Toolbar toolbar, int collapseMode) {
        if (toolbar.getLayoutParams() instanceof CollapsingToolbarLayout.LayoutParams) {
            CollapsingToolbarLayout.LayoutParams lp = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
            lp.setCollapseMode(collapseMode);
            toolbar.setLayoutParams(lp);
        }
    }

    private void addOnOffsetChangedListener() {
        mCollapsingToolbar.setAlpha(0);
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    private void removeOnOffsetChangedListener() {
        mCollapsingToolbar.setAlpha(1F);
        mAppBarLayout.removeOnOffsetChangedListener(this);
    }

    private void setCollapsingToolbarLayoutAttr() {
        mCollapsingToolbarLayout.setTitle("Hello World");
        mCollapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(this, R.color.cl_blue_5));
        mCollapsingToolbarLayout.setScrimAnimationDuration(3000);
        mCollapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);
        mCollapsingToolbarLayout.setExpandedTitleGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        mCollapsingToolbarLayout.setExpandedTitleMarginBottom(0);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        final int height = DensityUtil.dp2Px(300);
        int halfHeight = height / 2;
        int diff = -verticalOffset - halfHeight;
        Log.d(TAG, "onOffsetChanged: verticalOffset  = " + verticalOffset + ", diff = " + diff + ", testHeight = " + height);
        if (diff >= 0) {
            mCollapsingToolbar.setAlpha(diff * 1.0f / halfHeight);
        } else {
            mCollapsingToolbar.setAlpha(0F);
        }
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, CoordinatorLayoutTestAct.class));
    }
}
