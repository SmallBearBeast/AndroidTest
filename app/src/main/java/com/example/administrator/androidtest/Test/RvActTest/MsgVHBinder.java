package com.example.administrator.androidtest.Test.RvActTest;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.bear.librv.VHBridge;
import com.bear.librv.VHolder;
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

    class MsgVHolder extends VHolder<Msg> {
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
