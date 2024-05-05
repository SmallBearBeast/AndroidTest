package com.example.administrator.androidtest.Test.MainTest.ViewDemo.RecyclerViewDemo;

import androidx.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bear.librv.MultiTypeDelegate;
import com.bear.librv.MultiTypeHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.administrator.androidtest.R;

public class ImageDelegate extends MultiTypeDelegate<Image, ImageDelegate.ImageHolder> {
    @NonNull
    @Override
    protected ImageHolder onCreateViewHolder(@NonNull View itemView) {
        return new ImageHolder(itemView);
    }

    public static class ImageHolder extends MultiTypeHolder<Image> {
        private final TextView textView;
        private final ImageView imageView;

        public ImageHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        @Override
        public void bindFull(int pos, Image image) {
            super.bindFull(pos, image);
            textView.setText("Image-" + image.id);
//
//            GlideUrl glideUrl = new GlideUrl(image.url, new LazyHeaders.Builder()
//                    .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36 Edg/124.0.0.0")
//                    .build());
//            Glide.with(imageView.getContext()).load(glideUrl).into(imageView);
            Glide.with(imageView.getContext()).load(image.url).into(imageView);
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
