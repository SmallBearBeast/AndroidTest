package com.example.administrator.androidtest.Test.MainTest.LibraryDemo.GlideDemo;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;

public class GlideDemoComponent extends TestActivityComponent {
    @Override
    protected void onCreate() {
        setOnClickListener(this, R.id.glideDemoButton);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.glideDemoButton:
                glideLoadBitmapTest();
                break;

            default:
                break;
        }
    }

    private void glideLoadBitmapTest() {
        Glide.with(getContext()).asBitmap().load(R.drawable.girl).addListener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                Log.d(TAG, "onLoadFailed() called with: e = [" + e + "], model = [" + model + "], target = [" + target + "], isFirstResource = [" + isFirstResource + "]");
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                Log.d(TAG, "onResourceReady() called with: resource = [" + resource + "], model = [" + model + "], target = [" + target + "], dataSource = [" + dataSource + "], isFirstResource = [" + isFirstResource + "]");
                return false;
            }
        }).into(new BaseTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Log.d(TAG, "onResourceReady() called with: resource = [" + resource + "], transition = [" + transition + "]");

            }

            @Override
            public void getSize(@NonNull SizeReadyCallback cb) {
                Log.d(TAG, "getSize() called with: cb = [" + cb + "]");

            }

            @Override
            public void removeCallback(@NonNull SizeReadyCallback cb) {
                Log.d(TAG, "removeCallback() called with: cb = [" + cb + "]");

            }
        });
    }
}
