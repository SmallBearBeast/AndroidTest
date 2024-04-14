package com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo.Detail;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bear.librv.MultiTypeAdapter;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo.TiktokBean;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;

public class AdapterComponent extends TestActivityComponent {

    private ViewPager2 tiktokDetailViewPager;

    public AdapterComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        tiktokDetailViewPager = findViewById(R.id.tiktokDetailViewPager);
        tiktokDetailViewPager.setOffscreenPageLimit(1);
        MultiTypeAdapter adapter = new MultiTypeAdapter(getActivity().getLifecycle());
        adapter.register(TiktokBean.class, new VideoDetailDelegate(this));
        tiktokDetailViewPager.setAdapter(adapter);
    }

    private static class Sdasd extends FragmentStateAdapter {


        public Sdasd(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return null;
        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
