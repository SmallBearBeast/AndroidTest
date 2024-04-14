package com.example.administrator.androidtest.Test.RvActTest;

import android.net.Uri;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bear.librv.MultiTypeDelegate;
import com.bear.librv.MultiTypeHolder;
import com.example.administrator.androidtest.R;
import com.example.libfresco.FrescoView;

public class ImageDelegate extends MultiTypeDelegate<Image, ImageDelegate.ImageHolder> {
    @NonNull
    @Override
    protected ImageHolder onCreateViewHolder(@NonNull View itemView) {
        return new ImageHolder(itemView);
    }

    public static class ImageHolder extends MultiTypeHolder<Image> {
        private final TextView mTv_1;
        private final FrescoView mFv_1;

        public ImageHolder(View itemView) {
            super(itemView);
            mTv_1 = itemView.findViewById(R.id.tv_1);
            mFv_1 = itemView.findViewById(R.id.fv_1);
        }

        @Override
        public void bindFull(int pos, Image image) {
            super.bindFull(pos, image);
            mTv_1.setText("NiuBi " + image.mId);
            mFv_1.setImageUri(Uri.parse(image.mUrl_1));
        }

        @Override
        public void onStop() {
            Log.d(TAG, "onStop: mPos = " + getItemPosition());
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.item_rv_image;
    }
}
