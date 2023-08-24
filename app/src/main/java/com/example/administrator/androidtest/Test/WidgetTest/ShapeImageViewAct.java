package com.example.administrator.androidtest.Test.WidgetTest;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Widget.ShapeImageView;

public class ShapeImageViewAct extends ComponentAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ShapeImageView siv_2 = findViewById(R.id.siv_2);
        siv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siv_2.setImageResource(R.drawable.girl);
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.act_shape_image_view;
    }

}
