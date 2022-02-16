package com.example.administrator.androidtest.Test.MotionActTest;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import com.example.libbase.Util.DensityUtil;
import com.example.libbase.Util.ScreenUtil;
import com.example.liblog.SLog;

public class OutRecyclerView extends RecyclerView implements NestedScrollingParent2 {
    private static final String TAG = "OutRecyclerView";
    private NestedScrollingParentHelper parentHelper = new NestedScrollingParentHelper(this);
    private InRecyclerView inRecyclerView;
    private int[] childLocation = new int[2];
    private int maxHeight;
    public OutRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public OutRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        maxHeight = ScreenUtil.getStatusBarHeight() + DensityUtil.dp2Px(100);
        setOnFlingListener(new OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                if (inRecyclerView != null) {
                    if (velocityY > 0) {
                        inRecyclerView.fling(velocityX, velocityY);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target,
                                       @ViewCompat.ScrollAxis int axes, @ViewCompat.NestedScrollType int type) {
        SLog.d(TAG, "onStartNestedScroll() called with: child = [" + child + "], target = [" + target + "], axes = [" + axes + "], type = [" + type + "]");
        return true;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target,
                                       @ViewCompat.ScrollAxis int axes, @ViewCompat.NestedScrollType int type) {
        SLog.d(TAG, "onNestedScrollAccepted() called with: child = [" + child + "], target = [" + target + "], axes = [" + axes + "], type = [" + type + "]");
        parentHelper.onNestedScrollAccepted(child, target, axes, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, @ViewCompat.NestedScrollType int type) {
        SLog.d(TAG, "onStopNestedScroll() called with: target = [" + target + "], type = [" + type + "]");
        parentHelper.onStopNestedScroll(target, type);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed,
                               @ViewCompat.NestedScrollType int type) {
        SLog.d(TAG, "onNestedScroll() called with: target = [" + target + "], dxConsumed = [" + dxConsumed + "], dyConsumed = [" + dyConsumed + "], dxUnconsumed = [" + dxUnconsumed + "], dyUnconsumed = [" + dyUnconsumed + "], type = [" + type + "]");
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed,
                                  @ViewCompat.NestedScrollType int type) {
        SLog.d(TAG, "onNestedPreScroll() dx = " + dx + ", dy = " + dy + ", consumed = " + consumed + ", type = " + type);
        if (target instanceof InRecyclerView) {
            inRecyclerView = (InRecyclerView) target;
            target.getLocationOnScreen(childLocation);
            if (dy >= 0) {
                if (childLocation[1] > maxHeight) {
                    consumed[0] = 0;
                    consumed[1] = dy;
                    scrollBy(0, dy);
                }
            } else {
                if (!target.canScrollVertically(-1)){
                    consumed[0] = 0;
                    consumed[1] = dy;
                    scrollBy(0, dy);
                }
            }
        }
    }
}
