package com.example.administrator.androidtest.other.WidgetTest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentActivity;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.databinding.ActShapeImageViewBinding;
import com.example.administrator.androidtest.widget.ShapeImageView;

public class ShapeImageViewActivity extends ComponentActivity<ActShapeImageViewBinding> {
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
    protected ActShapeImageViewBinding inflateViewBinding(LayoutInflater inflater) {
        return ActShapeImageViewBinding.inflate(inflater);
    }
}
