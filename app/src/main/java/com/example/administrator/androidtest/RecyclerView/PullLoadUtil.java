package com.example.administrator.androidtest.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;

public class PullLoadUtil {
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

    public static boolean isCanRecyclerViewUp(View view, float diff){
        RecyclerView rv = (RecyclerView) view;
        if (diff < 0 && rv.computeVerticalScrollExtent() + rv.computeVerticalScrollOffset() < rv.computeVerticalScrollRange()) {
            return true;
        }
        return false;
    }

    public static boolean isCanRecyclerViewDown(View view, float diff){
        RecyclerView rv = (RecyclerView) view;
        if (rv.computeVerticalScrollOffset() > 0 && diff > 0) {
            return true;
        }
        return false;
    }

    public static boolean isCanRecyclerView(View view, float diff){
        return isCanRecyclerViewUp(view, diff) || isCanRecyclerViewDown(view, diff);
    }

    public static boolean isCanScrollView(View view, float diff){
        boolean isCan = false;
        ScrollView sv = (ScrollView) view;
        if(sv.getScrollY() >= 0 && diff > 0){
            isCan = true;
        }else if(sv.getChildCount() > 0){
            if(sv.getScrollY() + sv.getHeight() <= sv.getChildAt(0).getHeight() && diff > 0){
                isCan = true;
            }
        }
        return isCan;
    }
}
