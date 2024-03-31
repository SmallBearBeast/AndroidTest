package com.example.administrator.androidtest.Test.MainTest.WidgetDemo.FullTextViewDemo;

import android.graphics.Color;
import android.view.View;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;
import com.example.administrator.androidtest.Widget.FullTextView.FullTextView;
import com.example.administrator.androidtest.Widget.FullTextView.TextOpt;

public class FullTextViewDemoComponent extends TestActivityComponent {

    private FullTextView ftvFullText;

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
