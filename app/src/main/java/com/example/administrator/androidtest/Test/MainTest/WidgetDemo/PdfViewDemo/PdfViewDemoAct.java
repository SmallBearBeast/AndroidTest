package com.example.administrator.androidtest.Test.MainTest.WidgetDemo.PdfViewDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Widget.pdfview.PdfView;

import java.io.File;

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
        pdfView.setDividerHeight(100);
        findViewById(R.id.loadPdfButton_1).setOnClickListener(this);
        findViewById(R.id.loadPdfButton_2).setOnClickListener(this);
        findViewById(R.id.prePageButton).setOnClickListener(this);
        findViewById(R.id.nextPageButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id. loadPdfButton_1:
                loadPdf("PdfTest_1.pdf");
                break;
            case R.id. loadPdfButton_2:
                loadPdf("git.pdf");
                break;
            case R.id. prePageButton:
                prePage();
                break;
            case R.id. nextPageButton:
                nextPage();
                break;
        }
    }

    private void loadPdf(String pdfName) {
        File dir = new File(getFilesDir(), "doc");
        if (!dir.exists() && !dir.mkdirs()) {
            return;
        }
        File file = new File(dir, pdfName);
        if (file.exists()) {
            pdfView.load(file.getAbsolutePath());
        }
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
