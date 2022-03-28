package com.example.administrator.androidtest.Test.MainTest.BottomSheetTest;

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

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestListFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class VpAndRvBottomSheetFragment extends BottomSheetDialogFragment {
    private static final String TAG = "VpAndRvBottomSheetFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initAndGetView();
    }

    private View initAndGetView() {
        ViewPager viewPager = new ViewPager(requireContext());
        viewPager.setId(R.id.viewpager);
        viewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return TestListFragment.get("Test-" + position);
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
        viewPager.setAdapter(fragmentPagerAdapter);
        return viewPager;
    }

    public static void show(@NonNull FragmentManager manager) {
        new VpAndRvBottomSheetFragment().show(manager, TAG);
    }
}
