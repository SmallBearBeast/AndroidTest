package com.bear.libcommon.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class BitmapUtil {
    public static Bitmap getBitmap(String path, int maxWidth, int maxHeight){
        BitmapFactory.Options op = OptionsUtil.optionsByWH(path, maxWidth, maxHeight);
        return BitmapFactory.decodeFile(path, op);
    }

    public static Bitmap getBitmap(String path, int inSampleSize){
        BitmapFactory.Options op = OptionsUtil.optionsByRate(path, inSampleSize);
        return BitmapFactory.decodeFile(path, op);
    }

    public static void save(String path, int maxWidth, int maxHeight, String savePath, boolean recycle){
        save(getBitmap(path, maxWidth, maxHeight), savePath, recycle);
    }

    public static void save(String path, int inSampleSize, String savePath, boolean recycle){
        save(getBitmap(path, inSampleSize), savePath, recycle);
    }

    public static void save(Bitmap bitmap, String savePath, boolean recycle){
        if(createFile(savePath)){
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(savePath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                if(recycle) {
                    bitmap.recycle();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(fos);
            }
        }
    }

    public static BitmapOp rotate(BitmapOp bop, int degree){
        bop.mMatrix.postRotate(degree);
        return bop;
    }

    public static BitmapOp rotate(BitmapOp bop, int degree, float px, float py){
        bop.mMatrix.postRotate(degree, px, py);
        return bop;
    }

    public static BitmapOp translate(BitmapOp bop, float dx, float dy){
        bop.mMatrix.postTranslate(dx, dy);
        return bop;
    }

    public static BitmapOp scale(BitmapOp bop, float sx, float sy){
        bop.mMatrix.postScale(sx, sy);
        return bop;
    }

    public static class BitmapOp{
        private Matrix mMatrix;
        private Bitmap mSrcBm;

        public BitmapOp(Bitmap srcBm){
            mSrcBm = srcBm;
            mMatrix = new Matrix();
        }

        public Bitmap bitmap(){
            return Bitmap.createBitmap(mSrcBm, 0, 0, mSrcBm.getWidth(), mSrcBm.getHeight(), mMatrix, true);
        }
    }

    private static void close(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            try {
                if (closeable != null) {
                    closeable.close();
                }
            } catch (IOException ioe) {}
        }
    }

    private static boolean createFile(String filePath){
        File file = new File(filePath);
        File parent = file.getParentFile();
        if(file.exists()){
            return true;
        } else if(parent.exists()){
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if(parent.mkdirs()){
                try {
                    return file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
