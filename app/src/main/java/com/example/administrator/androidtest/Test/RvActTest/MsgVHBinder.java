package com.example.administrator.androidtest.Test.RvActTest;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;
import com.example.administrator.androidtest.Base.Adapter.VHBridge;
import com.example.administrator.androidtest.Base.Adapter.VHolder;
import com.example.administrator.androidtest.R;

public class MsgVHBinder extends VHBridge<MsgVHBinder.MsgVHolder> {
    @NonNull
    @Override
    protected MsgVHolder onCreateViewHolder(@NonNull View itemView) {
        return new MsgVHolder(itemView);
    }

    @Override
    protected int layoutId() {
        return R.layout.item_rv_msg;
    }

    class MsgVHolder extends VHolder<Msg>{
        private TextView mTvText;
        public MsgVHolder(View itemView) {
            super(itemView);
            mTvText = findViewById(R.id.tv_text);
            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
            lp.setFullSpan(true);
            itemView.setLayoutParams(lp);
        }

        @Override
        public void bindFull(int pos, Msg msg) {
            super.bindFull(pos, msg);
            mTvText.setText(msg.mText);
        }
    }
}
