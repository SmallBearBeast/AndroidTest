package com.example.administrator.androidtest.Test.RvActTest;

import android.net.Uri;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.bear.librv.Payload;
import com.bear.librv.MultiTypeDelegate;
import com.bear.librv.MultiTypeHolder;
import com.example.administrator.androidtest.R;
import com.example.libfresco.FrescoView;
import com.example.liblog.SLog;

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

    public static class InfoHolder extends MultiTypeHolder<Info> {
        private final TextView mTv_1;
        private final TextView mTv_2;
        private final TextView mTv_3;
        private final FrescoView mFv_1;
        private final FrescoView mFv_2;
        private final FrescoView mFv_3;

        public InfoHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTv_1 = itemView.findViewById(R.id.tv_1);
            mTv_2 = itemView.findViewById(R.id.tv_2);
            mTv_3 = itemView.findViewById(R.id.tv_3);
            mFv_1 = itemView.findViewById(R.id.fv_1);
            mFv_2 = itemView.findViewById(R.id.fv_2);
            mFv_3 = itemView.findViewById(R.id.fv_3);
        }

        @Override
        public void bindFull(int pos, Info info) {
            super.bindFull(pos, info);
            mFv_1.setImageUri(Uri.parse(info.mUrl_1));
            mFv_2.setImageUri(Uri.parse(info.mUrl_2));
            mFv_3.setImageUri(Uri.parse(info.mUrl_3));
            mTv_1.setText(info.mText_1);
            mTv_2.setText(info.mText_2);
            mTv_3.setText(info.mText_3);
        }

        @Override
        public void bindPartial(Info info, @NonNull Payload payload) {
            switch (payload.mType){
                case 111:
                    mFv_1.setImageUri(Uri.parse(info.mUrl_1));
                    mFv_2.setImageUri(Uri.parse(info.mUrl_2));
                    mFv_3.setImageUri(Uri.parse(info.mUrl_3));
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            SLog.d(TAG, "onClick: mPos = " + getItemPosition() + " getAdapterPosition() = " + getAdapterPosition() + " getLayoutPosition() = "+ getLayoutPosition());
        }
    }
}
