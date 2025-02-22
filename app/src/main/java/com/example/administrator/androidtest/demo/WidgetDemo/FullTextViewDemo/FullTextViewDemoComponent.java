package com.example.administrator.androidtest.demo.WidgetDemo.FullTextViewDemo;

import android.graphics.Color;
import android.view.View;

import androidx.lifecycle.Lifecycle;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.TestActivityComponent;
import com.example.administrator.androidtest.widget.FullTextView.FullTextView;
import com.example.administrator.androidtest.widget.FullTextView.TextOpt;

public class FullTextViewDemoComponent extends TestActivityComponent {

    private FullTextView ftvFullText;

    public FullTextViewDemoComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ftvFullText = findViewById(R.id.fullTextView);
        setOnClickListener(this, R.id.fullTextView);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fullTextButton) {
            changeFullTextView();
        }
    }

    private void changeFullTextView() {
        TextOpt bgOpt = TextOpt.bgOpt(0, 5, Color.RED);
        TextOpt fgOpt = TextOpt.fgOpt(5, ftvFullText.length(), Color.BLUE);
        ftvFullText.bg(bgOpt).fg(fgOpt).done();
    }
}
