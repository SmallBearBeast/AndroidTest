package com.example.administrator.androidtest.Test.MediaTest;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.libbase.Util.DensityUtil;
import com.example.libframework.Rv.VHBridge;
import com.example.libframework.Rv.VHolder;

public class TextBridge extends VHBridge {

    @Override
    protected int getSpanSize(RecyclerView rv) {
        if (rv != null && rv.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) rv.getLayoutManager();
            return gridLayoutManager.getSpanCount();
        }
        return super.getSpanSize(rv);
    }

    @NonNull
    @Override
    protected VHolder onCreateViewHolder(@NonNull View itemView) {
        return new TextVHolder(itemView);
    }

    @Override
    protected int layoutId() {
        return 0;
    }

    @Override
    protected View itemView() {
        TextView tv = new TextView(getContext());
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2Px(100));
        tv.setLayoutParams(lp);
        tv.setGravity(Gravity.CENTER);
        tv.setText("Hello World");
        tv.setTextColor(Color.WHITE);
        tv.setBackgroundColor(Color.BLACK);
        return tv;
    }

    class TextVHolder extends VHolder{
        public TextVHolder(View itemView) {
            super(itemView);
        }
    }
}
