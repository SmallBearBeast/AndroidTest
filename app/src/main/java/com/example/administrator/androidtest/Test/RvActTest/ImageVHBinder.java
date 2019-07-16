package com.example.administrator.androidtest.Test.RvActTest;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.example.administrator.androidtest.Base.Adapter.VHBridge;
import com.example.administrator.androidtest.Base.Adapter.VHolder;
import com.example.administrator.androidtest.Fresco.FrescoView;
import com.example.administrator.androidtest.R;

public class ImageVHBinder extends VHBridge<ImageVHBinder.ImageVHolder> {
    @NonNull
    @Override
    protected ImageVHolder onCreateViewHolder(@NonNull View itemView) {
        return new ImageVHolder(itemView);
    }

    class ImageVHolder extends VHolder<Image>{
        private TextView mTv_1;
        private FrescoView mFv_1;

        public ImageVHolder(View itemView) {
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
            Log.d(TAG, "onStop: mPos = " + mPos);
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.item_rv_image;
    }
}
