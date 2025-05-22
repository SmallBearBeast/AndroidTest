package com.example.administrator.androidtest.demo.ViewDemo.RecyclerViewDemo;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bear.librv.MultiTypeDelegate;
import com.bear.librv.MultiTypeHolder;
import com.example.administrator.androidtest.R;

public class MsgDelegate extends MultiTypeDelegate<Msg, MsgDelegate.MsgHolder> {
    @NonNull
    @Override
    protected MsgHolder onCreateViewHolder(@NonNull View itemView) {
        return new MsgHolder(itemView);
    }

    @Override
    protected int layoutId() {
        return R.layout.item_rv_msg;
    }

    public static class MsgHolder extends MultiTypeHolder<Msg> {
        private final TextView mTvText;
        public MsgHolder(View itemView) {
            super(itemView);
            mTvText = findViewById(R.id.tv_text);
        }

        @Override
        public void bindFull(int pos, Msg msg) {
            super.bindFull(pos, msg);
            mTvText.setText(msg.mText);
            mTvText.setSelected(true);
        }
    }
}
