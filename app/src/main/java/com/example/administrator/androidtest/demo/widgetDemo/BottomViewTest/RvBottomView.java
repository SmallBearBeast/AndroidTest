package com.example.administrator.androidtest.demo.widgetDemo.BottomViewTest;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bear.librv.MultiTypeAdapter;
import com.bear.librv.MultiTypeDelegate;
import com.bear.librv.MultiTypeHolder;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.widget.BottomView;
import com.example.libcommon.Util.CollectionUtil;
import com.example.libcommon.Util.DensityUtil;

public class RvBottomView extends BottomView {
    public RvBottomView(Activity activity) {
        super(activity);
        contentView(R.layout.view_rv_bottom_test);
        RecyclerView recyclerView = findViewById(R.id.rv_test_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        MultiTypeAdapter adapter = new MultiTypeAdapter(((AppCompatActivity)activity).getLifecycle());
        adapter.register(String.class, new RvBottomMultiTypeDelegate());
        adapter.getChanger().addLast(CollectionUtil.asListNotNull(
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"
        ));
        recyclerView.setAdapter(adapter);
    }


    private static class RvBottomMultiTypeDelegate extends MultiTypeDelegate<String, MultiTypeHolder<String>> implements View.OnClickListener {

        @NonNull
        @Override
        protected MultiTypeHolder<String> onCreateViewHolder(@NonNull View itemView) {
            return new MultiTypeHolder<>(itemView);
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
//            tv.setOnClickListener(this);
            return tv;
        }

        @Override
        public void onClick(View v) {

        }
    }
}
