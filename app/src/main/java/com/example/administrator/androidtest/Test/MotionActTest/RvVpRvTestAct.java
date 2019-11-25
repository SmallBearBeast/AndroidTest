package com.example.administrator.androidtest.Test.MotionActTest;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.administrator.androidtest.R;
import com.example.libbase.Util.CollectionUtil;
import com.example.libbase.Util.DensityUtil;
import com.example.libframework.ActAndFrag.ComponentAct;
import com.example.libframework.Rv.VHAdapter;
import com.example.libframework.Rv.VHBridge;
import com.example.libframework.Rv.VHolder;

import java.util.ArrayList;
import java.util.List;

public class RvVpRvTestAct extends ComponentAct {
    private RecyclerView mRvTest;
    private VHAdapter mAdapter;
    @Override
    protected int layoutId() {
        return R.layout.act_rv_vp_rv;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mRvTest = findViewById(R.id.rv_test);
        mRvTest.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new VHAdapter();
        mAdapter.register(new TextVHBridge(), String.class);
        mAdapter.register(new VpVHBridge(), Integer.class);
        mAdapter.getDataManager().addLast(CollectionUtil.asListNotNull(
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", 1
        ));
        mRvTest.setAdapter(mAdapter);
        mRvTest.setBackgroundColor(Color.GREEN);
    }

    private static class TextVHBridge extends VHBridge {
        @NonNull
        @Override
        protected VHolder onCreateViewHolder(@NonNull View itemView) {
            return new VHolder(itemView);
        }

        @Override
        protected int layoutId() {
            return -1;
        }

        @Override
        protected View itemView() {
            TextView tv = new TextView(mContext);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2Px(60));
            tv.setLayoutParams(lp);
            tv.setText("TextView");
            return tv;
        }
    }

    private static class VpVHBridge extends VHBridge {
        @NonNull
        @Override
        protected VHolder onCreateViewHolder(@NonNull View itemView) {
            return new VHolder(itemView) ;
        }

        @Override
        protected int layoutId() {
            return -1;
        }

        @Override
        protected View itemView() {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_vp_rv_1, null);
            ViewPager vp = view.findViewById(R.id.vp_test);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(lp);
            vp.setAdapter(new VpAdapter(mContext));
            return view;
        }

        private static class VpAdapter extends PagerAdapter {

            private List<RecyclerView> mRvList = new ArrayList<>();

            public VpAdapter(Context context) {
                for (int i = 0; i < 5; i++) {
                    RecyclerView rv = new InRecyclerView(context);
                    ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    rv.setLayoutParams(lp);
                    rv.setLayoutManager(new LinearLayoutManager(context));
                    VHAdapter adapter = new VHAdapter();
                    adapter.register(new TextVHBridge(), String.class);
                    adapter.getDataManager().addLast(CollectionUtil.asListNotNull(
                            "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"
                    ));
                    rv.setAdapter(adapter);
                    mRvList.add(rv);
                }
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(mRvList.get(position));
                return mRvList.get(position);
            }

            @Override
            public int getCount() {
                return mRvList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        }
    }


}
