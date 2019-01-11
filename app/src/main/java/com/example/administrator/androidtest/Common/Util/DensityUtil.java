package com.example.administrator.androidtest.Common.Util;


import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.administrator.androidtest.App;

/**
 * 
 * @author hitomi
 * 尺寸工具类
 *
 */
public class DensityUtil extends AppInitUtil{
	 /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2Px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2Dip(float pxValue) {
        final float scale = sContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);  
    }
    
    /**
     * 将sp值转换为px值，保证文字大小不变
     */ 
    public static int sp2px(float spValue) {
        final float fontScale = sContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f); 
    } 
    
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     */
    public static int px2Sp(float pxValue) {
    	final float fontScale = sContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
    
    /**
     * 根据上下文重置DisplayMetrics
     */
    public static DisplayMetrics dueDisplayMetrics(Context context){
    	WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
    	DisplayMetrics dm=new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm;
    }
    
    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(){
    	DisplayMetrics dm=dueDisplayMetrics(sContext);
    	return dm.widthPixels;
    }
    
    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(){
    	DisplayMetrics dm=dueDisplayMetrics(sContext);
    	return dm.heightPixels;
    }

    /**
     * 按比值获取高度
     */
    public static int getScreenHeightByPersent(float persent){
        return (int) (getScreenHeight() * persent);
    }


    /**
     * 按照比值获取宽度
     */
    public static int getScreenWidthByPersent(float persent){
        return (int) (getScreenWidth() * persent);
    }
}
