package com.example.administrator.androidtest.Test.ViewTest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bear.libcomponent.ComponentAct;
import com.example.administrator.androidtest.R;

public class ViewTestAct extends ComponentAct {
    @Override
    protected int layoutId() {
        return R.layout.act_view_test;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TextView tvTest_1 = findViewById(R.id.tv_test_1);
        tvTest_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = tvTest_1.getVisibility();
                int newVisibility = visibility == View.VISIBLE ? View.INVISIBLE
                        : visibility == View.INVISIBLE ? View.GONE
                        : visibility == View.GONE ? View.VISIBLE : View.GONE;
                tvTest_1.setVisibility(newVisibility);
            }
        });

        ImageView ivImage = findViewById(R.id.iv_image);
        ivImage.setImageBitmap(getTurnOverBitmap(R.drawable.girl));
    }

    public Bitmap getTurnOverBitmap(int drawableId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableId);
        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1, 0.5f, 0.5f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }
}

