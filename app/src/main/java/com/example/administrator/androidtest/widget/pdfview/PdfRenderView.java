package com.example.administrator.androidtest.widget.pdfview;

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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PdfRenderView extends RecyclerView {
    private static final String TAG = "PdfRenderView";

    private int dividerHeight = 10;
    private String currentPath = "";
    private PdfRenderer pdfRenderer;
    private final PdfBitmapPool pdfBitmapPool = new PdfBitmapPool();
    private final PdfBitmapCache pdfBitmapCache = new PdfBitmapCache();
    private final Map<String, Bitmap> activeBitmapCache = new ConcurrentHashMap<>();
    private ExecutorService executor = Executors.newCachedThreadPool();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public PdfRenderView(Context context) {
        this(context, null);
    }

    public PdfRenderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        pdfBitmapCache.setRecyclerCallback(pdfBitmapPool::put);
        initView();
    }

    private void initView() {
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
            if (currentPath.equals(path)) {
                SLog.d(TAG, "load: use same path. path = " + path);
                return;
            }
            currentPath = path;
            if (getAdapter() != null && getAdapter() instanceof PdfItemAdapter) {
                ((PdfItemAdapter) getAdapter()).cancelJob();
            }
            closePdf();
            activeBitmapCache.clear();
            File file = new File(path);
            int startIndex = path.lastIndexOf("/") + 1;
            int endIndex = path.lastIndexOf(".");
            String prefixKey = path.substring(startIndex, endIndex);
            prefixKey = prefixKey.isEmpty() ? "pdf-" + file.lastModified() : prefixKey + "-" + file.lastModified();
            ParcelFileDescriptor pfd = ParcelFileDescriptor.open(new File(path), ParcelFileDescriptor.MODE_READ_ONLY);
            pdfRenderer = new PdfRenderer(pfd);
            SLog.d(TAG, "load: path = " + path + ", prefixKey = " + prefixKey + ", lastModified = " + file.lastModified() + ", pageCount = " + pdfRenderer.getPageCount());
            setAdapter(new PdfItemAdapter(prefixKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        pdfBitmapCache.setRecyclerCallback(pdfBitmapPool::put);
        if (executor.isShutdown()) {
            executor = Executors.newCachedThreadPool();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        SLog.d(TAG, "onDetachedFromWindow");
        closePdf();
        activeBitmapCache.clear();
        pdfBitmapCache.clear();
        pdfBitmapPool.clear();
        executor.shutdown();
        currentPath = "";
    }

    private void closePdf() {
        try {
            synchronized (PdfRenderer.class) {
                if (pdfRenderer != null) {
                    pdfRenderer.close();
                    pdfRenderer = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            }
        }

        @NonNull
        @Override
        public PdfItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ImageView iv = new ImageView(parent.getContext());
            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            return new PdfItemViewHolder(iv);
        }

        @Override
        public void onBindViewHolder(@NonNull PdfItemViewHolder holder, int position) {
            holder.bindPdfItem(position, prefixKey + position);
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

        @Override
        public int getItemCount() {
            return pdfPageCount;
        }

        public void cancelJob() {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                ViewHolder viewHolder = getChildViewHolder(getChildAt(i));
                if (viewHolder instanceof PdfItemViewHolder) {
                    ((PdfItemViewHolder)viewHolder).cancelJob();
                }
            }
        }
    }

    private class PdfItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private Future<?> futureJob;

        public PdfItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
        }

        public void bindPdfItem(int position, String cacheKey) {
            imageView.setTag(position);
            Bitmap cacheBitmap = activeBitmapCache.get(cacheKey);
            if (cacheBitmap != null && !cacheBitmap.isRecycled()) {
                SLog.d(TAG, "bindPdfItem: use active cache: position = " + position + ", cacheKey = " + cacheKey);
                imageView.setImageBitmap(cacheBitmap);
                return;
            }
            cacheBitmap = pdfBitmapCache.remove(cacheKey);
            if (cacheBitmap != null && !cacheBitmap.isRecycled()) {
                SLog.d(TAG, "bindPdfItem: use lru cache: position = " + position + ", cacheKey = " + cacheKey);
                activeBitmapCache.put(cacheKey, cacheBitmap);
                imageView.setImageBitmap(cacheBitmap);
                return;
            }
            if (pdfRenderer != null) {
                SLog.d(TAG, "bindPdfItem: use source: position = " + position + ", cacheKey = " + cacheKey);
                loadPdfBitmap(position, bitmap -> {
                    SLog.d(TAG, "bindPdfItem: load bitmap success: position = " + position + ", tag = " + imageView.getTag());
                    if (imageView.getTag().equals(position)) {
                        activeBitmapCache.put(cacheKey, bitmap);
                        mainHandler.post(() -> imageView.setImageBitmap(bitmap));
                    } else {
                        pdfBitmapCache.put(cacheKey, bitmap);
                    }
                });
            }
        }

        public void cancelJob() {
            if (futureJob != null) {
                futureJob.cancel(true);
            }
        }

        private void loadPdfBitmap(int position, LoadPdfBitmapCallback callback) {
            futureJob = executor.submit(() -> {
                if (imageView.getTag().equals(position)) {
                    // java.lang.IllegalStateException: Current page not closed
                    synchronized (PdfRenderer.class) {
                        if (pdfRenderer != null) {
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
                }
            });
        }
    }

    private interface LoadPdfBitmapCallback {
        void onLoadPdfBitmap(Bitmap bitmap);
    }
}
