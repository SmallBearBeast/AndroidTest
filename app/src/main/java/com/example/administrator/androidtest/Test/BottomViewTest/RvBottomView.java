package com.example.administrator.androidtest.Test.BottomViewTest;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.administrator.androidtest.R;
import com.example.libbase.Com.BottomView;
import com.example.libbase.Util.CollectionUtil;
import com.example.libbase.Util.DensityUtil;
import com.example.libframework.Rv.DataManager;
import com.example.libframework.Rv.VHAdapter;
import com.example.libframework.Rv.VHBridge;
import com.example.libframework.Rv.VHolder;

import java.util.ArrayList;
import java.util.List;

public class RvBottomView extends BottomView {
    protected RvBottomView(Activity activity) {
        super(activity);
        contentView(R.layout.view_rv_bottom_test);
        RecyclerView recyclerView = findViewById(R.id.rv_test_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        VHAdapter vhAdapter = new VHAdapter(((AppCompatActivity)activity).getLifecycle());
        vhAdapter.register(new RvBottomVHBridge(), String.class);
        vhAdapter.getDataManager().addLast(CollectionUtil.asListNotNull(
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"
        ));
        recyclerView.setAdapter(vhAdapter);
    }


    private static class RvBottomVHBridge extends VHBridge implements View.OnClickListener {

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
            TextView tv = new TextView(getContext());
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2Px(60));
            tv.setLayoutParams(lp);
            tv.setText("TextView");
//            tv.clickListener(this);
            return tv;
        }

        @Override
        public void onClick(View v) {

        }
    }
}
