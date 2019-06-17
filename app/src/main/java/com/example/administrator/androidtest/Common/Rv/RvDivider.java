package com.example.administrator.androidtest.Common.Rv;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

// 插入时候分割线不会移动，会有视觉问题，最好是分割线是透明区域，由background来决定颜色。
// 插入删除由于每个itemview范围不一致，导致起始点动画突变，基本无解。建议不要有动画。
public class RvDivider extends RecyclerView.ItemDecoration {
    private static final String TAG = "RvDivider";
    private RecyclerView.LayoutManager mLayoutManager;
    private int mOrientation;
    private int mColor;
    private Drawable mDrawable;
    private Paint mPaint;
    private int mDividerWidth;
    private int mDividerVer;

    public RvDivider(LinearLayoutManager layoutManager, int dividerWidth, int color){
        this((RecyclerView.LayoutManager)layoutManager, dividerWidth, color, null);
    }

    public RvDivider(LinearLayoutManager layoutManager, int dividerWidth, Drawable drawable){
        this((RecyclerView.LayoutManager)layoutManager, dividerWidth, 0, drawable);
    }


    public RvDivider(GridLayoutManager layoutManager, int dividerWidth){
        this((RecyclerView.LayoutManager)layoutManager, dividerWidth, 0, null);
    }

    public RvDivider(StaggeredGridLayoutManager layoutManager, int dividerWidth){
        this(layoutManager, dividerWidth, 0, null);
    }

    private RvDivider(RecyclerView.LayoutManager layoutManager, int dividerWidth, int color, Drawable drawable){
        mLayoutManager = layoutManager;
        mOrientation = getManagerOrientation();
        mDividerWidth = dividerWidth;
        mColor = color;
        mDrawable = drawable;
        if(mLayoutManager instanceof LinearLayoutManager) {
            initPaint();
        }
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(mColor);
    }

    //注意GridLayoutManager是LinearLayoutManager的子类。
    private int getManagerOrientation(){
        if(mLayoutManager instanceof LinearLayoutManager){
            return ((LinearLayoutManager) mLayoutManager).getOrientation();
        }else if(mLayoutManager instanceof StaggeredGridLayoutManager){
            return ((StaggeredGridLayoutManager) mLayoutManager).getOrientation();
        }
        return RecyclerView.VERTICAL;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if(mLayoutManager instanceof LinearLayoutManager){
            if(mOrientation == RecyclerView.VERTICAL) {
                for (int i = 1, count = parent.getChildCount(); i < count; i++) {
                    View child = parent.getChildAt(i);
                    if(mDrawable != null){
                        mDrawable.setBounds(child.getLeft(), child.getTop() - mDividerWidth, child.getRight(), child.getTop());
                        mDrawable.draw(c);
                    }else {
                        c.drawRect(child.getLeft(), child.getTop() - mDividerWidth, child.getRight(), child.getTop(), mPaint);
                    }
                }
            }else if(mOrientation == RecyclerView.HORIZONTAL){
                for (int i = 1, count = parent.getChildCount(); i < count; i++) {
                    View child = parent.getChildAt(i);
                    if(mDrawable != null){
                        mDrawable.setBounds(child.getLeft(), child.getTop() - mDividerWidth, child.getRight(), child.getTop());
                        mDrawable.draw(c);
                    }else {
                        c.drawRect(child.getLeft() - mDividerWidth, child.getTop(), child.getLeft(), child.getBottom(), mPaint);
                    }
                }
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(mLayoutManager instanceof GridLayoutManager){
            GridLayoutManager manager = (GridLayoutManager) mLayoutManager;
            int pos = parent.getChildAdapterPosition(view);
            int spanCount = manager.getSpanCount();
            if(mOrientation == RecyclerView.VERTICAL) {
                outRect.left = (pos % spanCount == 0 ? 0 : mDividerWidth / 2);
                outRect.top = (pos < spanCount ? 0 : mDividerWidth);
                outRect.right = ((pos + 1) % spanCount == 0 ? 0 : mDividerWidth / 2);
            }else if(mOrientation == RecyclerView.HORIZONTAL){
                outRect.top = (pos % spanCount == 0 ? 0 : mDividerWidth / 2);
                outRect.left = (pos < spanCount ? 0 : mDividerWidth);
                outRect.bottom = ((pos + 1) % spanCount == 0 ? 0 : mDividerWidth / 2);
            }
            Log.d(TAG, "getItemOffsets: outRect = " + outRect + " pos = " + pos);
        }else if(mLayoutManager instanceof LinearLayoutManager){
            int pos = parent.getChildAdapterPosition(view);
            if(mOrientation == RecyclerView.VERTICAL){
                outRect.set(0, pos == 0 ? 0 : mDividerWidth, 0, 0);
            }else if(mOrientation == RecyclerView.HORIZONTAL){
                outRect.set(pos == 0 ? 0 : mDividerWidth, 0, 0, 0);
            }
        }else if(mLayoutManager instanceof StaggeredGridLayoutManager){
            StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) mLayoutManager;
            int pos = parent.getChildAdapterPosition(view);
            int spanCount = manager.getSpanCount();
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                int spanIndex = ((StaggeredGridLayoutManager.LayoutParams) lp).getSpanIndex();
                if(mOrientation == RecyclerView.VERTICAL){
                    outRect.left = (spanIndex == 0 ? 0 : mDividerWidth / 2);
                    outRect.top = (pos < spanCount ? 0 : mDividerWidth);
                    outRect.right = ((spanIndex + 1) == spanCount ? 0 : mDividerWidth / 2);
                }else if(mOrientation == RecyclerView.HORIZONTAL){
                    outRect.top = (spanIndex == 0 ? 0 : mDividerWidth / 2);
                    outRect.left = (pos < spanCount ? 0 : mDividerWidth);
                    outRect.bottom = ((spanIndex + 1) == spanCount ? 0 : mDividerWidth / 2);
                }
                Log.d(TAG, "getItemOffsets: outRect = " + outRect + " pos = " + pos + " spanIndex = " + spanIndex);
            }
        }
    }
}
