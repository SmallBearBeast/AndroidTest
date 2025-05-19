package com.bear.libcommon.util;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * 存储工具类
 */
public class StorageUtil {
    /**
     * 外部存储是否存在
     */
    public static boolean isExternalStorageExists() {
        return Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED);
    }

    /**
     * 外部存储空间大小 byte
     */
    public static long getExternalSpace() {
        try {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return stat.getBlockSizeLong() * stat.getBlockCountLong();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }

    /**
     * 外部存储可用空间大小 byte
     */
    public static long getExternalRemainSpace() {
        try {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }

    /**
     * 内部存储空间大小 byte
     */
    public static long getInternalSpace() {
        try {
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            return stat.getBlockSizeLong() * stat.getBlockCountLong();
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1L;
    }

    /**
     * 可用内部存储空间剩余大小
     */
    public static long getInternalRemainSpace() {
        try {
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            return stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1L;
    }

    /**
     * 是否有足够的空间存储内容(先考虑外置存储，后考虑内置)
     */
    public static boolean hasSpace(long contentLength) {
        boolean hasSpace = true;
        if (isExternalStorageExists()) {
            if (contentLength > getExternalRemainSpace())
                hasSpace = false;
        } else {
            if (contentLength > getInternalSpace())
                hasSpace = false;
        }
        return hasSpace;
    }

}
