package com.example.administrator.androidtest.demo.OtherDemo.MediaStoreDemo;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.bear.librv.MultiTypeDelegate;
import com.bear.librv.MultiTypeHolder;
import com.example.administrator.androidtest.other.fileMedia.Info.BaseInfo;
import com.example.administrator.androidtest.other.fileMedia.Info.ImageInfo;
import com.example.administrator.androidtest.R;
import com.bear.libcommon.util.DensityUtil;
import com.bear.libfresco.FrescoView;

public class BaseImageVideoDelegate extends MultiTypeDelegate<Cursor, BaseImageVideoDelegate.ImageVideoHolder> {
    private final int mDivider;

    public BaseImageVideoDelegate(int divider) {
        mDivider = divider;
    }

    @NonNull
    @Override
    protected ImageVideoHolder onCreateViewHolder(@NonNull View itemView) {
        return new ImageVideoHolder(itemView, mDivider);
    }

    @Override
    protected int layoutId() {
        return R.layout.item_image;
    }

    public static class ImageVideoHolder extends MultiTypeHolder<Cursor> {
        private FrescoView fvImage;
        private int mSize;
        public ImageVideoHolder(View itemView, int divider) {
            super(itemView);
            fvImage = findViewById(R.id.fv_image);
            if (getRecyclerView() != null) {
                ViewGroup.LayoutParams lp = itemView.getLayoutParams();
                if (mSize == 0) {
                    int spanCount = ((GridLayoutManager)getRecyclerView().getLayoutManager()).getSpanCount();
                    mSize = (getRecyclerView().getWidth() - divider * (spanCount - 1)) / spanCount;
                }
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                lp.height = mSize;
                itemView.setLayoutParams(lp);
            }
        }

        @Override
        protected void bindFull(int pos, Cursor cursor) {
            super.bindFull(pos, cursor);
//            BaseInfo info = BaseInfo.from(cursor);
//            if (info instanceof ImageInfo){
//                ImageInfo imageInfo = (ImageInfo) info;
//                fvImage.setPath(imageInfo.mPath, DensityUtil.dp2Px(mSize / 8.0f), DensityUtil.dp2Px(mSize / 8.0f));
//            }
        }

        @Override
        public void bindCursor(int pos, Cursor cursor) {
            BaseInfo info = BaseInfo.from(cursor);
            if (info instanceof ImageInfo){
                ImageInfo imageInfo = (ImageInfo) info;
                fvImage.setPath(imageInfo.mPath, DensityUtil.dp2Px(mSize / 8.0f), DensityUtil.dp2Px(mSize / 8.0f));
            }
        }
    }
}
