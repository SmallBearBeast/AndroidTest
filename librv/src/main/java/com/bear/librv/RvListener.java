package com.bear.librv;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView全局点击监听器
 */
public class RvListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final byte TYPE_CLICK = 1;
    private static final byte TYPE_LONG_CLICK = 2;
    private final GestureDetector gestureDetector;
    private final OnItemClickListener itemClickListener;

    public static class OnItemClickListener {
        //处理了返回true
        public boolean onItemClick(View view, int position) {
            return false;
        }

        public boolean onItemLongClick(View view, int position) {
            return false;
        }
    }

    public RvListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        itemClickListener = listener;

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            //多次点击反应慢是因为走了双击事件的回调
            @Override
            public boolean onSingleTapUp(@NonNull MotionEvent event) {
                //单击事件
                View childView = recyclerView.findChildViewUnder(event.getX(), event.getY());
                if (childView != null && itemClickListener != null) {
                    int pos = recyclerView.getChildLayoutPosition(childView);
                    return handleViewClickType(childView, event.getX(), event.getY(), pos, TYPE_CLICK);
                }
                return false;
            }

            @Override
            public void onLongPress(@NonNull MotionEvent event) {
                //长按事件
                View childView = recyclerView.findChildViewUnder(event.getX(), event.getY());
                if (childView != null && itemClickListener != null) {
                    int pos = recyclerView.getChildLayoutPosition(childView);
                    handleViewClickType(childView, event.getX(), event.getY(), pos, TYPE_LONG_CLICK);
                }
            }
        });
    }

    private boolean handleViewClickType(View targetView, float x, float y, int pos, int clickType) {
        // 快速不处理事件检查
        if (!isPointInsideView(targetView, x, y)) {
            return false;
        }

        if (targetView instanceof ViewGroup) {
            final float relativeX = x - targetView.getX() - targetView.getScrollX();
            final float relativeY = y - targetView.getY() - targetView.getScrollY();
            ViewGroup viewGroup = (ViewGroup) targetView;
            final int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = viewGroup.getChildAt(i);
                // 优先处理子View
                if (handleViewClickType(view, relativeX, relativeY, pos, clickType)) {
                    return true;
                }
            }
        }

        if (isPointInsideView(targetView, x, y)) {
            if (clickType == TYPE_CLICK) {
                return itemClickListener.onItemClick(targetView, pos);
            } else if (clickType == TYPE_LONG_CLICK) {
                return itemClickListener.onItemLongClick(targetView, pos);
            }
        }
        return false;
    }

    private boolean isPointInsideView(View view, float x, float y) {
        return view.getVisibility() == View.VISIBLE
                && x >= view.getX()
                && x <= view.getX() + view.getWidth()
                && y >= view.getY()
                && y <= view.getY() + view.getHeight();
    }

    // TODO: 2025/8/7 为什么是放在onInterceptTouchEvent里面
    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }
}
