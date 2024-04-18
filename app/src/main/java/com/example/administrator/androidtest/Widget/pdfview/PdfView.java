package com.example.administrator.androidtest.Widget.pdfview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileNotFoundException;

public class PdfView extends RecyclerView {
    private PdfRenderer pdfRenderer;

    private int dividerHeight = 50;

    public PdfView(Context context) {
        this(context, null);
    }

    public PdfView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                for (int i = 0; i < getItemDecorationCount(); i++) {
                    removeItemDecoration(getItemDecorationAt(i));
                }
                ColorDrawable drawable = new ColorDrawable(Color.TRANSPARENT);
                drawable.setBounds(0, 0, getMeasuredWidth(), dividerHeight);
                decoration.setDrawable(drawable);
                addItemDecoration(decoration);
            }
        });
    }

    public void load(String path) {
        try {
            ParcelFileDescriptor pfd = ParcelFileDescriptor.open(new File(path), ParcelFileDescriptor.MODE_READ_ONLY);
            PdfRenderer pdfRenderer = new PdfRenderer(pfd);
            setAdapter(new PdfItemAdapter(pdfRenderer));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (pdfRenderer != null) {
            pdfRenderer.close();
            pdfRenderer = null;
        }
        PdfBitmapPool.clear();
    }

    private static class PdfItemAdapter extends RecyclerView.Adapter<PdfItemViewHolder> {

        private final int pdfPageCount;
        private PdfRenderer pdfRenderer;

        public PdfItemAdapter(PdfRenderer renderer) {
            pdfRenderer = renderer;
            pdfPageCount = pdfRenderer.getPageCount();
        }

        @NonNull
        @Override
        public PdfItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ImageView iv = new ImageView(parent.getContext());
            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            return new PdfItemViewHolder(iv, pdfRenderer);
        }

        @Override
        public void onBindViewHolder(@NonNull PdfItemViewHolder holder, int position) {
            holder.bindPdfItem(position);
        }

        @Override
        public int getItemCount() {
            return pdfPageCount;
        }
    }

    private static class PdfItemViewHolder extends RecyclerView.ViewHolder {

        private PdfRenderer pdfRenderer;

        public PdfItemViewHolder(@NonNull View itemView, PdfRenderer renderer) {
            super(itemView);
            pdfRenderer = renderer;
        }

        // TODO: 2024/4/18 线程加载
        public void bindPdfItem(int position) {
            PdfRenderer.Page page = pdfRenderer.openPage(position);
            Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            if (itemView instanceof ImageView) {
                ((ImageView)itemView).setImageBitmap(bitmap);
            }
        }
    }
}
