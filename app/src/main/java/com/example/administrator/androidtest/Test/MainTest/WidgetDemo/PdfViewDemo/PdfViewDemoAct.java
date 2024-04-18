package com.example.administrator.androidtest.Test.MainTest.WidgetDemo.PdfViewDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Widget.pdfview.PdfView;

public class PdfViewDemoAct extends ComponentAct implements View.OnClickListener {
    private PdfView pdfView;
    @Override
    protected int layoutId() {
        return R.layout.act_pdf_view_test;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pdfView = findViewById(R.id.pdfView);
        findViewById(R.id.loadPdfButton).setOnClickListener(this);
        findViewById(R.id.prePageButton).setOnClickListener(this);
        findViewById(R.id.nextPageButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id. loadPdfButton:
                loadPdf();
                break;
            case R.id. prePageButton:
                prePage();
                break;
            case R.id. nextPageButton:
                nextPage();
                break;
        }
    }

    private void loadPdf() {

    }

    private void prePage() {

    }

    private void nextPage() {

    }

    public static void go(Context context) {
        Intent intent = new Intent(context, PdfViewDemoAct.class);
        context.startActivity(intent);
    }
}
