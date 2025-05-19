package com.example.libcommon.Util;

import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class OptionsUtil {

    public static final List<String> NORMAL_TYPE = new ArrayList<String>(4){{
        add("jpeg");
        add("jpg");
        add("png");
        add("webp");
    }};

    /**
     * 获取Options
     */
    public static BitmapFactory.Options options(String path){
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, op);
        return op;
    }

    /**
     * 判断给定路径的图片类型
     */
    public static String type(String path){
        BitmapFactory.Options op = options(path);
        if(!TextUtils.isEmpty(op.outMimeType)){
            return op.outMimeType.substring(6);
        }
        return null;
    }

    /**
     * 判断是否是所属类型
     */
    public static boolean isType(String path, List<String> typeArray){
        String type = type(path);
        return typeArray.contains(type);
    }

    /**
     * 宽 / 高
     */
    public static float rate(String path){
        BitmapFactory.Options op = options(path);
        return op.outWidth * 1.0f / op.outHeight;
    }

    /**
     * 判断宽高比是否在给定范围
     */
    public static boolean isRate(String path, float minRate, float maxRate){
        float rate = rate(path);
        return rate >= minRate && rate <= maxRate;
    }

    /**
     * 判断宽高比是否在给定分辨率
     */
    public static boolean isResolution(String path, int width, int height){
        BitmapFactory.Options op = options(path);
        return op.outWidth <= width && op.outHeight <= height;
    }

    public static BitmapFactory.Options optionsByRate(String path, int inSampleSize){
        BitmapFactory.Options op = options(path);
        op.inSampleSize = inSampleSize;
        op.inJustDecodeBounds = false;
        return op;
    }

    public static BitmapFactory.Options optionsByWH(String path, int maxWidth, int maxHeight){
        BitmapFactory.Options op = options(path);
        op.inJustDecodeBounds = false;
        op.inSampleSize = calInSampleSize(op, maxWidth, maxHeight);
        return op;
    }

    private static int calInSampleSize(BitmapFactory.Options op, int maxWidth, int maxHeight){
        int height = op.outHeight;
        int width = op.outWidth;
        int inSampleSize = 1;
        while (width >= maxWidth && height >= maxHeight) {
            width = width >> 1;
            height = height >> 1;
            inSampleSize = inSampleSize << 1;
        }
        return inSampleSize;
    }

}
