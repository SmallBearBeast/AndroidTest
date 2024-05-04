package com.example.administrator.androidtest.Test.MainTest.WidgetDemo.PdfViewDemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.bear.libcomponent.component.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Widget.pdfview.PdfRenderView;

import java.io.File;

public class PdfViewDemoAct extends ComponentAct implements View.OnClickListener {
    private PdfRenderView pdfRenderView;
    @Override
    protected int layoutId() {
        return R.layout.act_pdf_view_test;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pdfRenderView = findViewById(R.id.pdfView);
        pdfRenderView.setDividerHeight(100);
        findViewById(R.id.loadPdfButton_1).setOnClickListener(this);
        findViewById(R.id.loadPdfButton_2).setOnClickListener(this);
        findViewById(R.id.loadPdfButton_3).setOnClickListener(this);
        findViewById(R.id.loadPdfButton_4).setOnClickListener(this);
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
            case R.id. loadPdfButton_3:
                loadPdf("Large_Pdf_Test.pdf");
                break;
            case R.id. loadPdfButton_4:
                loadPdf("Mid_Pdf_Test.pdf");
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
            pdfRenderView.load(file.getAbsolutePath());
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
