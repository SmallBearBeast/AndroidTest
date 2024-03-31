package com.example.administrator.androidtest.Test.MainTest.FlutterTest;

import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;

//import io.flutter.embedding.android.FlutterActivity;

public class FlutterTestComponent extends TestActivityComponent {

    @Override
    protected void onCreate() {
        super.onCreate();
        setOnClickListener(this, R.id.flutterTestButton);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.flutterTestButton:
//                getContext().startActivity(
//                        FlutterActivity.withCachedEngine("my_engine_id").build(getContext())
//                );
                break;
        }
    }
}
