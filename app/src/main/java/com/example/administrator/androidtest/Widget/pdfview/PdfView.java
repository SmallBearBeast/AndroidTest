package com.example.administrator.androidtest.Widget.pdfview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.pdf.PdfRenderer;
import android.os.Handler;
import android.os.Looper;
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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PdfView extends RecyclerView {
    private static final String TAG = "PdfView";

    private int dividerHeight = 10;
    private PdfRenderer pdfRenderer;
    private final PdfBitmapPool pdfBitmapPool;
    private final PdfBitmapCache pdfBitmapCache;
    private final Map<String, Bitmap> activeBitmapCache = new HashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public PdfView(Context context) {
        this(context, null);
    }

    public PdfView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        pdfBitmapPool = new PdfBitmapPool();
        pdfBitmapCache = new PdfBitmapCache();
        pdfBitmapCache.setRecyclerCallback(pdfBitmapPool::put);
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
        drawable.setColor(Color.TRANSPARENT);
        drawable.setShape(GradientDrawable.RECTANGLE);
        decoration.setDrawable(drawable);
        addItemDecoration(decoration);
    }

    public void load(String path) {
        try {
            activeBitmapCache.clear();
            int startIndex = path.lastIndexOf("/") + 1;
            int endIndex = path.lastIndexOf(".");
            String prefixKey = path.substring(startIndex, endIndex);
            prefixKey = prefixKey.isEmpty() ? "pdf-" : prefixKey + "-";
            SLog.d(TAG, "load: path = " + path + ", prefixKey = " + prefixKey);
            ParcelFileDescriptor pfd = ParcelFileDescriptor.open(new File(path), ParcelFileDescriptor.MODE_READ_ONLY);
            pdfRenderer = new PdfRenderer(pfd);
            setAdapter(new PdfItemAdapter(prefixKey));
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
        pdfBitmapCache.clear();
        pdfBitmapPool.clear();
        activeBitmapCache.clear();
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
        private final String prefixKey;

        public PdfItemAdapter(String key) {
            prefixKey = key;
            if (pdfRenderer != null) {
                pdfPageCount = pdfRenderer.getPageCount();
                SLog.d(TAG, "new PdfItemAdapter: pdfPageCount = " + pdfPageCount);
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
            holder.bindPdfItem(position, prefixKey + position);
        }

        @Override
        public int getItemCount() {
            return pdfPageCount;
        }

        @Override
        public void onViewRecycled(@NonNull PdfItemViewHolder holder) {
            super.onViewRecycled(holder);
            String cacheKey = prefixKey + holder.getAbsoluteAdapterPosition();
            Bitmap bitmap = activeBitmapCache.remove(cacheKey);
            SLog.d(TAG, "onViewRecycled: cacheKey = " + cacheKey + ", bitmap = " + bitmap);
            if (bitmap != null) {
                pdfBitmapCache.put(cacheKey, bitmap);
            }
        }
    }

    private class PdfItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        public PdfItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
        }

        public void bindPdfItem(int position, String cacheKey) {
            imageView.setTag(position);
            Bitmap cacheBitmap = pdfBitmapCache.remove(cacheKey);
            if (cacheBitmap != null && !cacheBitmap.isRecycled()) {
                SLog.d(TAG, "bindPdfItem: use cache: position = " + position + ", cacheKey = " + cacheKey);
                activeBitmapCache.put(cacheKey, cacheBitmap);
                imageView.setImageBitmap(cacheBitmap);
                return;
            }
            if (pdfRenderer != null) {
                loadPdfBitmap(position, bitmap -> {
                    if (imageView.getTag().equals(position)) {
                        SLog.d(TAG, "bindPdfItem: put into activeBitmapCache: position = " + position + ", cacheKey = " + cacheKey);
                        activeBitmapCache.put(cacheKey, bitmap);
                        mainHandler.post(() -> imageView.setImageBitmap(bitmap));
                    } else {
                        pdfBitmapCache.put(cacheKey, bitmap);
                    }
                });
            }
        }

        private void loadPdfBitmap(int position, LoadPdfBitmapCallback callback) {
            executor.execute(() -> {
                if (imageView.getTag().equals(position)) {
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
