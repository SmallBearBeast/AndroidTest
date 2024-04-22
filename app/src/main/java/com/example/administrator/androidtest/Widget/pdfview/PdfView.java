package com.example.administrator.androidtest.Widget.pdfview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liblog.SLog;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PdfView extends RecyclerView {
    private static final String TAG = "PdfView";

    private int dividerHeight = 10;
    private String prefixKey = "pdf";
    private PdfRenderer pdfRenderer;
    private final PdfBitmapPool pdfBitmapPool;
    private final PdfBitmapCache pdfBitmapCache;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public PdfView(Context context) {
        this(context, null);
    }

    public PdfView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        pdfBitmapPool = new PdfBitmapPool();
        pdfBitmapCache = new PdfBitmapCache();
        init();
    }

    private void init() {
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        initDecoration(dividerHeight);
    }

    private void initDecoration(int height) {
        for (int index = getItemDecorationCount() - 1; index > 0; index--) {
            removeItemDecorationAt(index);
        }
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setSize(0, height);
        drawable.setColor(Color.GREEN);
        drawable.setShape(GradientDrawable.RECTANGLE);
        decoration.setDrawable(drawable);
        addItemDecoration(decoration);
    }

    public void load(String path) {
        try {
            prefixKey = path.substring(path.lastIndexOf("/") + 1);
            prefixKey = prefixKey.isEmpty() ? "pdf-" : prefixKey + "-";
            SLog.d(TAG, "load: path = " + path + ", prefixKey = " + prefixKey);
            ParcelFileDescriptor pfd = ParcelFileDescriptor.open(new File(path), ParcelFileDescriptor.MODE_READ_ONLY);
            pdfRenderer = new PdfRenderer(pfd);
            setAdapter(new PdfItemAdapter());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        SLog.d(TAG, "onDetachedFromWindow");
        if (pdfRenderer != null) {
            pdfRenderer.close();
            pdfRenderer = null;
        }
        pdfBitmapPool.clear();
        pdfBitmapCache.trimToSize(0);
        executor.shutdown();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDividerHeight(int height) {
        dividerHeight = height;
        initDecoration(height);
        if (getAdapter() != null) {
            getAdapter().notifyDataSetChanged();
        }
    }

    private class PdfItemAdapter extends RecyclerView.Adapter<PdfItemViewHolder> {

        private int pdfPageCount;

        public PdfItemAdapter() {
            if (pdfRenderer != null) {
                pdfPageCount = pdfRenderer.getPageCount();
            }
        }

        @NonNull
        @Override
        public PdfItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ImageView iv = new ImageView(parent.getContext());
            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            return new PdfItemViewHolder(iv);
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

    private class PdfItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        public PdfItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
        }

        public void bindPdfItem(int position) {
            itemView.setTag(position);
            Bitmap cacheBitmap = pdfBitmapCache.get(prefixKey + position);
            if (cacheBitmap != null && !cacheBitmap.isRecycled()) {
                SLog.d(TAG, "bindPdfItem: use cache: position = " + position);
                imageView.setImageBitmap(cacheBitmap);
                return;
            }
            if (pdfRenderer != null) {
                loadPdfBitmap(position, bitmap -> {
                    itemView.post(() -> ((ImageView) itemView).setImageBitmap(bitmap));
                });
            }
        }

        private void loadPdfBitmap(int position, LoadPdfBitmapCallback callback) {
            executor.execute(() -> {
                if (itemView.getTag().equals(position)) {
                    synchronized (PdfRenderer.class) {
                        PdfRenderer.Page page = pdfRenderer.openPage(position);
                        int width = getResources().getDisplayMetrics().densityDpi * page.getWidth() / 72;
                        int height = getResources().getDisplayMetrics().densityDpi * page.getHeight() / 72;
                        int pageWidth = page.getWidth();
                        int pageHeight = page.getHeight();
                        int viewWidth = getWidth();
                        if (pageWidth > 0) {
                            width = viewWidth;
                            height = (int) (viewWidth * 1f / pageWidth * pageHeight);
                        }
                        Bitmap bitmap = pdfBitmapPool.get(width, height);
                        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                        page.close();
                        pdfBitmapCache.put(prefixKey + position, bitmap);
                        SLog.d(TAG, "loadPdfBitmap: position = " + position + ", page.getWidth() = " + page.getWidth() + ", page.getHeight() = " + page.getHeight() + ", width = " + width + ", height = " + height + ", densityDpi = " + getResources().getDisplayMetrics().densityDpi);
                        if (callback != null) {
                            callback.onLoadPdfBitmap(bitmap);
                        }
                    }
                }
            });
        }
    }

    private interface LoadPdfBitmapCallback {
        void onLoadPdfBitmap(Bitmap bitmap);
    }
}
