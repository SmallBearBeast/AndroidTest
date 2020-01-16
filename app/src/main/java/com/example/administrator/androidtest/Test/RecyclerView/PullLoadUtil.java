package com.example.administrator.androidtest.Test.RecyclerView;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;

public class PullLoadUtil {
    private static final String TAG = "PullLoadUtil";
    public static boolean isScrollViewIntercept(View view, float diff){
        boolean isIntecepted = false;
        ScrollView sv = (ScrollView) view;
        if(sv.getScrollY() <= 0 && diff > 0){
            isIntecepted = true;
        }else if(sv.getChildCount() > 0){
            if(sv.getScrollY() + sv.getHeight() >= sv.getChildAt(0).getHeight() && diff > 0){
                isIntecepted = true;
            }
        }
        return isIntecepted;
    }

    public static boolean isRecyclerViewIntercept(View view, float diff){
        boolean isIntecepted = false;
        RecyclerView rv = (RecyclerView) view;
        if(rv.computeVerticalScrollOffset() <= 0 && diff > 0){
            isIntecepted = true;
        }else if (diff < 0 && rv.computeVerticalScrollExtent() + rv.computeVerticalScrollOffset() >= rv.computeVerticalScrollRange()){
            isIntecepted = true;
        }
        return isIntecepted;
    }


    /**
     * 判断View是否可以向上滑动
     */
    public static boolean isViewScrollUp(View view){
        if(view instanceof RecyclerView){
            return isCanRecyclerViewUp(view);
        }else if(view instanceof ScrollView){
            return isCanScrollViewUp(view);
        }
        return false;
    }


    /**
     * 判断View是否可以向下滑动
     */
    public static boolean isViewScrollDown(View view){
        if(view instanceof RecyclerView){
            return isCanRecyclerViewDown(view);
        }else if(view instanceof ScrollView){
            return isCanScrollViewDown(view);
        }
        return false;
    }

    /**RecyclerView滑动判断**/

    /**
     * 判断RecyclerView是否可以向上滑动
     */
    public static boolean isCanRecyclerViewUp(View view){
        RecyclerView rv = (RecyclerView) view;
        if (rv.computeVerticalScrollExtent() + rv.computeVerticalScrollOffset() < rv.computeVerticalScrollRange()) {
            return true;
        }
        return false;
    }

    /**
     * 判断RecyclerView是否可以向下滑动
     */
    public static boolean isCanRecyclerViewDown(View view){
        RecyclerView rv = (RecyclerView) view;
        if (rv.computeVerticalScrollOffset() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断RecyclerView是否可以滑动(上滑和下滑)
     */
    public static boolean isCanRecyclerView(View view){
        return isCanRecyclerViewUp(view) || isCanRecyclerViewDown(view);
    }

    /**************************************************************/


    /**ScrollView滑动判断**/

    /**
     * 判断ScrollView是否可以滑动(上滑和下滑)
     */
    public static boolean isCanScrollView(View view, float diff){
        return isCanScrollViewDown(view) || isCanScrollViewUp(view);
    }

    /**
     * 判断ScrollView是否可以向下滑动
     */
    public static boolean isCanScrollViewDown(View view){
        ScrollView sv = (ScrollView) view;
        if(sv.getScrollY() > 0){
            return true;
        }
        return false;
    }

    /**
     * 判断ScrollView是否可以向上滑动
     */
    public static boolean isCanScrollViewUp(View view){
        ScrollView sv = (ScrollView) view;
        if(sv.getChildCount() > 0) {
            if (sv.getScrollY() + sv.getHeight() < sv.getChildAt(0).getHeight()) {
                return true;
            }
        }
        return false;
    }

    /**************************************************************/
}
