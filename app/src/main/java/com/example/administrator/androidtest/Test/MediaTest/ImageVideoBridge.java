package com.example.administrator.androidtest.Test.MediaTest;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.bear.librv.VHBridge;
import com.bear.librv.VHolder;
import com.example.administrator.androidtest.Common.Media.Info.BaseInfo;
import com.example.administrator.androidtest.Common.Media.Info.ImageInfo;
import com.example.administrator.androidtest.R;
import com.example.libbase.Util.DensityUtil;
import com.example.libfresco.FrescoView;

public class ImageVideoBridge extends VHBridge<ImageVideoBridge.ImageVideoVHolder> {
    private int mDivider;

    public ImageVideoBridge(int divider) {
        mDivider = divider;
    }

    @NonNull
    @Override
    protected ImageVideoVHolder onCreateViewHolder(@NonNull View itemView) {
        return new ImageVideoVHolder(itemView);
    }

    @Override
    protected int layoutId() {
        return R.layout.item_image;
    }

    class ImageVideoVHolder extends VHolder<BaseInfo> {
        private FrescoView mFvImage;
        private int mSize;
        public ImageVideoVHolder(View itemView) {
            super(itemView);
            mFvImage = findViewById(R.id.fv_image);
            if (getRecyclerView() != null) {
                ViewGroup.LayoutParams lp = itemView.getLayoutParams();
                if (mSize == 0) {
                    int spanCount = ((GridLayoutManager)getRecyclerView().getLayoutManager()).getSpanCount();
                    mSize = (getRecyclerView().getWidth() - mDivider * (spanCount - 1)) / spanCount;
                }
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                lp.height = mSize;
                itemView.setLayoutParams(lp);
            }
        }

        @Override
        public void bindCursor(int pos, Cursor cursor) {
            BaseInfo info = BaseInfo.from(cursor);
            if (info instanceof ImageInfo){
                ImageInfo imageInfo = (ImageInfo) info;
                mFvImage.setPath(imageInfo.mPath, DensityUtil.dp2Px(mSize / 8.0f), DensityUtil.dp2Px(mSize / 8.0f));
            }
        }
    }
}
