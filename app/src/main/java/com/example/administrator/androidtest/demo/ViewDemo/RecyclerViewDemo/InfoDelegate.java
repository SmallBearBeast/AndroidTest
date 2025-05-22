package com.example.administrator.androidtest.demo.ViewDemo.RecyclerViewDemo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bear.liblog.SLog;
import com.bear.librv.MultiTypeDelegate;
import com.bear.librv.MultiTypeHolder;
import com.bear.librv.Payload;
import com.bumptech.glide.Glide;
import com.example.administrator.androidtest.R;

public class InfoDelegate extends MultiTypeDelegate<Info, InfoDelegate.InfoHolder> {
    @NonNull
    @Override
    protected InfoHolder onCreateViewHolder(@NonNull View itemView) {
        return new InfoHolder(itemView);
    }

    @Override
    protected int layoutId() {
        return R.layout.item_rv_info;
    }

    @Override
    protected int getSpanSize(RecyclerView rv) {
        return super.getSpanSize(rv);
    }

    public static class InfoHolder extends MultiTypeHolder<Info> {
        private final TextView mTv_1;
        private final TextView mTv_2;
        private final TextView mTv_3;
        private final ImageView mIv_1;
        private final ImageView mIv_2;
        private final ImageView mIv_3;

        public InfoHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTv_1 = itemView.findViewById(R.id.tv_1);
            mTv_2 = itemView.findViewById(R.id.tv_2);
            mTv_3 = itemView.findViewById(R.id.tv_3);
            mIv_1 = itemView.findViewById(R.id.fv_1);
            mIv_2 = itemView.findViewById(R.id.fv_2);
            mIv_3 = itemView.findViewById(R.id.fv_3);
        }

        @Override
        public void bindFull(int pos, Info info) {
            super.bindFull(pos, info);
            Glide.with(mIv_1.getContext()).load(info.url_1).into(mIv_1);
            Glide.with(mIv_2.getContext()).load(info.url_2).into(mIv_2);
            Glide.with(mIv_3.getContext()).load(info.url_3).into(mIv_3);
            mTv_1.setText(info.text_1);
            mTv_2.setText(info.text_2);
            mTv_3.setText(info.text_3);
        }

        @Override
        public void bindPartial(Info info, @NonNull Payload payload) {
            switch (payload.mType) {
                case RecyclerViewDemoActivity.PARTIAL_UPDATE_TYPE:
                    Glide.with(mIv_1.getContext()).load(info.url_1).into(mIv_1);
                    Glide.with(mIv_2.getContext()).load(info.url_2).into(mIv_2);
                    Glide.with(mIv_3.getContext()).load(info.url_3).into(mIv_3);
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            SLog.d(TAG, "onClick: mPos = " + getItemPosition() + " getAdapterPosition() = " + getAdapterPosition() + " getLayoutPosition() = " + getLayoutPosition());
        }
    }
}
