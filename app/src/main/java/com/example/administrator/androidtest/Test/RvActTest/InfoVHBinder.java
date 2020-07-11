package com.example.administrator.androidtest.Test.RvActTest;

import android.net.Uri;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.bear.librv.Notify;
import com.bear.librv.VHBridge;
import com.bear.librv.VHolder;
import com.example.administrator.androidtest.R;
import com.example.libfresco.FrescoView;
import com.example.liblog.SLog;

public class InfoVHBinder extends VHBridge<InfoVHBinder.InfoVHolder> {
    @NonNull
    @Override
    protected InfoVHolder onCreateViewHolder(@NonNull View itemView) {
        return new InfoVHolder(itemView);
    }

    @Override
    protected int layoutId() {
        return R.layout.item_rv_info;
    }

    class InfoVHolder extends VHolder<Info> {
        private TextView mTv_1;
        private TextView mTv_2;
        private TextView mTv_3;
        private FrescoView mFv_1;
        private FrescoView mFv_2;
        private FrescoView mFv_3;

        public InfoVHolder(View itemView) {
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
        public void bindPartial(Info info, @NonNull Notify notify) {
            switch (notify.mType){
                case 111:
                    mFv_1.setImageUri(Uri.parse(info.mUrl_1));
                    mFv_2.setImageUri(Uri.parse(info.mUrl_2));
                    mFv_3.setImageUri(Uri.parse(info.mUrl_3));
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            SLog.d(TAG, "onClick: mPos = " + getPos() + " getAdapterPosition() = " + getAdapterPosition() + " getLayoutPosition() = "+ getLayoutPosition());
        }
    }
}
